package org.github.app24.pridecapeflags.client.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.SpriteIconButton;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.gui.PrideCapeFlagsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PauseScreen.class)
public abstract class PauseScreenMixin extends Screen {
    private static final Component PRIDE_CAPE_FLAGS = Component.translatable("pridecapeflags.menu.settings");

    protected PauseScreenMixin(Component title) {
        super(title);
    }

    @Definition(id = "integratedServer", local = @Local(type = IntegratedServer.class, name = "integratedServer"))
    @Expression("integratedServer = ?")
    @Inject(method = "createPauseMenu", at = @At("MIXINEXTRAS:EXPRESSION"), order = -1000)
    private void insertPrideCapeFlagsIconButton(CallbackInfo ci, @Local(name = "iconButtonRow") LinearLayout iconButtonRow) {
        SpriteIconButton playerReportingButton = SpriteIconButton.builder(
                        PRIDE_CAPE_FLAGS, var1x -> this.minecraft.gui.setScreen(new PrideCapeFlagsScreen(this, Minecraft.getInstance().player)), true
                )
                .width(20)
                .sprite(Identifier.fromNamespaceAndPath(PrideCapeFlags.MOD_ID, "pause_menu/cape_editor"), 15, 15)
                .withTootip()
                .build();
        iconButtonRow.addChild(playerReportingButton);
    }
}
