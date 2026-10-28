package tomeko.hybedwars.hud

import net.minecraft.client.Minecraft
//? if 1.8.9 {
/*//? if ornithe {
/*import net.minecraft.client.gui.Gui
import net.minecraft.client.renderer.GlStateManager
*///?}
//? if forge {
/*import cc.polyfrost.oneconfig.config.annotations.*
import cc.polyfrost.oneconfig.config.core.OneColor
import cc.polyfrost.oneconfig.hud.BasicHud
import cc.polyfrost.oneconfig.libs.universal.UGraphics
import cc.polyfrost.oneconfig.libs.universal.UMatrixStack
import cc.polyfrost.oneconfig.renderer.TextRenderer
*///?}
import net.minecraft.client.renderer.RenderHelper
import net.minecraft.init.Items
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
*///?} else {
import net.minecraft.client.gui.GuiGraphicsExtractor
import net.minecraft.world.item.Item
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
//?}
//? if !forge {
import org.polyfrost.compose.render.PolyColor
import org.polyfrost.oneconfig.api.config.v1.annotations.*
import org.polyfrost.oneconfig.api.hud.v1.HudManager
import org.polyfrost.oneconfig.api.hud.v1.LegacyHud
//?}
import tomeko.hybedwars.config.HyBedWarsConfig
import tomeko.hybedwars.utils.HypixelPackets
import tomeko.hybedwars.utils.Constants
import tomeko.hybedwars.utils.ItemTracker

class BedwarsResourceDisplay
//? if forge {
//: BasicHud(true)
//?} else {
    : LegacyHud("${Constants.MOD_ID}_bedwars_resource_display.json", "BedWars Resource Display", Category.COMBAT)
