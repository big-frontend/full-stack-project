package com.spacecraft.hybrid.annotation

@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
@kotlin.annotation.Retention(AnnotationRetention.BINARY)
annotation class FeatureExtension(
    val name: String,
    val actions: Array<Action>,
    val residentType: ResidentType = ResidentType.FORBIDDEN
) {
    enum class ResidentType {
        FORBIDDEN, USEABLE, RESIDENT_NORMAL, RESIDENT_IMPORTANT
    }
}