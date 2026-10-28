package tomeko.hybedwars.hud

//? if !forge {
import net.minecraft.client.Minecraft
//? if ornithe {
//import net.minecraft.util.ResourceLocation
//?} else {
import net.minecraft.resources.Identifier
//?}
import org.jetbrains.skia.Canvas
import org.jetbrains.skia.FilterMipmap
import org.jetbrains.skia.FilterMode
import org.jetbrains.skia.Image
import org.jetbrains.skia.MipmapMode
import org.jetbrains.skia.Rect
import tomeko.hybedwars.utils.Debug

/**
 * Vanilla heart textures as Skia images, for drawing hearts inside Compose HUDs.
 * Read from the game's resources (so resource packs apply) the first time they're needed.
 */
object HeartTextures {
    private class Sprite(val image: Image, val src: Rect)

    private val NEAREST = FilterMipmap(FilterMode.NEAREST, MipmapMode.NONE)

    private var loaded = false
    private var container: Sprite? = null
    private var full: Sprite? = null

    /** Draws an empty heart outline, filled in red when [filled]. */
    fun draw(canvas: Canvas, x: Float, y: Float, w: Float, h: Float, filled: Boolean) {
        load()
        val dst = Rect.makeXYWH(x, y, w, h)
        container?.let { canvas.drawImageRect(it.image, it.src, dst, NEAREST, null, true) }
        if (filled) full?.let { canvas.drawImageRect(it.image, it.src, dst, NEAREST, null, true) }
    }

    private fun load() {
        if (loaded) return
        loaded = true

        try {
            //? if ornithe {
            /*// 1.8.9 keeps every HUD icon in one 256x256 sheet, scaled up by HD resource packs
            val sheet = Image.makeFromEncoded(read("textures/gui/icons.png"))
            val px = sheet.width / 256f
            container = Sprite(sheet, Rect.makeXYWH(16 * px, 0f, 9 * px, 9 * px))
            full = Sprite(sheet, Rect.makeXYWH(52 * px, 0f, 9 * px, 9 * px))
            *///?} else {
            container = whole(Image.makeFromEncoded(read("textures/gui/sprites/hud/heart/container.png")))
            full = whole(Image.makeFromEncoded(read("textures/gui/sprites/hud/heart/full.png")))
            //?}
        } catch (e: Exception) {
            Debug.forceLog("Couldn't load heart textures: $e")
        }
    }

    private fun whole(image: Image) = Sprite(image, Rect.makeWH(image.width.toFloat(), image.height.toFloat()))

    private fun read(path: String): ByteArray {
        //? if ornithe {
        //return Minecraft.getMinecraft().resourceManager.getResource(ResourceLocation(path)).inputStream.use { it.readBytes() }
        //?} else {
        return Minecraft.getInstance().resourceManager
            .getResource(Identifier.withDefaultNamespace(path))
            .orElseThrow { IllegalStateException("missing $path") }
            .open().use { it.readBytes() }
        //?}
    }
}
//?}
