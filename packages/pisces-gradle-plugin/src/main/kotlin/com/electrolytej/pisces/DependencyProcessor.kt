package com.electrolytej.pisces

import javassist.CtClass
import com.electrolytej.pisces.annotation.Dependency

class DependencyProcessor : BaseProcessor<DependencyMetaData>() {
    override val modifiedClassName: String
        get() = "com.spacecraft.hybrid.DependencyManagerImpl"
    override fun supportAnnotation() = setOf(Dependency::class.java)

    override fun map(ctClass: CtClass): DependencyMetaData {
        val dependency =
            ctClass.getAnnotation(Dependency::class.java) as Dependency
        return DependencyMetaData(dependency.key, ctClass.name, ctClass.getSuperclasses())
    }

    override fun process(modifiedCtClass: CtClass, sons: List<DependencyMetaData>) {
        val deps = sons.toMutableList<DependencyMetaData?>().removeParent()
        if (deps.isEmpty()) return
        modifiedCtClass.getDeclaredMethod("initDependencyMetaData").insertAfter(buildString {
            append("com.spacecraft.hybrid.DependencyManager.Dependency dependency;")
            deps.filterNotNull().forEach { d->
                append("dependency = new com.spacecraft.hybrid.DependencyManager.Dependency(\"${d.classname}\");")
                append("$1.put(\"${d.key}\",dependency);")
            }
        })
    }


}

data class DependencyMetaData(
    val key: String,
    override var classname: String,
    override var superClasses: List<String>,
) : MetaData