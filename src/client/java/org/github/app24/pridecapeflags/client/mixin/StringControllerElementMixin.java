package org.github.app24.pridecapeflags.client.mixin;

import dev.isxander.yacl3.gui.controllers.string.StringControllerElement;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StringControllerElement.class)
public class StringControllerElementMixin {
    @Inject(method = "setFocused", at = @At(value = "TAIL"))
    private void setFocused(boolean focused, CallbackInfo ci) {
        if (focused)
            Minecraft.getInstance().onTextInputFocusChange((StringControllerElement) (Object) this, focused);
    }
}
