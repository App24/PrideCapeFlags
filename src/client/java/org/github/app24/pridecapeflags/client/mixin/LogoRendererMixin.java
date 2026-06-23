package org.github.app24.pridecapeflags.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LogoRenderer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.config.ModConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LogoRenderer.class)
public class LogoRendererMixin {
    @Unique
    private static final Identifier PRIDE_LOGO = Identifier.fromNamespaceAndPath(PrideCapeFlags.MOD_ID, "textures/gui/title/pridecraft.png");
    @Unique
    private static final Identifier MINECRAFT_EDITION = Identifier.fromNamespaceAndPath(PrideCapeFlags.MOD_ID,"textures/gui/title/edition.png");

    @WrapOperation(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V", ordinal = 0))
    private void renderPrideCraftLogo(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color, Operation<Void> original){
        if(!PrideCapeFlags.IS_PRIDE || !ModConfig.showPrideTitle){
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, textureWidth, textureHeight, color);
            return;
        }
        instance.blit(
                RenderPipelines.GUI_TEXTURED, PRIDE_LOGO, x, y, u,v,width,height,textureWidth,textureHeight,color
        );
    }

    @WrapOperation(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V", ordinal = 1))
    private void renderPrideEdition(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier texture, int x, int y, float u, float v, int width, int height, int textureWidth, int textureHeight, int color, Operation<Void> original){
        if(!PrideCapeFlags.IS_PRIDE || !ModConfig.showPrideTitle){
            original.call(instance, renderPipeline, texture, x, y, u, v, width, height, textureWidth, textureHeight, color);
            return;
        }
        instance.blit(
                RenderPipelines.GUI_TEXTURED, MINECRAFT_EDITION, x, y, u,v,width,height,textureWidth,textureHeight,color
        );
    }
}
