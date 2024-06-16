package com.electrolytej.pisces

import com.electrolytej.pisces.annotation.WebFeatureExtension
import com.electrolytej.pisces.annotation.WebInherited
import javassist.CtClass
import com.jamesfchen.P
/**
 * 用于处理feature、widget 、module、inherited注解
 * 新的注解处理不要在这里添加，另起炉灶模拟EventTargetProcessor在创建一个Processor！！！！！！
 */
class GeminiMetaDataProcessor : BaseProcessor<MetaData>() {
    override val modifiedClassName = "com.spacecraft.hybrid.WebMetaDataSetImpl"
    override fun supportAnnotation() = setOf(
        WebInherited::class.java,
        WebFeatureExtension::class.java)
    override fun map(ctClass: CtClass): MetaData? {
        val webInheritedAnnotation =
            ctClass.getAnnotation(WebInherited::class.java) as? WebInherited
        if (webInheritedAnnotation != null) {
            return InheritedMetaData(ctClass.name, ctClass.getSuperclasses())
        }
        val webFeatureExtensionAnnotation =
            ctClass.getAnnotation(WebFeatureExtension::class.java) as? WebFeatureExtension
        if (webFeatureExtensionAnnotation != null) {
            return WebFeatureExtensionMetaData(
                webFeatureExtensionAnnotation.name,
                ctClass.name,
                ctClass.getSuperclasses(),
                webFeatureExtensionAnnotation.getMethods(),
            )
        }
        return null
    }

    override fun process(modifiedCtClass: CtClass, sons: List<MetaData>) {
        //去掉继承链的无效类
        //todo:是否抛出异常让开发者修改会更佳合理而不是remove
        val extensions = sons.filterIsInstance<IExtensionMetaData>()
            .toMutableList<IExtensionMetaData?>()
            .removeParent { item, other ->
                return@removeParent item.name != other.name
            }
        sons.filterIsInstance<InheritedMetaData>().forEach { inherited ->
            val sucess = extensions.replace(inherited)
            if (!sucess) {
                throw RuntimeException("Fail to resolve inherited: " + inherited.classname)
            }
        }
        extensions.removeParent { item, other ->
            return@removeParent item.name != other.name
        }
        P.info("web extensions:${extensions.size}个 $extensions")
        modifiedCtClass.injectMetaData(extensions,
            "initFeatureMetaData"
        )

    }
}

data class WebFeatureExtensionMetaData(
    override val name: String,
    override var classname: String,
    override var superClasses: List<String>,
    override val methods: List<Method>,
) : IExtensionMetaData

fun WebFeatureExtension?.getMethods() = getMethods(this?.actions)