package tomeko.hybedwars.teammates.heart_textures

//? if !forge {
import net.minecraft.client.Minecraft
//? if ornithe {
/*import net.minecraft.util.ResourceLocation
*///?} else {
import net.minecraft.resources.Identifier
//?}
import org.polyfrost.compose.render.FilterQuality
import org.polyfrost.compose.render.PolyBitmap
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.compose.render.RenderContext

/**
 * Vanilla heart textures as bitmaps, for drawing hearts inside Compose HUDs.
 * Read from the game's resources (so resource packs apply) the first time they're needed.
 */
sealed class HeartTextures(val container: HeartSprite, val full: HeartSprite) {
    class HeartSprite(val image: PolyBitmap, val u0: Float = 0f, val v0: Float = 0f, val u1: Float = 1f, val v1: Float = 1f )

    companion object {
        // 1.8.9 keeps every HUD icon in one 256x256 sheet, scaled up by HD resource packs
        //? if 1.8.9
        //private val sheet = decode("textures/gui/icons.png")

        fun decode(path: String): PolyBitmap =
            PolyBitmap.decode(read(path)) ?: throw IllegalStateException("can't decode $path")

        private fun read(path: String): ByteArray {
            //? if ornithe {
            /*return Minecraft.getMinecraft().resourceManager.getResource(ResourceLocation(path)).inputStream.use { it.readBytes() }
            *///?} else {
            return Minecraft.getInstance().resourceManager
                .getResource(Identifier.withDefaultNamespace(path))
                .orElseThrow { IllegalStateException("missing $path") }
                .open().use { it.readBytes() }
            //?}
        }
    }

    //? if 1.8.9
    //constructor(container: (PolyBitmap) -> HeartSprite, full: (PolyBitmap) -> HeartSprite) : this(container(sheet), full(sheet))

    /** Draws an empty heart outline, filled in red when [filled]. */
    fun draw(ctx: RenderContext, x: Float, y: Float, w: Float, h: Float, filled: Boolean) {
        ctx.draw(container, x, y, w, h)
        if (filled) ctx.draw(full, x, y, w, h)
    }

    private fun RenderContext.draw(sprite: HeartSprite, x: Float, y: Float, w: Float, h: Float) =
        image(sprite.image, x, y, w, h, sprite.u0, sprite.v0, sprite.u1, sprite.v1, PolyColor.WHITE, FilterQuality.None)
}
//?}