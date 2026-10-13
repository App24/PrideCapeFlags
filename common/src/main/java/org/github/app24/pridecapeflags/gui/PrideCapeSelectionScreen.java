package org.github.app24.pridecapeflags.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.Constants;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;
import org.github.app24.pridecapeflags.gui.widgets.CapeSelectionWidget;
import org.github.app24.pridecapeflags.gui.widgets.PrideCapeList;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PrideCapeSelectionScreen extends Screen {
    public final HeaderAndFooterLayout layout = new HeaderAndFooterLayout(this);
    private final Screen lastScreen;
    protected PrideCapeList list;
    private int elytraToggleTicker;
    private List<CapeSelectionWidget> capeWidgets;
    private boolean showElytra;
    private boolean useElytraCape;
    private boolean showCape;
    public static final Component GUI_APPLY = Component.translatable("menu.multiplayerOptions.applyChanges");
    private Button saveButton;

    private CapeSelectionWidget currentSelectedCape, currentSelectedElytraCape;

    public PrideCapeSelectionScreen(Screen lastScreen) {
        super(Component.translatable("pridecapeflags.menu.selection.title"));

        this.lastScreen = lastScreen;
    }

    @Override
    protected void init() {
        if (this.minecraft.player == null) {
            this.minecraft.gui.setScreen(new NotInWorldScreen(this.minecraft, this.lastScreen));
            return;
        }

        this.showCape = PrideCapeFlagsClient.MOD_CONFIG.showPrideCape();
        this.useElytraCape = PrideCapeFlagsClient.MOD_CONFIG.useElytraCape();

        this.addTitle();
        this.addContents();
        this.addFooter();
        this.layout.visitWidgets(this::addRenderableWidget);
        this.repositionElements();
    }

//    private void addExtraSettings(){
//        int pY = this.layout.getHeaderHeight();
//
//        var useElytraButton = CycleButton.onOffBuilder(useElytraCape).create(20, pY+10, 150, 20, Component.translatable("pridecapeflags.menu.useElytraCape"), (button, value) -> {
//            this.useElytraCape = value;
//        });
//
//        var showCapeButton = CycleButton.onOffBuilder(this.showCape).create(20, pY+35, 150, 20, Component.translatable("pridecapeflags.menu.showCape"), (button, value) -> {
//            this.showCape = value;
//        });
//
//        this.layout.addToContents(useElytraButton, layoutSettings -> layoutSettings.align(0,0));
//        this.layout.addToContents(showCapeButton, layoutSettings -> layoutSettings.align(0,0.01f));
//    }

    protected void addTitle() {
        this.layout.addTitleHeader(this.title, this.font);
    }

    private void addContents() {
        this.list = this.layout.addToContents(new PrideCapeList(this.minecraft, this.width, this));
        this.addOptions();
    }

    protected void addOptions() {
        addHeader();

        capeWidgets = new ArrayList<>();

        Minecraft.getInstance().getResourceManager().listResources("flags", v -> true).forEach((identifier, resource) -> {
            if(!identifier.getPath().endsWith(".png")) return;
            String flagName = identifier.getPath().replace("flags/", "").replace(".png", "");
            Identifier id = Identifier.fromNamespaceAndPath(identifier.getNamespace(), flagName);
            var capeSelectionWidget = new CapeSelectionWidget(0, 0, Component.translatable(identifier.getNamespace() + ".flag." + flagName), this.minecraft, id);
            if (id.toString().equals(PrideCapeFlagsClient.MOD_CONFIG.getPrideCapeFlag()))
                setCurrentSelectedCape(capeSelectionWidget);
            if (id.toString().equals(PrideCapeFlagsClient.MOD_CONFIG.getElytraCapeFlag()))
                setCurrentSelectedElytraCape(capeSelectionWidget);
            capeSelectionWidget.setOnCapeClick(widget -> {
                this.saveButton.active = true;
                this.setCurrentSelectedCape(widget);
            });
            capeSelectionWidget.setOnElytraClick(widget -> {
                this.saveButton.active = true;
                this.setCurrentSelectedElytraCape(widget);
            });
            capeWidgets.add(capeSelectionWidget);
        });

        boolean capeTextureLoaded = this.currentSelectedCape != null;
        boolean elytraTextureLoaded = this.currentSelectedElytraCape != null;

        if (!elytraTextureLoaded) {
            var capeSelectionWidget = new CapeSelectionWidget(0, 0, Component.translatable(Constants.MOD_ID + ".missing_flag"), this.minecraft, Identifier.tryParse(PrideCapeFlagsClient.MOD_CONFIG.getElytraCapeFlag()));
            capeSelectionWidget.active = false;
            setCurrentSelectedElytraCape(capeSelectionWidget);
            capeWidgets.addFirst(capeSelectionWidget);
        }

        if (!capeTextureLoaded) {
            var capeSelectionWidget = new CapeSelectionWidget(0, 0, Component.translatable(Constants.MOD_ID + ".missing_flag"), this.minecraft, Identifier.tryParse(PrideCapeFlagsClient.MOD_CONFIG.getPrideCapeFlag()));
            capeSelectionWidget.active = false;
            setCurrentSelectedCape(capeSelectionWidget);
            capeWidgets.addFirst(capeSelectionWidget);
        }

        this.list.addCapeWidgets(capeWidgets);
    }

    private void addHeader() {
        List<AbstractWidget> widgets = new ArrayList<>();

        var useElytraButton = CycleButton.onOffBuilder(this.useElytraCape).create(0, 0, 101, 20, Component.translatable("pridecapeflags.menu.useElytraCape"), (button, value) -> {
            this.useElytraCape = value;
            this.saveButton.active = true;
        });

        var showCapeButton = CycleButton.onOffBuilder(this.showCape).create(0, 0, 101, 20, Component.translatable("pridecapeflags.menu.showCape"), (button, value) -> {
            this.showCape = value;
            this.saveButton.active = true;
        });

        widgets.add(showCapeButton);
        widgets.add(useElytraButton);

        this.list.addHeader(widgets);

        this.list.addText(Component.translatable("pridecapeflags.menu.selection.leftClick"), this.font, 9);
        this.list.addText(Component.translatable("pridecapeflags.menu.selection.rightClick"), this.font, 9);
    }

    @Override
    public void tick() {
        super.tick();
        this.elytraToggleTicker++;
        if(this.elytraToggleTicker >= 30) {
            showElytra = !showElytra;
            if (this.capeWidgets != null) {
                this.capeWidgets.forEach(capeSelectionWidget -> {
                    capeSelectionWidget.setShowElytra(showElytra);
                });
            }
            this.elytraToggleTicker = 0;
        }
    }

    private void setCurrentSelectedCape(CapeSelectionWidget selectedCape) {
        if(this.currentSelectedCape != null){
            this.currentSelectedCape.setCapeSelected(false);
        }

        this.currentSelectedCape = selectedCape;

        if(this.currentSelectedCape != null){
            this.currentSelectedCape.setCapeSelected(true);
        }
    }

    private void setCurrentSelectedElytraCape(CapeSelectionWidget selectedCape) {
        if(this.currentSelectedElytraCape != null){
            this.currentSelectedElytraCape.setElytraSelected(false);
        }

        this.currentSelectedElytraCape = selectedCape;

        if(this.currentSelectedElytraCape != null){
            this.currentSelectedElytraCape.setElytraSelected(true);
        }
    }

    protected void addFooter() {
        LinearLayout footer = this.layout.addToFooter(LinearLayout.horizontal().spacing(8));

        this.saveButton = footer.addChild(Button.builder(GUI_APPLY, button -> this.saveChanges()).width(200).build());
        footer.addChild(Button.builder(CommonComponents.GUI_BACK, button -> this.onClose()).width(200).build());

        this.saveButton.active = false;
    }

    @Override
    protected void repositionElements() {
        this.layout.arrangeElements();
        if (this.list != null) {
            this.list.updateSize(this.width, this.layout);
        }
    }

    @Override
    public void onClose() {
        this.minecraft.gui.setScreen(this.lastScreen);
    }

    private void saveChanges(){
        PrideCapeFlagsClient.MOD_CONFIG.setPrideCapeFlag(currentSelectedCape.getCapeTexture().toString());
        PrideCapeFlagsClient.MOD_CONFIG.setElytraCapeFlag(currentSelectedElytraCape.getCapeTexture().toString());
        PrideCapeFlagsClient.MOD_CONFIG.setUseElytraCape(this.useElytraCape);
        PrideCapeFlagsClient.MOD_CONFIG.setShowPrideCape(this.showCape);
        PrideCapeFlagsClient.MOD_CONFIG.saveConfig();
        PrideCapeFlagsClient.MOD_CONFIG.updateConfig();

        this.saveButton.active = false;
    }
}
