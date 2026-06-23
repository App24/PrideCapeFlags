package org.github.app24.pridecapeflags.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {
    @Unique
    private static final ResourceLocation PRIDE_LOGO = new ResourceLocation(PrideCapeFlags.MOD_ID, "textures/gui/title/pridecraft.png");
    @Unique
    private static final ResourceLocation MINECRAFT_EDITION = new ResourceLocation(PrideCapeFlags.MOD_ID,"textures/gui/title/edition.png");

    @WrapOperation(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 0))
    private void renderPrideCraftLogo(GuiGraphics instance, ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n, Operation<Void> original){
        if(!PrideCapeFlags.IS_PRIDE || !ModConfig.showPrideTitle){
            original.call(instance, resourceLocation, i, j, f, g, k, l, m, n);
            return;
        }
        instance.blit(
                PRIDE_LOGO, i, j, f, g, k, l, m, n
        );
    }

    @WrapOperation(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 1))
    private void renderPrideEdition(GuiGraphics instance, ResourceLocation resourceLocation, int i, int j, float f, float g, int k, int l, int m, int n, Operation<Void> original){
        if(!PrideCapeFlags.IS_PRIDE || !ModConfig.showPrideTitle){
            original.call(instance, resourceLocation, i, j, f, g, k, l, m, n);
            return;
        }
        instance.blit(
                MINECRAFT_EDITION, i, j, f, g, k, l, m, n
        );
    }
}
