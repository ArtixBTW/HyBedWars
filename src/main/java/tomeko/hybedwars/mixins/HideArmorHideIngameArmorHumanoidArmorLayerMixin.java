package tomeko.hybedwars.mixins;

//? if 1.8.9 {
/*
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import tomeko.hybedwars.hide_armor.HideArmor;

@Mixin(LayerArmorBase.class)
abstract class HideIngameArmorHumanoidArmorLayerMixin {
    @Shadow
    public abstract ItemStack getCurrentArmor(EntityLivingBase entityLivingBaseIn, int armorSlot);

    @Inject(method = "renderLayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/entity/layers/LayerArmorBase;getArmorModel(I)Lnet/minecraft/client/model/ModelBase;"), cancellable = true)
    private void hybedwars$cancelArmor(EntityLivingBase entityLivingBaseIn, float f, float g, float partialTicks, float h, float i, float j, float scale, int armorSlot, CallbackInfo ci) {
        if (HideArmor.shouldHideArmor(getCurrentArmor(entityLivingBaseIn, armorSlot)) && !entityLivingBaseIn.isInvisible()) {
            ci.cancel();
        }
    }
}
*///?}
