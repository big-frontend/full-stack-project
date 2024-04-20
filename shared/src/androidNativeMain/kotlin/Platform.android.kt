import platform.posix.ADD
import platform.gles.GL_ADD
class AndroidPlatform : Platform {
    override val name: String = "Android Native 32"
    fun a(){
        ADD
        GL_ADD
    }
}

actual fun getPlatform(): Platform = AndroidPlatform()