package org.github.app24.pridecapeflags.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import org.github.app24.pridecapeflags.Constants;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashManager.class)
public class SplashManagerMixin {

    @Inject(method = "getSplash", at = @At("RETURN"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<SplashRenderer> cir){
        if(PrideCapeFlags.IS_PRIDE && PrideCapeFlagsClient.MOD_CONFIG.showPrideTitle()){
            cir.setReturnValue(new SplashRenderer(SplashManager.literalSplash("HAPPY PRIDE")));
        }
    }
}