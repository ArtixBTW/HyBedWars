package tomeko.hybedwars.hud

//? if forge {
/*import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
*///?} else {
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.polyfrost.compose.composables.PolyBox
import org.polyfrost.compose.composables.PolyCanvas
import org.polyfrost.compose.composables.PolyColumn
import org.polyfrost.compose.composables.PolyMcText
import org.polyfrost.compose.composables.PolyModifier
import org.polyfrost.compose.composables.PolyRow
import org.polyfrost.compose.composables.padding
import org.polyfrost.compose.composables.size
import org.polyfrost.compose.layout.PolyInsets
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.api.hud.v1.Hud
import org.polyfrost.oneconfig.api.hud.v1.HudManager
//?}
import tomeko.hybedwars.config.HyBedWarsConfig
import tomeko.hybedwars.location.HypixelPackets
import tomeko.hybedwars.teammates.Teammates
import tomeko.hybedwars.teammates.Teammates.Teammate
import tomeko.hybedwars.utils.Constants
import tomeko.hybedwars.utils.removeFormatting
import java.util.Locale
import kotlin.math.ceil

/**
 * Lists your teammates and their health.
 * On Fabric and Ornithe this is a Compose HUD, so the HUD editor shows a live preview card for it.
 */
class TeammateHud
//? if forge {
//: BasicHud(true)
//?} else {
    : Hud("${Constants.MOD_ID}_teammates.json", "BedWars Teammates", Category.COMBAT)
