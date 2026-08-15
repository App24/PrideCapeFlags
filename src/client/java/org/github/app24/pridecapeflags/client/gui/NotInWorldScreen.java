package org.github.app24.pridecapeflags.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;

public class NotInWorldScreen extends ConfirmScreen {
    private final Screen parent;

    public NotInWorldScreen(Minecraft client, Screen parent) {
        super(
                //~ if >=26.2 'setScreen' -> 'gui.setScreen'
                _ -> client.gui.setScreen(parent),
                //~ if >=26.2 'net.minecraft.ChatFormatting' -> 'TextColor'
                Component.translatable("pridecapeflags.not_in_world.title").withColor(TextColor.RED),
                Component.translatable("pridecapeflags.not_in_world")
        );
        this.parent = parent;
    }

    @Override
    protected void addButtons(LinearLayout layout) {
        layout.addChild(Button.builder(CommonComponents.GUI_OK, _ -> onClose()).build());
    }

    @Override
    public void onClose() {
        //~ if >=26.2 'setScreen' -> 'gui.setScreen'
        minecraft.gui.setScreen(parent);
    }
}
