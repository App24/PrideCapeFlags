package org.github.app24.pridecapeflags.client.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SplashManager.class)
public class SplashManagerMixin {

    @Inject(method = "getSplash", at = @At("RETURN"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<SplashRenderer> cir){
        if(PrideCapeFlags.IS_PRIDE && ModConfig.showPrideTitle){
            cir.setReturnValue(new SplashRenderer("HAPPY PRIDE"));
        }
    }
}
