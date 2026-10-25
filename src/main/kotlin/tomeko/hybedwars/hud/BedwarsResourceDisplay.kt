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
        private const val SUBCATEGORY_RESOURCES = "Resources"
        private const val CATEGORY_BACKGROUND = "Background"
        //?}
    }

    //? if forge {
    /*@Dropdown(
        name = "Text Type",
        options = ["No Shadow", "Shadow", "Full Shadow"]
    )
    var textType = 0

    @Color(name = "Text Color")
    var textColor: OneColor = OneColor(255, 255, 255)
    *///?} else {
    @Color(
        title = "Text Color",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
    )
    var txtColor = PolyColor(0xFFFFFFFF.toInt())

    @Switch(
        title = "Text Shadow",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
    )
    var textShadow = true

    @Color(
        title = "Text Shadow Color",
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
    )
    var textShadowColor = PolyColor(0xFF000000.toInt())
    //?}

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Mode",
        options = ["Vertical", "Horizontal"],
        //? if !forge {
        category = CATEGORY_GENERAL,
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
            "Item Padding",
        min = 0f,
        max = 10f,
        //? if !forge {
        step = 0.1f,
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_GENERAL
        //?}
    )
    var itemPadding = 5f

    @Slider(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Icon Padding",
        min = 0f,
        max = 10f,
        //? if !forge {
        step = 0.1f,
        category = CATEGORY_GENERAL,
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
            "Padding Side",
        options = ["Left", "Middle", "Right"],
        //? if !forge {
        category = CATEGORY_GENERAL,
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
            "Show Iron",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showIron = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show Gold"
        //? if !forge {
        , category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
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
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
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
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
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
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
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
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
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
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showTotal = true

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Total Side",
        options = ["Left", "Right"],
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var totalSide = 1

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show When Zero",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showWhenZero = true

    @Switch(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Show In Lobby",
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var showInLobby = true

    @Dropdown(
        //? if forge {
        //name =
        //?} else {
        title =
            //?}
            "Icon Side",
        options = ["Left", "Right"],
        //? if !forge {
        category = CATEGORY_GENERAL,
        subcategory = SUBCATEGORY_RESOURCES
        //?}
    )
    var iconSide = 0

    //? if !forge {
    @Switch(
        title = "Show Background",
        category = CATEGORY_BACKGROUND
    )
    var background = false

    @Color(
        title = "Background Color",
        category = CATEGORY_BACKGROUND
    )
    var backgroundColor = PolyColor(0x80000000.toInt())

    @Slider(
        title = "Background Radius",
        min = 0f,
        max = 10f,
        step = 0.1f,
        category = CATEGORY_BACKGROUND
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
        val offset = iconSize + itemPadding

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

            return (showWhenZero ||
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

        fun String.appendAmount(amount: String): String =
            this + (if (this.isNotEmpty()) " " else "") + amount

        fun String.appendTotal(inventoryAmount: Int, enderChestAmount: Int): String =
            this.appendAmount(
                if (showInventory || showEnderChest)
                    "(${inventoryAmount + enderChestAmount})"
                else
                    "${inventoryAmount + enderChestAmount}"
            )

        fun getText(item: Item): String {
            val inventoryAmount = ItemTracker.inventory[item] ?: 0
            val enderChestAmount = ItemTracker.enderChest[item] ?: 0

            var text = ""
            if (totalSide == 0 && showTotal) {
                text = text.appendTotal(inventoryAmount, enderChestAmount)
            }

            if (showInventory && showEnderChest) {
                text = text.appendAmount("$inventoryAmount + $enderChestAmount")
            } else if (showInventory) {
                text = text.appendAmount(inventoryAmount.toString())
            } else if (showEnderChest) {
                text = text.appendAmount(enderChestAmount.toString())
            }

            if (totalSide == 1 && showTotal) {
                text = text.appendTotal(inventoryAmount, enderChestAmount)
            }

            return text
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
            if (horizontal) entryWidths.sum() + (size - 1) * itemPadding
            else entryWidths.maxOrNull() ?: 0f
        val contentHeight: Float =
            if (horizontal) iconSize
            else size * offset - itemPadding

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
                cursorX += entryWidth + itemPadding
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