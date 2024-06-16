package com.electrolytej.pisces.annotation

import com.spacecraft.hybrid.IExtensionMetaData


@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class Action(
    val name: String,
    val instanceMethod: Boolean = false,
    val mode: IExtensionMetaData.Mode,
    val type: IExtensionMetaData.Type = IExtensionMetaData.Type.FUNCTION,
    val access: IExtensionMetaData.Access = IExtensionMetaData.Access.NONE,
    val normalize: IExtensionMetaData.Normalize = IExtensionMetaData.Normalize.JSON,
    val multiple: IExtensionMetaData.Multiple = IExtensionMetaData.Multiple.SINGLE,
    val alias: String = "",
    val permissions: Array<String> = [],
    val subAttrs: Array<String> = [],
    val residentType: ResidentType = ResidentType.NONE
) {
    enum class ResidentType {
        NONE, USEABLE
    }
}