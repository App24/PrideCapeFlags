package org.github.app24.pridecapeflags.gui.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.ARGB;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.github.app24.pridecapeflags.CapeTexture;
import org.github.app24.pridecapeflags.Constants;
import org.github.app24.pridecapeflags.PlayerSkinBuilder;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.function.Consumer;

public class CapeSelectionWidget extends AbstractWidget {
    private static final Identifier DISABLED_BACKGROUND = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "widget/disabled_cape_background");
    private static final Identifier BACKGROUND = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "widget/cape_background");
    private static final Identifier CAPE_SELECTED = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "widget/cape_selected");
    private static final Identifier ELYTRA_SELECTED = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "widget/elytra_selected");

    private final Minecraft minecraft;

    private final Identifier capeTexture;
    private boolean capeSelected;
    private boolean elytraSelected;
    private Consumer<CapeSelectionWidget> onCapeClick;
    private boolean showElytra;
    private Consumer<CapeSelectionWidget> onElytraClick;

    public CapeSelectionWidget(int x, int y, Component message, Minecraft minecraft, Identifier capeTexture) {
        super(x, y, 64, 64, message);
        this.minecraft = minecraft;
        this.capeTexture = capeTexture;
        this.setTooltip(Tooltip.create(message));
    }

    @Override
    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int i, int i1, float v) {
        graphics.blitSprite(
                RenderPipelines.GUI_TEXTURED,
                this.isActive()? BACKGROUND:DISABLED_BACKGROUND,
                this.getX(),
                this.getY(),
                this.getWidth(),
                this.getHeight(),
                ARGB.white(this.alpha)
        );

        if(capeSelected) {
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    CAPE_SELECTED,
                    this.getX(),
                    this.getY(),
                    this.getWidth(),
                    this.getHeight(),
                    ARGB.white(this.alpha)
            );
        }

        if(elytraSelected) {
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED,
                    ELYTRA_SELECTED,
                    this.getX(),
                    this.getY(),
                    this.getWidth(),
                    this.getHeight(),
                    ARGB.white(this.alpha)
            );
        }

        int size = 24;

        int xP = this.getX();
        int yP = this.getY();

        extractEntityInInventoryFollowsMouse(graphics, xP,yP,xP+64,yP+64, size, 0.0625F / 2f);
        this.handleCursor(graphics);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput graphics) {

    }

    public void setCapeSelected(boolean capeSelected) {
        this.capeSelected = capeSelected;
    }

    public void setElytraSelected(boolean elytraSelected) {
        this.elytraSelected = elytraSelected;
    }

    public Identifier getCapeTexture() {
        return capeTexture;
    }

    public void setShowElytra(boolean showElytra) {
        this.showElytra = showElytra;
    }

    public void setOnCapeClick(Consumer<CapeSelectionWidget> onCapeClick){
        this.onCapeClick = onCapeClick;
    }

    public void setOnElytraClick(Consumer<CapeSelectionWidget> onElytraClick){
        this.onElytraClick = onElytraClick;
    }

    @Override
    public void onClick(MouseButtonEvent event, boolean doubleClick) {
        if (!PrideCapeFlagsClient.checkFlagValid(this.capeTexture)) return;
        if (event.button() == 1) {
            if (this.onCapeClick != null)
                this.onCapeClick.accept(this);
        } else if (event.button() == 3) {
            if (this.onElytraClick != null)
                this.onElytraClick.accept(this);
        }
    }

    @Override
    protected boolean isValidClickButton(MouseButtonInfo buttonInfo) {
        return buttonInfo.button() == 1 || buttonInfo.button() == 3;
    }

    private void extractEntityInInventoryFollowsMouse(final GuiGraphicsExtractor graphics, final int x0, final int y0, final int x1, final int y1, final int size, float offsetY) {
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

        renderState.skin = this.minecraft.player.getSkin();

        var skinBuilder = new PlayerSkinBuilder(renderState.skin);

//        if (this.showCape) {
//            var capeResourceLocation = Identifier.tryParse(capeFlagBox.getValue());
//            var elytraResourceLocation = Identifier.tryParse(elytraFlagBox.getValue());
        var capeResourceLocation = capeTexture;
        var elytraResourceLocation = capeTexture;
            if (capeResourceLocation != null) {
//                if (!PrideCapeFlagsClient.checkFlagValid(capeResourceLocation))
//                    renderState.showCape = false;
                skinBuilder.setCape(new CapeTexture(capeResourceLocation));
                skinBuilder.setElytra(skinBuilder.getCape());
            }
            if (elytraResourceLocation != null) {
                if (!PrideCapeFlagsClient.checkFlagValid(elytraResourceLocation))
                    skinBuilder.setElytra(null);
                else
                    skinBuilder.setElytra(new CapeTexture(elytraResourceLocation));
            }
//        } else {
//            var playerSkin = this.minecraft.getSkinManager().createLookup(this.minecraft.getGameProfile(), false).get();
//            skinBuilder.setCape(playerSkin.cape());
//            skinBuilder.setElytra(playerSkin.elytra());
//        }
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

        graphics.entity(renderState, (float) size, translation, rotation, null, x0, y0, x1, y1);
    }
}
