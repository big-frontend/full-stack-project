import platform.posix.ACCESSPERMS
import platform.gles.GL_ADD

class ApplePlatform : Platform {
    override val name: String = "apple platform"
    fun a() {
        ACCESSPERMS
        GL_ADD
    }
}

actual fun getPlatform(): Platform = ApplePlatform()