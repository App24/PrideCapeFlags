package org.github.app24.pridecapeflags.client.gui;

import dev.isxander.yacl3.api.CustomTabProvider;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.gui.YACLScreen;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;

import java.util.Optional;

public class ConfigScreen extends YACLScreen {
    public ConfigScreen(YetAnotherConfigLib config, Screen parent) {
        super(config, parent);
    }

    @Override
    protected void init() {

        var slice = (width/3) * 2 + 1;
        var buttonWidth = slice - 50;

        AbstractWidget widget = Button.builder(Component.translatable("pridecapeflags.config.editCape"), button ->
                this.minecraft.gui.setScreen(new PrideCapeFlagsScreen(this))).bounds(12, 0, buttonWidth, 20).build();


        widget.setPosition(15,50);

        addRenderableWidget(widget);
        super.init();

//        this.list.addButton(Lists.newArrayList(widget), Component.empty(), null);
    }

//    @Override
//    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
//        Optional<GuiEventListener> child = this.getChildAt(event.x(), event.y());
//        if (child.isEmpty()) {
//            return false;
//        } else {
//            GuiEventListener widget = (GuiEventListener)child.get();
//            if (widget.mouseClicked(event, doubleClick) && widget.shouldTakeFocusAfterInteraction()) {
//                this.setFocused(widget);
//                if (event.button() == 1) {
//                    this.setDragging(true);
//                }
//            }
//
//            return true;
//        }
//    }

//    @Override
//    public boolean mouseClicked(final MouseButtonEvent event, final boolean doubleClick) {
//        Optional<GuiEventListener> child = this.getChildAt(event.x(), event.y());
//        if (child.isEmpty()) {
//            return false;
//        } else {
//            GuiEventListener widget = (GuiEventListener)child.get();
//            if (widget.mouseClicked(event, doubleClick) && widget.shouldTakeFocusAfterInteraction()) {
//                this.setFocused(widget);
//                if (event.button() == 1) {
//                    this.setDragging(true);
//                }
//            }
//
//            return true;
//        }
//    }
//
//    @Override
//    public boolean mouseDragged(final MouseButtonEvent event, final double dx, final double dy) {
//        return this.getFocused() != null && this.isDragging() && event.button() == 1 && this.getFocused().mouseDragged(event, dx, dy);
//    }
}
