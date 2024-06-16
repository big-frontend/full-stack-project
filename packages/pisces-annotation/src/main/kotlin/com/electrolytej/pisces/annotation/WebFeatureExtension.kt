package com.electrolytej.pisces.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class WebFeatureExtension(
    val name: String,
    val actions: Array<Action>
)