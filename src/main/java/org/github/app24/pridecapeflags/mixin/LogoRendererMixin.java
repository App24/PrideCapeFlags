package org.github.app24.pridecapeflags.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.resources.ResourceLocation;
import org.github.app24.pridecapeflags.Config;
import org.github.app24.pridecapeflags.PrideCapeFlagsMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {
    @Unique
    private static final ResourceLocation PRIDE_LOGO = ResourceLocation.fromNamespaceAndPath(PrideCapeFlagsMod.MODID, "textures/gui/title/pridecraft.png");
    @Unique
    private static final ResourceLocation MINECRAFT_EDITION = ResourceLocation.fromNamespaceAndPath(PrideCapeFlagsMod.MODID,"textures/gui/title/edition.png");

    @WrapOperation(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 0))
    private void renderPrideCraftLogo(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight, Operation<Void> original){
        if(!PrideCapeFlagsMod.IS_PRIDE || !Config.SHOW_PRIDE_TITLE.get()){
            original.call(instance, atlasLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
            return;
        }
        instance.blit(
                PRIDE_LOGO, x, y, uOffset,vOffset,width,height,textureWidth,textureHeight
        );
    }

    @WrapOperation(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lnet/minecraft/resources/ResourceLocation;IIFFIIII)V", ordinal = 1))
    private void renderPrideEdition(GuiGraphics instance, ResourceLocation atlasLocation, int x, int y, float uOffset, float vOffset, int width, int height, int textureWidth, int textureHeight, Operation<Void> original){
        if(!PrideCapeFlagsMod.IS_PRIDE || !Config.SHOW_PRIDE_TITLE.get()){
            original.call(instance, atlasLocation, x, y, uOffset, vOffset, width, height, textureWidth, textureHeight);
            return;
        }
        instance.blit(
                MINECRAFT_EDITION, x, y, uOffset,vOffset,width,height,textureWidth,textureHeight
        );
    }
}
