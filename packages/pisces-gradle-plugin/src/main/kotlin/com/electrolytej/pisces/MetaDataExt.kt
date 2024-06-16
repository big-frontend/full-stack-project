package com.electrolytej.pisces

import com.electrolytej.pisces.annotation.Action
interface IExtensionMetaData : MetaData {
    val name: String
    val methods: List<Method>
    /**
     * Invocation mode.
     */
    enum class Mode {
        /**
         * Synchronous invocation. When calling actions in such mode, caller
         * will get response until invocation finished.
         */
        SYNC,

        /**
         * Asynchronous invocation. When calling actions in such mode, caller
         * will get an empty response immediately, but wait in a different
         * thread to get response until invocation finished.
         */
        ASYNC,

        /**
         * Callback invocation. When calling actions in such mode, caller will
         * get an empty response immediately, but receive response through
         * callback when invocation finished.
         */
        CALLBACK,

        /**
         * Synchronous invocation. When calling actions in such mode, caller
         * will get response until invocation finished, but receive response
         * through callback later.
         */
        SYNC_CALLBACK
    }

    enum class Type {
        FUNCTION, ATTRIBUTE, EVENT
    }

    enum class Access {
        NONE, READ, WRITE
    }

    enum class Normalize {
        RAW, JSON
    }

    enum class NativeType {
        INSTANCE
    }

    enum class Multiple {
        SINGLE, MULTI
    }

   companion object{
        const val ACTION_INIT = "__init__"
       @JvmStatic
       fun getOrdinalBy(obj: String): Int {
           var ordinal = 0
           if (Mode.SYNC.name.equals(obj)) {
               ordinal = Mode.SYNC.ordinal
           } else if (Mode.ASYNC.name.equals(obj)) {
               ordinal = Mode.ASYNC.ordinal
           } else if (Mode.CALLBACK.name.equals(obj)) {
               ordinal = Mode.CALLBACK.ordinal
           } else if (Mode.SYNC_CALLBACK.name.equals(obj)) {
               ordinal = Mode.SYNC_CALLBACK.ordinal
           } else if (Type.FUNCTION.name.equals(obj)) {
               ordinal = Type.FUNCTION.ordinal
           } else if (Type.ATTRIBUTE.name.equals(obj)) {
               ordinal = Type.ATTRIBUTE.ordinal
           } else if (Type.EVENT.name.equals(obj)) {
               ordinal = Type.EVENT.ordinal
           } else if (Access.NONE.name.equals(obj)) {
               ordinal = Access.NONE.ordinal
           } else if (Access.READ.name.equals(obj)) {
               ordinal = Access.READ.ordinal
           } else if (Access.WRITE.name.equals(obj)) {
               ordinal = Access.WRITE.ordinal
           } else if (Normalize.RAW.name.equals(obj)) {
               ordinal = Normalize.RAW.ordinal
           } else if (Normalize.JSON.name.equals(obj)) {
               ordinal = Normalize.JSON.ordinal
           } else if (NativeType.INSTANCE.name.equals(obj)) {
               ordinal = NativeType.INSTANCE.ordinal
           } else if (Multiple.SINGLE.name.equals(obj)) {
               ordinal = Multiple.SINGLE.ordinal
           } else if (Multiple.MULTI.name.equals(obj)) {
               ordinal = Multiple.MULTI.ordinal
           }
           return ordinal
       }
   }
}

interface MetaData {
    var classname: String
    var superClasses: List<String>
}

private val d: (Int, MetaData, Int, MetaData) -> Int = l@{ i, item, j, other ->
    if (item.superClasses.contains(other.classname)) {
        return@l j
    } else if (other.superClasses.contains(item.classname)) {
        return@l i
    }
    -1
}

fun <T : MetaData> MutableList<T?>.removeParent(
    skip: ((T, T) -> Boolean)? = null,
) = remove(skip, deleteCondition = d)

/**
 *去掉继承链中的无效父类
 *
 * @param skip:跳过item与other的比较
 * @param deleteCondition:-1为没有需要删除的元素
 *
 */
fun <T> MutableList<T?>.remove(
    skip: ((T, T) -> Boolean)?,
    deleteCondition: (Int, T, Int, T) -> Int
) = apply {
    for (i in 0 until size) {
        val item = get(i) ?: continue
        for (j in i + 1 until size) {
            val other = get(j) ?: continue
            if (skip?.invoke(item, other) == true) continue
            val pos = deleteCondition(i, item, j, other)
            if (pos != -1) {
                set(pos, null)
                //如果pos为item的index，则break内部循环，从i+1的item找parent类
                if (pos == i) break
            }
        }
    }
    val iter = iterator()
    while (iter.hasNext()) {
        if (iter.next() == null) {
            iter.remove()
        }
    }
}

data class Method(
    val name: String,
    val isInstanceMethod: Boolean = false,
    val mode: IExtensionMetaData.Mode,
    val type: IExtensionMetaData.Type,
    val access: IExtensionMetaData.Access,
    val normalize: IExtensionMetaData.Normalize,
    val multiple: IExtensionMetaData.Multiple,
    val alias: String = "",
    val permissions: Array<String>,
    val subAttrs: Array<String>,
    val residentType: Action.ResidentType,
)

fun getMethods(actions: Array<Action>?): List<Method> {
    val l = mutableListOf<Method>()
    if (actions.isNullOrEmpty()) return l
    for (action in actions) {
        l.add(
            Method(
                action.name,
                action.instanceMethod,
                action.mode,
                action.type,
                action.access,
                action.normalize,
                action.multiple,
                action.alias,
                action.permissions,
                action.subAttrs,
                action.residentType
            )
        )
    }
    return l
}

