package org.github.app24.pridecapeflags.client.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.gui.GuiGraphics;
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

    @WrapOperation(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V", ordinal = 0))
    private void renderPrideCraftLogo(GuiGraphics instance, RenderPipeline renderPipeline, Identifier identifier, int i, int j, float f, float g, int k, int l, int m, int n, int o, Operation<Void> original){
        if(!PrideCapeFlags.IS_PRIDE || !ModConfig.showPrideTitle){
            original.call(instance, renderPipeline, identifier, i, j, f, g, k, l, m, n, o);
            return;
        }
        instance.blit(
                RenderPipelines.GUI_TEXTURED, PRIDE_LOGO, i, j, f, g, k, l, m, n, o
        );
    }

    @WrapOperation(method = "renderLogo(Lnet/minecraft/client/gui/GuiGraphics;IFI)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/GuiGraphics;blit(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIFFIIIII)V", ordinal = 1))
    private void renderPrideEdition(GuiGraphics instance, RenderPipeline renderPipeline, Identifier identifier, int i, int j, float f, float g, int k, int l, int m, int n, int o, Operation<Void> original){
        if(!PrideCapeFlags.IS_PRIDE || !ModConfig.showPrideTitle){
            original.call(instance, renderPipeline, identifier, i, j, f, g, k, l, m, n, o);
            return;
        }
        instance.blit(
                RenderPipelines.GUI_TEXTURED, MINECRAFT_EDITION, i, j, f, g, k, l, m, n, o
        );
    }
}
