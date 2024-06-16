package com.electrolytej.pisces

import javassist.CtClass
import com.electrolytej.pisces.annotation.EventTarget

class EventTargetProcessor : BaseProcessor<EventTargetMetaData>() {
    override val modifiedClassName = "com.spacecraft.hybrid.event.EventTargetDataSetImpl"
    override fun supportAnnotation() = setOf(EventTarget::class.java)
    override fun map(ctClass: CtClass): EventTargetMetaData {
        val eventTargetAnnotation =
            ctClass.getAnnotation(EventTarget::class.java) as EventTarget
        return EventTargetMetaData(
            eventTargetAnnotation.eventNames,
            ctClass.name,
            ctClass.getSuperclasses()
        )
    }

    override fun process(modifiedCtClass: CtClass, sons: List<EventTargetMetaData>) {
        //todo:是否抛出异常让开发者修改会更佳合理而不是remove
        val eventTargets = sons.toMutableList<EventTargetMetaData?>().removeParent()
        if (eventTargets.isEmpty()) return
        //重组装列表event target 为 event为key的map数据类型
        val allEventTargetMap = mutableMapOf<String, MutableList<EventTargetMetaData>>()
        for (eventTarget in eventTargets) {
            if (eventTarget == null) continue
            for (event in eventTarget.eventNames) {
                var targets = allEventTargetMap[event]
                if (targets == null) {
                    targets = mutableListOf()
                    allEventTargetMap[event] = targets
                }
                targets.add(eventTarget)
            }
        }
        allEventTargetMap.forEach { (event, evenTargets) ->
            evenTargets.forEach { et ->
                modifiedCtClass.getDeclaredMethod("initEventTargetMetaData")
                    .insertAfter(buildString {
                        append(
                            "com.spacecraft.hybrid.event.EventTargetMetaData eventTarget_$event = new com.spacecraft.hybrid.event.EventTargetMetaData(" +
                                    "new String[]{${et.eventNames.joinToString(",") { "\"$it\"" }}}," +
                                    "\"${et.classname}\"" +
                                    ");"
                        )
                        append("com.spacecraft.hybrid.event.EventTargetDataSetImpl.put(\"${event}\", eventTarget_$event);")
                    })
            }
        }
    }
}

data class EventTargetMetaData(
    val eventNames: Array<String>,
    override var classname: String,
    override var superClasses: List<String>
): MetaData

//去掉继承链的无效类
fun <T : EventTargetMetaData?> MutableList<T?>.removeParent() = removeParent(null)
