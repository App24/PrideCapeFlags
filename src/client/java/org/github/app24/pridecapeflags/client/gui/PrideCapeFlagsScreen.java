package org.github.app24.pridecapeflags.client.gui;

import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.layouts.FrameLayout;
import net.minecraft.client.gui.layouts.LinearLayout;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.CapeTexture;
import org.github.app24.pridecapeflags.client.PlayerSkinBuilder;
import org.github.app24.pridecapeflags.client.PrideCapeFlagsClient;
import org.github.app24.pridecapeflags.client.config.ModConfig;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class PrideCapeFlagsScreen extends Screen {
    public static final Component CAPE_BOX = Component.translatable("pridecapeflags.config.menu.prideCapeFlag");
    public static final Component ELYTRA_BOX = Component.translatable("pridecapeflags.config.menu.elytraCapeFlag");
    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(PrideCapeFlags.MOD_ID, "textures/gui/cape_selection_bg.png");
    public static final Component GUI_APPLY = Component.translatable("menu.multiplayerOptions.applyChanges");

    private final Screen previousScreen;
    private final FrameLayout footerFrame = new FrameLayout();
    private EditBox capeFlagBox;
    private EditBox elytraFlagBox;
    private CycleButton<Boolean> showCapeButton;
    private CycleButton<Boolean> useElytraButton;
    private boolean showCape;
    private boolean showElytra;
    private boolean useElytraCape;
    private Button saveButton;

    private static final int ERROR_TEXT_COLOR = -2142128;

    public PrideCapeFlagsScreen(Screen previousScreen, LocalPlayer localPlayer) {
        super(Component.translatable("pridecapeflags.config.menu.title"));

        this.previousScreen = previousScreen;
        this.showElytra = localPlayer != null && localPlayer.getItemBySlot(EquipmentSlot.CHEST).is(Items.ELYTRA);
    }

    public PrideCapeFlagsScreen(Screen previousScreen) {
        this(previousScreen, null);
    }

    @Override
    protected void init() {

        if (this.minecraft.player == null) {
            this.minecraft.setScreen(new NotInWorldScreen(this.minecraft, previousScreen));
            return;
        }

        var capeFlagBoxX = this.width / 2 - 150 / 2 + 40;
        int yP = this.height / 2 - 25;

        this.capeFlagBox = new EditBox(this.font, capeFlagBoxX, yP, 150, 20, this.capeFlagBox, CAPE_BOX);
        this.elytraFlagBox = new EditBox(this.font, capeFlagBoxX, yP + 70, 150, 20, this.elytraFlagBox, ELYTRA_BOX);


        {
            var resetButton = Button.builder(Component.literal("R"), button -> {
                        this.capeFlagBox.setValue(ModConfig.prideCapeFlag);
                    })
                    .size(20, 20).build();

            resetButton.setPosition(capeFlagBoxX + 150 + 5, yP);


            this.capeFlagBox.setResponder(s -> {
                resetButton.active = !this.capeFlagBox.getValue().equals(ModConfig.prideCapeFlag);
                updateApplyButton();
            });

            this.addRenderableWidget(resetButton);
        }


        {
            var resetButton = Button.builder(Component.literal("R"), button -> {
                        this.elytraFlagBox.setValue(ModConfig.elytraPrideCapeFlag);
                    })
                    .size(20, 20).build();

            resetButton.setPosition(capeFlagBoxX + 150 + 5, yP + 70);

            this.elytraFlagBox.setResponder(s -> {
                resetButton.active = !this.elytraFlagBox.getValue().equals(ModConfig.elytraPrideCapeFlag);
                updateApplyButton();
            });

            this.addRenderableWidget(resetButton);
        }

        this.capeFlagBox.setValue(ModConfig.prideCapeFlag);
        this.showCape = ModConfig.showPrideCape;
        this.elytraFlagBox.setValue(ModConfig.elytraPrideCapeFlag);

        this.useElytraCape = ModConfig.useElytraCape;

        AbstractWidget switchToElytra = CycleButton.onOffBuilder(this.showElytra).create((this.width - 272) / 2, this.height / 2 + 18 + 20, 92, 20, Component.translatable("pridecapeflags.config.menu.showElytra"), (button, value) -> {
            this.showElytra = value;
        });


        {
            var resetButton = Button.builder(Component.literal("R"), button -> {
                        this.useElytraCape = ModConfig.useElytraCape;
                        useElytraButton.setValue(this.useElytraCape);
                        button.active = false;
                        this.elytraFlagBox.setEditable(this.useElytraCape);
                        updateApplyButton();
                    })
                    .size(20, 20).build();

            resetButton.active = false;

            resetButton.setPosition(capeFlagBoxX + 150 + 5, yP + 30);

            useElytraButton = CycleButton.onOffBuilder(useElytraCape).create(capeFlagBoxX, yP + 30, 150, 20, Component.translatable("pridecapeflags.midnightconfig.useElytraCape"), (button, value) -> {
                this.useElytraCape = value;
                resetButton.active = this.useElytraCape != ModConfig.useElytraCape;
                this.elytraFlagBox.setEditable(this.useElytraCape);
                updateApplyButton();
            });

            this.addRenderableWidget(resetButton);
        }
        this.elytraFlagBox.setEditable(this.useElytraCape);

        {
            var resetButton = Button.builder(Component.literal("R"), button -> {
                this.showCape = ModConfig.showPrideCape;
                showCapeButton.setValue(this.showCape);
                button.active = false;
                updateApplyButton();
            }).size(20, 20).build();

            resetButton.active = false;

            resetButton.setPosition(capeFlagBoxX + 150 + 5, yP - 40);

            showCapeButton = CycleButton.onOffBuilder(this.showCape).create(capeFlagBoxX, yP - 40, 150, 20, Component.translatable("pridecapeflags.config.menu.showCape"), (button, value) -> {
                this.showCape = value;
                resetButton.active = this.showCape != ModConfig.showPrideCape;
                updateApplyButton();
            });

            this.addRenderableWidget(resetButton);
        }

        this.addWidget(this.capeFlagBox);
        this.setInitialFocus(this.capeFlagBox);
        this.addWidget(this.elytraFlagBox);

        int textWidth = this.font.width(CAPE_BOX);
        this.addRenderableWidget(new StringWidget(capeFlagBoxX, yP - 10, textWidth, 9, CAPE_BOX, this.font));

        textWidth = this.font.width(ELYTRA_BOX);
        this.addRenderableWidget(new StringWidget(capeFlagBoxX, yP + 60, textWidth, 9, ELYTRA_BOX, this.font));

        this.addRenderableWidget(switchToElytra);
        this.addRenderableWidget(useElytraButton);
        this.addRenderableWidget(showCapeButton);

        textWidth = this.font.width(this.title);
        this.addRenderableWidget(new StringWidget(this.width / 2 - textWidth / 2, 40, textWidth, 9, this.title, this.font));

        this.footerFrame.defaultChildLayoutSetting().align(0.5F, 0.5F);

        LinearLayout footer = footerFrame.addChild(LinearLayout.horizontal().spacing(8));

        footer.addChild(Button.builder(CommonComponents.GUI_CANCEL, button -> this.onClose()).width(150).build());
        saveButton = footer.addChild(Button.builder(CommonComponents.GUI_BACK, button -> this.saveSettings()).width(150).build());

        footerFrame.visitWidgets(this::addRenderableWidget);

        repositionFooter();
    }

    @Override
    protected void repositionElements() {
        super.repositionElements();
    }

    private void repositionFooter() {
        var footerHeight = 33;

        footerFrame.setMinWidth(this.width);
        footerFrame.setMinHeight(footerHeight);
        footerFrame.arrangeElements();
        footerFrame.setY(this.height - footerHeight);
    }

    @Override
    public void onClose() {
        this.minecraft.setScreen(this.previousScreen);
    }

    private void saveSettings() {
        if (hasSettingsChanged()) {
            ModConfig.prideCapeFlag = this.capeFlagBox.getValue();
            ModConfig.elytraPrideCapeFlag = this.elytraFlagBox.getValue();
            ModConfig.useElytraCape = this.useElytraCape;
            ModConfig.showPrideCape = this.showCape;
            MidnightConfig.write(PrideCapeFlags.MOD_ID);
        }

        this.onClose();
    }

    private boolean hasSettingsChanged() {
        return this.useElytraCape != ModConfig.useElytraCape ||
                this.showCape != ModConfig.showPrideCape ||
                !this.capeFlagBox.getValue().equals(ModConfig.prideCapeFlag) ||
                !this.elytraFlagBox.getValue().equals(ModConfig.elytraPrideCapeFlag);
    }

    private void updateApplyButton() {
        if (saveButton == null) return;
        saveButton.setMessage(hasSettingsChanged() ? GUI_APPLY : CommonComponents.GUI_BACK);
    }

    @Override
    public boolean keyPressed(KeyEvent input) {
        return super.keyPressed(input) || this.capeFlagBox.keyPressed(input) || this.elytraFlagBox.keyPressed(input);
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.capeFlagBox.charTyped(input) || this.elytraFlagBox.charTyped(input);
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics, int i, int j, float f) {
        super.renderBackground(guiGraphics, i, j, f);

        int xP = this.width / 2 - 90;
        int yP = this.height / 2 + 18;

        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, BACKGROUND, (this.width - 272) / 2, (this.height - 138) / 2, 0, 0, 92, 104, 512, 512);

        extractEntityInInventoryFollowsMouse(guiGraphics, xP - 38, yP - 79, xP + 38, yP + 9, 50, 0.0625F / 2f);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        super.render(guiGraphics, i, j, f);
        this.capeFlagBox.render(guiGraphics, i, j, f);
        this.elytraFlagBox.render(guiGraphics, i, j, f);
    }

    private void extractEntityInInventoryFollowsMouse(final GuiGraphics graphics, final int x0, final int y0, final int x1, final int y1, final int size, float offsetY) {
        float xAngle = 150;
        float yAngle = -15;
        Quaternionf rotation = (new Quaternionf()).rotateZ((float) Math.PI);
        Quaternionf xRotation = (new Quaternionf()).rotateX(yAngle * ((float) Math.PI / 180F));
        rotation.mul(xRotation);
        AvatarRenderState renderState = new AvatarRenderState();

        float offsetX = 0;
        renderState.chestEquipment = this.showElytra ? Items.ELYTRA.getDefaultInstance() : ItemStack.EMPTY;
        renderState.showCape = true;
        renderState.elytraRotX = (float) (Math.PI / 12);
        renderState.elytraRotZ = (float) (-Math.PI / 12);

        var skinBuilder = new PlayerSkinBuilder(renderState.skin);

        if (this.showCape) {
            var capeResourceLocation = Identifier.tryParse(capeFlagBox.getValue());
            var elytraResourceLocation = Identifier.tryParse(elytraFlagBox.getValue());
            if (capeResourceLocation != null) {
                if (!PrideCapeFlagsClient.checkFlagValid(capeResourceLocation))
                    renderState.showCape = false;
                skinBuilder.setCape(new CapeTexture(capeResourceLocation));
                skinBuilder.setElytra(skinBuilder.getCape());
            }
            if (elytraResourceLocation != null && useElytraCape) {
                if (!PrideCapeFlagsClient.checkFlagValid(elytraResourceLocation))
                    skinBuilder.setElytra(null);
                else
                    skinBuilder.setElytra(new CapeTexture(elytraResourceLocation));
            }
        } else {
            var playerSkin = this.minecraft.getSkinManager().createLookup(this.minecraft.getGameProfile(), false).get();
            skinBuilder.setCape(playerSkin.cape());
            skinBuilder.setElytra(playerSkin.elytra());
        }
        renderState.skin = skinBuilder.build();

        renderState.bodyRot = 180.0F + xAngle;
        renderState.yRot = 0;
        renderState.xRot = 0.0F;

        renderState.boundingBoxWidth = 0.6F;
        renderState.boundingBoxHeight = 1.8F;

        renderState.boundingBoxWidth /= renderState.scale;
        renderState.boundingBoxHeight /= renderState.scale;
        renderState.scale = 1.0F;

        Vector3f translation = new Vector3f(offsetX, renderState.boundingBoxHeight / 2.0F + offsetY, 0.0F);

        graphics.submitEntityRenderState(renderState, (float) size, translation, rotation, null, x0, y0, x1, y1);
    }
}