//?}
{
    //? if forge {
    //@Exclude
    //?}
    companion object {
        //? if !forge {
        fun register() {
            HudManager.register(BedwarsResourceDisplay(), Constants.MOD_ID, Constants.MOD_ICON)
        }
        //?}

        //? if !forge {
        private const val CATEGORY_GENERAL = "General"
        private const val SUBCATEGORY_GENERAL = "General"
        private const val CATEGORY_FORMATTING = "Formatting"
        private const val CATEGORY_STYLING = "Styling"
        //?}
    }

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Iron",
        description = "Show iron counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showIron = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Gold",
        description = "Show gold counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showGold = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Diamond",
        description = "Show diamond counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showDiamond = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Emerald",
        description = "Show emerald counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showEmerald = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Inventory",
        description = "Show inventory counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showInventory = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Ender Chest",
        description = "Show ender chest counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showEnderChest = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Total",
        description = "Show (inventory + ender chest) counter",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showTotal = true

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Mode",
        description = "Change display mode",
        options = ["Vertical", "Horizontal"],
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var mode = 0

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Counters Padding",
        description = "Change spacing between the counters",
        min = 0f,
        max = 10f,
        //? if !forge {
        step = 0.1f,
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var countersPadding = 5f

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Icon Padding",
        description = "Change spacing between the icon and the text",
        min = 0f,
        max = 10f,
        //? if !forge {
        step = 0.1f,
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var iconPadding = 5f

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Total Side",
        description = "Change side of the total counter display",
        options = ["Left", "Right"],
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var totalSide = 1

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Icon Side",
        description = "Change side of the icons",
        options = ["Left", "Right"],
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var iconSide = 0

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Padding Side",
        description = "Change padding side of the display in vertical mode",
        options = ["Left", "Middle", "Right"],
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var paddingSide = 0

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Compact Numbers",
        description = "Hide ender chest counter when equals 0",
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var compactNumbers = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Hide When Zero",
        description = "Hide counter if both inventory and ender chest equal 0",
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var hideWhenZero = false

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show In Lobby",
        description = "Show in Hypixel BedWars lobby",
        //? if !forge {
        category = CATEGORY_FORMATTING,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var showInLobby = true

    //? if forge {
    /*@Dropdown(
        name = "Text Shadow",
        description = "Change text shadow",
        options = ["No Shadow", "Shadow", "Full Shadow"]
    )
    var textType = 0

    @Color(
        name = "Text Color",
        description = "Change text color"
    )
    var textColor: OneColor = OneColor(255, 255, 255)
    *///?} else {
    @Color(
        title = "Text Color",
        description = "Change text color",
        category = CATEGORY_STYLING,
        subcategory = SUBCATEGORY_GENERAL
    )
    var txtColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        title = "Text Shadow",
        description = "Add text shadow",
        category = CATEGORY_STYLING,
        subcategory = SUBCATEGORY_GENERAL
    )
    var textShadow = true

    @Color(
        title = "Text Shadow Color",
        description = "Change text shadow color",
        category = CATEGORY_STYLING,
        subcategory = SUBCATEGORY_GENERAL
    )
    var textShadowColor = PolyColor(0xFF000000.toInt())
    //?}

    //? if !forge {
    @Switch(
        title = "Show Background",
        description = "Show background behind resource display",
        category = CATEGORY_STYLING,
        subcategory = SUBCATEGORY_GENERAL
    )
    var background = false

    @Color(
        title = "Background Color",
        description = "Change background color behind resource display",
        category = CATEGORY_STYLING,
        subcategory = SUBCATEGORY_GENERAL
    )
    var backgroundColor = PolyColor(0x80000000.toInt())

    @Slider(
        title = "Background Radius",
        description = "Change background radius behind resource display",
        min = 0f,
        max = 10f,
        step = 0.1f,
        category = CATEGORY_STYLING,
        subcategory = SUBCATEGORY_GENERAL
    )
    var backgroundRadius = 5f
    //?}

    //? if forge {
    //@Exclude
    //?}
    private var actualWidth = 1f

    //? if forge {
    //@Exclude
    //?}
    private var actualHeight = 1f

    //? if forge {
    /*override fun draw(
        matrices: UMatrixStack,
        x: Float,
        y: Float,
        scale: Float,
        example: Boolean
    )
    *///?} else {
    override fun render(
        //? if fabric {
        mcCtx: GuiGraphicsExtractor
        //?}
    )
    //?}
    {
        if (
        //? if forge {
        //!example
        //?} else {
            !HudManager.isEditing
            //?}
            && !HyBedWarsConfig.debugModeEnabled
            && (!HypixelPackets.inBedwars || (!showInLobby && HypixelPackets.inLobby))
        ) return

        val mc =
        //? if 1.8.9 {
        //Minecraft.getMinecraft()
            //?} else {
            Minecraft.getInstance()
        //?}

        val iconSize = 16f
        val offset = iconSize + countersPadding

        val IRON: Item =
        //? if 1.8.9 {
        //Items.iron_ingot
            //?} else {
            Items.IRON_INGOT
        //?}

        val GOLD: Item =
        //? if 1.8.9 {
        //Items.gold_ingot
            //?} else {
            Items.GOLD_INGOT
        //?}

        val DIAMOND: Item =
        //? if 1.8.9 {
        //Items.diamond
            //?} else {
            Items.DIAMOND
        //?}

        val EMERALD: Item =
        //? if 1.8.9 {
        //Items.emerald
            //?} else {
            Items.EMERALD
        //?}

        val items = mutableListOf(
            IRON,
            GOLD,
            DIAMOND,
            EMERALD
        )

        fun showItem(item: Item): Boolean {
            val inventoryAmount = ItemTracker.inventory[item] ?: 0
            val enderChestAmount = ItemTracker.enderChest[item] ?: 0

            return (!hideWhenZero ||
                    (showTotal && inventoryAmount + enderChestAmount > 0) ||
                    (showInventory && inventoryAmount > 0) ||
                    (showEnderChest && enderChestAmount > 0)) &&
                    when (item) {
                        IRON -> showIron
                        GOLD -> showGold
                        DIAMOND -> showDiamond
                        EMERALD -> showEmerald
                        else -> false
                    }
        }

        fun StringBuilder.appendAmount(amount: String) =
            apply {
                this.append(if (this.isNotEmpty()) " " else "")
                this.append(amount)
            }

        fun StringBuilder.appendTotal(inventoryAmount: Int, enderChestAmount: Int) =
            if (showInventory || showEnderChest) {
                this.appendAmount("(${inventoryAmount + enderChestAmount})")
            } else {
                // if total is the only element don't prepend a space for no reason
                append("${inventoryAmount + enderChestAmount}")
            }

        fun getText(item: Item): String {
            val inventoryAmount = ItemTracker.inventory[item] ?: 0
            val enderChestAmount = ItemTracker.enderChest[item] ?: 0

            val totalOnly = !(showInventory || showEnderChest)

            val shouldShowFull = !compactNumbers || enderChestAmount != 0

            val text = StringBuilder()
            if ((shouldShowFull || totalOnly) && totalSide == 0 && showTotal) {
                text.appendTotal(inventoryAmount, enderChestAmount)
            }

            if (showInventory) {
                text.appendAmount(inventoryAmount.toString())

                if (showEnderChest && shouldShowFull) {
                    text.append(" +")
                }
            }

            if (showEnderChest && (shouldShowFull || !showInventory)) {
                text.appendAmount(enderChestAmount.toString())
            }

            if ((shouldShowFull || totalOnly) && totalSide == 1 && showTotal) {
                text.appendTotal(inventoryAmount, enderChestAmount)
            }

            text.append(" ")

            return text.toString()
        }

        fun measure(text: String): Int =
        //? if 1.8.9 {
        //mc.fontRendererObj.getStringWidth(text)
            //?} else {
            mc.font.width(text)
        //?}

        val visibleItems = items.filter { showItem(it) }
        val texts = visibleItems.map { getText(it) }
        val textWidths = texts.map { measure(it) }
        val entryWidths = textWidths.map { it + iconPadding + iconSize }

        val size = visibleItems.size
        val horizontal = mode == 1

        if (size == 0) {
            actualWidth = 1f
            actualHeight = 1f
            return
        }

        val contentWidth: Float =
            if (horizontal) entryWidths.sum() + (size - 1) * countersPadding
            else entryWidths.maxOrNull() ?: 0f
        val contentHeight: Float =
            if (horizontal) iconSize
            else size * offset - countersPadding

        //? if forge {
        /*UGraphics.GL.pushMatrix()
        UGraphics.GL.scale(scale, scale, 1f)
        UGraphics.GL.translate(x / scale, y / scale, 0f)
        *///? elif ornithe {
        //GlStateManager.pushMatrix()
        //?} else {
        mcCtx.pose().pushMatrix()
        //?}

        //? if !forge {
        if (background) {
            //? if ornithe {
            //Gui.drawRect(
            //?} else {
            mcCtx.fill(
                //?}
                -backgroundRadius.toInt(),
                -backgroundRadius.toInt(),
                contentWidth.toInt() + backgroundRadius.toInt(),
                contentHeight.toInt() + backgroundRadius.toInt(),
                backgroundColor.argb
            )
        }
        //?}

        var cursorX = 0f
        for ((i, item) in visibleItems.withIndex()) {
            val stack = ItemStack(item)
            val text = texts[i]
            val textWidth = textWidths[i]
            val entryWidth = entryWidths[i]

            val entryX: Int
            val itemY: Int
            if (horizontal) {
                entryX = cursorX.toInt()
                itemY = 0
                cursorX += entryWidth + countersPadding
            } else {
                entryX = when (paddingSide) {
                    1 -> ((contentWidth - entryWidth) / 2f).toInt()
                    2 -> (contentWidth - entryWidth).toInt()
                    else -> 0
                }
                itemY = (i * offset).toInt()
            }

            val textX = entryX + if (iconSide == 1) 0 else (iconSize + iconPadding).toInt()
            val iconX = entryX + if (iconSide == 1) (textWidth + iconPadding).toInt()
            else 0

            //? if 1.8.9 {
            /*RenderHelper.enableGUIStandardItemLighting()
            mc.renderItem.zLevel = 200f
            mc.renderItem.renderItemAndEffectIntoGUI(stack, iconX, itemY)
            mc.renderItem.renderItemOverlayIntoGUI(
                mc.fontRendererObj,
                stack,
                0,
                0,
                ""
            )
            RenderHelper.disableStandardItemLighting()
            *///?} else {
            mcCtx.item(
                stack,
                iconX,
                itemY
            )
            //?}

            //? if 1.8.9 {
            //RenderHelper.disableStandardItemLighting()
            //?}

            //? if forge {
            /*TextRenderer.drawScaledString(
                text,
                textX.toFloat(),
                itemY + mc.fontRendererObj.FONT_HEIGHT / 2f,
                textColor.rgb,
                TextRenderer.TextType.toType(textType),
                1f
            )
            *///?} else {
            val textY = itemY + (
                    16 -
                            //? if 1.8.9 {
                            //mc.fontRendererObj.FONT_HEIGHT
                            //?} else {
                            mc.font.lineHeight
                    //?}
                    ) / 2

            if (textShadow) {
                //? if ornithe {
                /*mc.fontRendererObj.drawString(
                    text,
                    (textX + 1).toFloat(),
                    (textY + 1).toFloat(),
                    textShadowColor.argb,
                    false
                )
                *///?} else {
                mcCtx.text(
                    mc.font,
                    text,
                    textX + 1,
                    textY + 1,
                    textShadowColor.argb,
                    false
                )
                //?}
            }

            //? if ornithe {
            /*mc.fontRendererObj.drawString(
                text,
                textX.toFloat(),
                textY.toFloat(),
                txtColor.argb,
                false
            )
            *///?} else {
            mcCtx.text(
                mc.font,
                text,
                textX,
                textY,
                txtColor.argb,
                false
            )
            //?}

            //?}
        }

        //? if forge {
        //UGraphics.GL.popMatrix()
        //?} elif ornithe {
        //GlStateManager.popMatrix()
        //?} else {
        mcCtx.pose().popMatrix()
        //?}

        actualWidth = contentWidth
        actualHeight = contentHeight
    }

    //? if forge {
    /*override fun getWidth(scale: Float, example: Boolean): Float =
        actualWidth * scale

    override fun getHeight(scale: Float, example: Boolean): Float =
        actualHeight * scale

    override fun shouldShow(): Boolean =
        super.shouldShow() && (HyBedWarsConfig.debugModeEnabled || (HypixelPackets.inBedwars && (showInLobby || !HypixelPackets.inLobby)))
    *///?} else {
    override val width: Float get() = actualWidth
    override val height: Float get() = actualHeight
    override fun minimumSize(): Pair<Float, Float> = actualWidth to actualHeight
    override fun defaultPosition(): Pair<Float, Float> = 0f to 0f
    override fun update(): Boolean = true
    override fun multipleInstancesAllowed(): Boolean = true
    override fun deletable(): Boolean = true
    //?}
}
