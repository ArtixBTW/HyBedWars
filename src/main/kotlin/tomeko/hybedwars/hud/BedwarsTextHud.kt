package tomeko.hybedwars.hud

//? if forge {
/*import cc.polyfrost.oneconfig.config.annotations.Exclude
import cc.polyfrost.oneconfig.hud.SingleTextHud
*///?} else {
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import org.polyfrost.compose.composables.PolyBox
import org.polyfrost.compose.composables.PolyMcText
import org.polyfrost.compose.composables.PolyRow
import org.polyfrost.compose.composables.padding
import org.polyfrost.compose.layout.PolyInsets
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.api.hud.v1.Hud
import org.polyfrost.oneconfig.api.hud.v1.HudManager
//?}
import tomeko.hybedwars.utils.Constants

/**
 * Single line "Label: value" HUD used by the height limit, map and mode HUDs.
 * Hidden outside BedWars and whenever [value] has nothing to show.
 * On Fabric and Ornithe this is a Compose HUD, so the HUD editor shows a live preview card for it.
 */
abstract class BedwarsTextHud(
    id: String,
    hudTitle: String,
    defaultLabel: String,
    hudDescription: String
)
//? if forge {
/*: SingleTextHud(defaultLabel, false)
*///?} else {
    : Hud("${Constants.MOD_ID}_$id.json", hudTitle, Category.INFO)
//?}
{
    //? if forge {
    //@Exclude
    //?}
    companion object {
        //? if !forge {
        const val CATEGORY_GENERAL = "General"
        const val SUBCATEGORY_TEXT = "Text"
        const val SUBCATEGORY_OPTIONS = "Options"
        //?}

        // Same colors as Minecraft's §a, §e and §c
        const val COLOR_GOOD = 0xFF55FF55.toInt()
        const val COLOR_WARN = 0xFFFFFF55.toInt()
        const val COLOR_BAD = 0xFFFF5555.toInt()
    }

    /**
     * Text after the label. When [example] is true (HUD editor, preview card) return a sample if there's no real data.
     * Return null to hide the HUD.
     */
    protected abstract fun value(example: Boolean): String?

    /** ARGB color for the value, or null to use the normal text color. */
    protected open fun valueColor(example: Boolean): Int? = null

    //? if forge {
    /*override fun getText(example: Boolean): String {
        val text = value(example) ?: return ""
        val code = when (valueColor(example)) {
            COLOR_GOOD -> "§a"
            COLOR_WARN -> "§e"
            COLOR_BAD -> "§c"
            else -> ""
        }
        return code + text
    }

    override fun shouldShow(): Boolean =
        super.shouldShow() && BedwarsInfo.active && value(false) != null
    *///?} else {
    @Text(
        title = "Label",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_TEXT
    )
    var label = defaultLabel

    @Switch(
        title = "Show Label",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_TEXT
    )
    var showLabel = true

    private class Line(val label: String, val value: String, val valueColor: Int?)

    // Null hides the HUD
    private var lineState: MutableState<Line?> = mutableStateOf(null)

    init {
        padLeft = 4f
        padRight = 4f
        padTop = 4f
        padBottom = 4f
        staticWidth = false
    }

    override val description: String = hudDescription

    override fun update(): Boolean {
        // The preview card uses an instance that isn't "real", so it always gets sample values
        val example = !isReal || HudManager.isEditing
        val value = if (example || BedwarsInfo.active) value(example) else null

        lineState.value = value?.let {
            val labelText = if (showLabel && label.isNotBlank()) "$label: " else ""
            Line(labelText, it, valueColor(example))
        }
        return true
    }

    // The height limit changes as you move, 20 times a second keeps it in step
    override fun updateFrequency(): Long = 50_000_000L

    override fun setup() {
        super.setup()
        update()
    }

    // No background box while there's nothing to show
    override fun hasBackground(): Boolean = lineState.value != null

    @Composable
    override fun Content() {
        val line = lineState.value ?: return

        val scale = textScale
        val color = PolyColor(textColor, textChroma, textChromaSpeed)
        val valueColor = line.valueColor?.let { PolyColor(it) } ?: color
        val shadow = showShadow

        PolyBox(modifier = hudBackground().padding(PolyInsets(padLeft, padTop, padRight, padBottom))) {
            PolyRow {
                if (line.label.isNotEmpty()) PolyMcText(line.label, color = color, shadow = shadow, scale = scale)
                PolyMcText(line.value, color = valueColor, shadow = shadow, scale = scale)
            }
        }
    }

    override fun minimumSize(): Pair<Float, Float> =
        (padLeft + padRight + 2f) to (padTop + padBottom + 2f)

    override fun defaultPosition(): Pair<Float, Float> = 10f to 10f
    override fun multipleInstancesAllowed(): Boolean = true
    override fun deletable(): Boolean = true

    override fun clone(): Hud = (super.clone() as BedwarsTextHud).also {
        it.lineState = mutableStateOf(lineState.value)
    }
    //?}
}
