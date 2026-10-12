package org.github.app24.pridecapeflags.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;

public class PrideCapeConfigScreen extends Screen {
    private final Screen parent;
    public static final Component GUI_APPLY = Component.translatable("menu.multiplayerOptions.applyChanges");

    private boolean showPrideTitle;

    private Button saveButton;

    public final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);

    public PrideCapeConfigScreen(Screen parent) {
        super(Component.translatable("pridecapeflags.menu.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.showPrideTitle = PrideCapeFlagsClient.MOD_CONFIG.showPrideTitle();

        this.addTitle();
        this.addContents();
        this.addFooter();
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

    protected void addTitle() {
        this.layout.addTitleHeader(this.title, this.font);
    }

    private void addContents() {
        var linear = this.layout.addToContents(LinearLayout.vertical().spacing(8));

        linear.addChild(CycleButton.onOffBuilder(this.showPrideTitle).create(0,0, 250, 20, Component.translatable("pridecapeflags.menu.config.showPrideTitle"), (cycleButton, value) -> {
            this.showPrideTitle = value;

            this.saveButton.active = true;
        }));

        linear.addChild(Button.builder(Component.translatable("pridecapeflags.menu.config.openSelection"), button-> this.minecraft.gui.setScreen(new PrideCapeSelectionScreen(this))).width(250).build());
    }

    protected void addFooter() {
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));

        this.saveButton = footer.addChild(Button.builder(GUI_APPLY, button -> this.saveChanges()).width(200).build());
        footer.addChild(Button.builder(CommonComponents.GUI_BACK, button -> this.onClose()).width(200).build());

        this.saveButton.active = false;
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
    }

    private void saveChanges() {
        PrideCapeFlagsClient.MOD_CONFIG.setShowPrideTitle(this.showPrideTitle);

        PrideCapeFlagsClient.MOD_CONFIG.saveConfig();
        PrideCapeFlagsClient.MOD_CONFIG.updateConfig();

        this.saveButton.active = false;
    }
}
