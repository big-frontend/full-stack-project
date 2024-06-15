package com.spacecraft.hybrid.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class Widget(
    val name: String,
    val types: Array<Type> = [],
    val methods: Array<String> = [],
    val needDeleteSuperClasses: Boolean = false
)