//?}
{
    //? if forge {
    //@Exclude
    //?}
    companion object {
        //? if !forge {
        fun register() {
            HudManager.register(TeammateHud(), Constants.MOD_ID, Constants.MOD_ICON)
        }

        private const val SUBCATEGORY_HEALTH = "Health"
        private const val SUBCATEGORY_PLAYERS = "Players"

        private const val COLUMN_GAP = 4f
        private const val ROW_GAP = 1f
        //?}

        private const val HEART_SIZE = 9

        // Placeholder rows for the HUD editor and its preview card
        private val EXAMPLE = listOf(
            Teammate("§cTeammateOne", 20f, 20f),
            Teammate("§cTeammateTwo", 11f, 20f),
            Teammate("§cTeammateThree", 4f, 20f),
            Teammate("§cTeammateFour", 0f, 20f, true)
        )
    }

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Heart Icon",
        //? if !forge {
        subcategory = SUBCATEGORY_HEALTH
        //?}
    )
    var showHeart = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Color Health",
        description = "Green when healthy, yellow when hurt, red when low.",
        //? if !forge {
        subcategory = SUBCATEGORY_HEALTH
        //?}
    )
    var colorHealth = true

    //? if forge {
    /*@Switch(name = "Text Shadow")
    var textShadow = true
    *///?}

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Yourself",
        //? if !forge {
        subcategory = SUBCATEGORY_PLAYERS
        //?}
    )
    var showSelf = false

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Show Dead Teammates",
        description = "Keep listing teammates while they respawn or after they've been final killed.",
        //? if !forge {
        subcategory = SUBCATEGORY_PLAYERS
        //?}
    )
    var showDead = true

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Max Players Shown",
        description = "The most teammates the HUD will list at once.",
        min = 1f, max = 40f,
        //? if forge {
        //step = 1
        //?} else {
        step = 1f,
        subcategory = SUBCATEGORY_PLAYERS
        //?}
    )
    var maxPlayers = 10f

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Nearby Teammates in Castle",
        description = "In Castle (40v40), only list the closest teammates within range instead of the whole team.",
        //? if !forge {
        subcategory = SUBCATEGORY_PLAYERS
        //?}
    )
    var castleNearby = true

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
        //?}
        "Castle Range",
        description = "How close (in blocks) a teammate must be to show up in Castle.",
        min = 5f, max = 100f,
        //? if forge {
        //step = 1
        //?} else {
        step = 1f,
        subcategory = SUBCATEGORY_PLAYERS
        //?}
    )
    var castleRange = 25f

    /** Only in BedWars games: in lobbies everyone with your rank shows up as a "teammate". */
    private val active: Boolean
        get() = HyBedWarsConfig.debugModeEnabled || (HypixelPackets.inBedwars && HypixelPackets.inGame)

    private fun teammates(example: Boolean): List<Teammate> {
        if (example) return EXAMPLE
        if (!active) return emptyList()

        val castle = HypixelPackets.currentMode?.uppercase(Locale.ROOT)?.contains("CASTLE") == true
        val range = if (castleNearby && castle) castleRange else 0f
        return Teammates.collect(showSelf, showDead, range, maxPlayers.toInt())
    }

    private fun nameText(t: Teammate): String =
        if (t.dead) "§7§m" + t.displayName.removeFormatting() else t.displayName

    private fun healthText(t: Teammate): String {
        if (t.dead) return "§7Dead"
        if (t.health < 0) return "§7?"

        var color = "§f"
        if (colorHealth) {
            val fraction = t.health / t.maxHealth.coerceAtLeast(1f)
            color = when {
                fraction > 0.6f -> "§a"
                fraction > 0.3f -> "§e"
                else -> "§c"
            }
        }
        return color + ceil(t.health).toInt()
    }

    //? if !forge {
    private class Row(val name: String, val health: String, val alive: Boolean)

    // Starts with the example rows so the HUD editor's preview card has something to show
    private var rowsState: MutableState<List<Row>> = mutableStateOf(EXAMPLE.map(::toRow))

    private fun toRow(t: Teammate) = Row(nameText(t), healthText(t), !t.dead)

    init {
        padLeft = 4f
        padRight = 4f
        padTop = 4f
        padBottom = 4f
        staticWidth = false
    }

    override val description: String = "Your teammates and their health in BedWars."

    override fun update(): Boolean {
        rowsState.value = teammates(!isReal || HudManager.isEditing).map(::toRow)
        return true
    }

    // Collecting teammates walks the whole tab list, 20 times a second is plenty
    override fun updateFrequency(): Long = 50_000_000L

    override fun setup() {
        super.setup()
        update()
    }

    // No background box when there is nobody to list
    override fun hasBackground(): Boolean = rowsState.value.isNotEmpty()

    @Composable
    override fun Content() {
        val rows = rowsState.value
        if (rows.isEmpty()) return

        val scale = textScale
        val color = PolyColor(textColor, textChroma, textChromaSpeed)
        val shadow = showShadow

        // One column each for names, hearts and health so every row lines up
        PolyBox(modifier = hudBackground().padding(PolyInsets(padLeft, padTop, padRight, padBottom))) {
            PolyRow(gap = COLUMN_GAP * scale) {
                PolyColumn(gap = ROW_GAP * scale) {
                    for (row in rows) PolyMcText(row.name, color = color, shadow = shadow, scale = scale)
                }
                if (showHeart) {
                    PolyColumn(gap = ROW_GAP * scale) {
                        for (row in rows) Heart(row.alive, HEART_SIZE * scale)
                    }
                }
                PolyColumn(gap = ROW_GAP * scale) {
                    for (row in rows) PolyMcText(row.health, color = color, shadow = shadow, scale = scale)
                }
            }
        }
    }

    @Composable
    private fun Heart(full: Boolean, size: Float) {
        PolyCanvas(modifier = PolyModifier.size(size, size)) { x, y, w, h ->
            HeartTextures.draw(canvas, x, y, w, h, full)
        }
    }

    override fun minimumSize(): Pair<Float, Float> =
        (padLeft + padRight + 2f) to (padTop + padBottom + 2f)

    override fun defaultPosition(): Pair<Float, Float> = 10f to 40f
    override fun multipleInstancesAllowed(): Boolean = true
    override fun deletable(): Boolean = true

    override fun clone(): Hud = (super.clone() as TeammateHud).also {
        it.rowsState = mutableStateOf(rowsState.value)
    }
    //?} else {
    /*@Exclude
    private var actualWidth = 1f

    @Exclude
    private var actualHeight = 10f

    override fun draw(matrices: UMatrixStack, x: Float, y: Float, scale: Float, example: Boolean) {
        GlStateManager.pushMatrix()
        GlStateManager.translate(x, y, 0f)
        GlStateManager.scale(scale, scale, 1f)
        drawRows(teammates(example))
        GlStateManager.popMatrix()
    }

    override fun getWidth(scale: Float, example: Boolean): Float = actualWidth * scale
    override fun getHeight(scale: Float, example: Boolean): Float = actualHeight * scale

    override fun shouldShow(): Boolean = super.shouldShow() && active

    private fun drawRows(rows: List<Teammate>) {
        val mc = Minecraft.getMinecraft()
        val font = mc.fontRendererObj
        val rowHeight = 10
        val gap = 4

        // Health goes in its own column so every row lines up
        var nameWidth = 0
        var healthWidth = 0
        for (t in rows) {
            nameWidth = maxOf(nameWidth, font.getStringWidth(nameText(t)))
            healthWidth = maxOf(healthWidth, font.getStringWidth(healthText(t)))
        }

        val heartX = nameWidth + gap
        val healthX = if (showHeart) heartX + HEART_SIZE + 1 else heartX

        var y = 0
        for (t in rows) {
            font.drawString(nameText(t), 0f, y.toFloat(), 0xFFFFFFFF.toInt(), textShadow)
            if (showHeart) {
                mc.textureManager.bindTexture(Gui.icons)
                GlStateManager.color(1f, 1f, 1f, 1f)
                GlStateManager.enableBlend()
                // Outline, then the red fill on top
                Gui.drawModalRectWithCustomSizedTexture(heartX, y - 1, 16f, 0f, HEART_SIZE, HEART_SIZE, 256f, 256f)
                if (!t.dead) Gui.drawModalRectWithCustomSizedTexture(heartX, y - 1, 52f, 0f, HEART_SIZE, HEART_SIZE, 256f, 256f)
            }
            font.drawString(healthText(t), healthX.toFloat(), y.toFloat(), 0xFFFFFFFF.toInt(), textShadow)
            y += rowHeight
        }

        actualWidth = (healthX + healthWidth).toFloat().coerceAtLeast(1f)
        actualHeight = (rows.size * rowHeight - 1).toFloat().coerceAtLeast(rowHeight.toFloat())
    }
    *///?}
}
