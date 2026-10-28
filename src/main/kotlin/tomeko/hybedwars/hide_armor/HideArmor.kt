package tomeko.hybedwars.hide_armor

//? if 1.8.9 {
/*import net.minecraft.item.ItemArmor
import net.minecraft.item.ItemStack
import tomeko.hybedwars.config.HyBedWarsConfig
import tomeko.hybedwars.utils.HypixelPackets
import java.util.Locale

object HideArmor {
    @JvmStatic
    fun shouldHideArmor(stack: ItemStack?): Boolean {
        val item = stack?.item

        if (item !is ItemArmor)
            return false

        if (!HyBedWarsConfig.debugModeEnabled) {
            val mode = HypixelPackets.currentMode ?: return false

            return HyBedWarsConfig.hideArmor
                    // Don't hide armor if we're in a lobby,
                    // I think Leather Armor can show up in it sometimes?
                    && HypixelPackets.inGame
                    && HypixelPackets.inBedwars
                    && !mode.contains("LUCKY")
                    && item.armorMaterial == ItemArmor.ArmorMaterial.LEATHER
        } else {
            return item.armorMaterial == ItemArmor.ArmorMaterial.LEATHER
        }
    }
}
*///?}
