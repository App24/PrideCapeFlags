package org.github.app24.pridecapeflags.mixin;

import net.minecraft.client.gui.components.SplashRenderer;
import net.minecraft.client.resources.SplashManager;
import org.github.app24.pridecapeflags.Config;
import org.github.app24.pridecapeflags.PrideCapeFlagsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Locale;

@Mixin(SplashManager.class)
public class SplashManagerMixin {

    @Inject(method = "getSplash", at = @At("RETURN"), cancellable = true)
    private void getSplash(CallbackInfoReturnable<SplashRenderer> cir){
        if(PrideCapeFlagsMod.IS_PRIDE && Config.SHOW_PRIDE_TITLE.get()){
            cir.setReturnValue(new SplashRenderer(SplashManager.literalSplash("HAPPY PRIDE")));
        }
    }
}
