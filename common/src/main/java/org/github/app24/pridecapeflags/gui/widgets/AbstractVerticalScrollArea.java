package org.github.app24.pridecapeflags.gui.widgets;

import com.mojang.blaze3d.platform.cursor.CursorTypes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.github.app24.pridecapeflags.Constants;
import org.jspecify.annotations.Nullable;

public abstract class AbstractVerticalScrollArea extends AbstractWidget {
    public static final int SCROLLBAR_HEIGHT = 6;
    private static final int SCROLLBAR_MIN_WIDTH = 32;
    private static final Identifier SCROLLER_SPRITE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "widget/scroller");
    private static final Identifier SCROLLER_BACKGROUND_SPRITE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "widget/scroller_background");
    private final ScrollbarSettings scrollbarSettings;
    private double scrollAmount;
    private boolean scrolling;

    public AbstractVerticalScrollArea(int x, int y, int width, int height, Component message, ScrollbarSettings scrollbarSettings) {
        super(x, y, width, height, message);
        this.scrollbarSettings = scrollbarSettings;
    }

    @Override
    public boolean mouseScrolled(double mx, double my, double scrollX, double scrollY) {
        if (!this.visible) {
            return false;
        }

        this.setScrollAmount(this.scrollAmount() - scrollY * this.scrollRate());
        return true;
    }

    @Override
    public boolean mouseDragged(MouseButtonEvent event, double dx, double dy) {
        if (this.scrolling) {
            if (event.x() < this.getX()) {
                this.setScrollAmount(0.0);
            } else if (event.x() > this.getRight()) {
                this.setScrollAmount(this.maxScrollAmount());
            } else {
                double max = Math.max(1, this.maxScrollAmount());
                int barWidth = this.scrollerWidth();
                double yDragScale = Math.max(1.0, max / (this.width - barWidth));
                this.setScrollAmount(this.scrollAmount() + dx * yDragScale);
            }

            return true;
        } else {
            return super.mouseDragged(event, dx, dy);
        }
    }

    @Override
    public void onRelease(MouseButtonEvent event) {
        this.scrolling = false;
    }

    public double scrollAmount() {
        return this.scrollAmount;
    }

    public void setScrollAmount(double scrollAmount) {
        this.scrollAmount = Mth.clamp(scrollAmount, 0.0, this.maxScrollAmount());
    }

    public boolean updateScrolling(MouseButtonEvent event) {
        this.scrolling = this.scrollable() && this.isValidClickButton(event.buttonInfo()) && this.isOverScrollbar(event.x(), event.y());
        return this.scrolling;
    }

    protected boolean isOverScrollbar(double x, double y) {
        return x >= this.scrollBarX() && x < this.getRight() && y >= this.getY() && y <= this.scrollBarY()+ this.scrollbarHeight();
    }

    public void refreshScrollAmount() {
        this.setScrollAmount(this.scrollAmount);
    }

    public int maxScrollAmount() {
        return Math.max(0, this.contentWidth() - this.width);
    }

    protected boolean scrollable() {
        return this.maxScrollAmount() > 0;
    }

    public int scrollbarHeight() {
        return this.scrollbarSettings.scrollbarHeight();
    }

    protected int scrollerWidth(){
        return Mth.clamp((int)((float)(this.width*this.width)/this.contentWidth()), SCROLLBAR_MIN_WIDTH, this.width-8);
    }

    protected int scrollBarX() {
        return this.maxScrollAmount() == 0 ? this.getX():
                Math.max(this.getX(), (int)this.scrollAmount * (this.width - this.scrollerWidth()) / this.maxScrollAmount() + this.getX());
    }

    public int scrollBarY() {
        return this.getBottom() - this.scrollbarHeight();
    }

    protected double scrollRate() {
        return this.scrollbarSettings.scrollRate();
    }

    protected void extractScrollbar(GuiGraphicsExtractor graphics, int mouseX, int mouseY) {
        int scrollbarX = this.scrollBarX();
//        int scrollerHeight = this.scrollerHeight();
        int scrollerWidth = this.scrollerWidth();
        int scrollerY = this.scrollBarY();
        if (!this.scrollable() && this.scrollbarSettings.disabledScrollerSprite() != null) {
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED, this.scrollbarSettings.backgroundSprite(), this.getX(), scrollerY, this.getWidth(), this.scrollbarHeight()
            );
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED, this.scrollbarSettings.disabledScrollerSprite(), this.getX(), scrollerY, scrollerWidth, this.scrollbarHeight()
            );
            if (this.isOverScrollbar(mouseX, mouseY)) {
                graphics.requestCursor(CursorTypes.NOT_ALLOWED);
            }
        }

        if (this.scrollable()) {
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED, this.scrollbarSettings.backgroundSprite(), this.getX(), scrollerY, this.getWidth(), this.scrollbarHeight()
            );
            graphics.blitSprite(
                    RenderPipelines.GUI_TEXTURED, this.scrollbarSettings.scrollerSprite(), this.getX(), scrollerY, scrollerWidth, this.scrollbarHeight()
            );
            if (this.isOverScrollbar(mouseX, mouseY)) {
                graphics.requestCursor(this.scrolling ? CursorTypes.RESIZE_NS : CursorTypes.POINTING_HAND);
            }
        }
    }

//    @Override
//    protected void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
//
//        this.extractScrollbar(graphics, mouseX, mouseY);
//    }

//    @Override
//    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
//
//    }

    protected abstract int contentWidth();

//    public int contentWidth() {
//        return 0;
//    }

    public static ScrollbarSettings defaultSettings(int scrollRate) {
        return new ScrollbarSettings(SCROLLER_SPRITE, null, SCROLLER_BACKGROUND_SPRITE, SCROLLBAR_HEIGHT, SCROLLBAR_MIN_WIDTH, scrollRate, true);
    }

    public record ScrollbarSettings(
            Identifier scrollerSprite,
            @Nullable Identifier disabledScrollerSprite,
            Identifier backgroundSprite,
            int scrollbarHeight,
            int scrollbarMinWidth,
            int scrollRate,
            boolean resizingScrollbar
    ) {
        public static final ScrollbarSettings NO_SCROLL = AbstractVerticalScrollArea.defaultSettings(0);
    }
}
