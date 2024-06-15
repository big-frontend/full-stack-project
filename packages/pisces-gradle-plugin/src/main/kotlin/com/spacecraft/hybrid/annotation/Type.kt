package com.spacecraft.hybrid.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class Type(val name: String, val isDefault: Boolean = false)