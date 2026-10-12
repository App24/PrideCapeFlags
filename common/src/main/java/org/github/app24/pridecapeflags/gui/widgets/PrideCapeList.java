package org.github.app24.pridecapeflags.gui.widgets;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.screens.Screen;
import org.github.app24.pridecapeflags.gui.PrideCapeSelectionScreen;

import java.util.Collections;
import java.util.List;

public class PrideCapeList extends ContainerObjectSelectionList<PrideCapeList.Entry> {
    private final PrideCapeSelectionScreen screen;

    public PrideCapeList(Minecraft minecraft, int width, PrideCapeSelectionScreen screen) {
        super(minecraft, width, screen.layout.getContentHeight(), screen.layout.getHeaderHeight(), 69);
        this.screen=screen;
    }

    public void addHeader(List<AbstractWidget> widgets) {
        this.addEntry(new HeaderEntry(widgets, this.screen), 25);
    }

    public void addCapeWidgets(List<CapeSelectionWidget> widgets) {
        for (int i = 0; i < widgets.size(); i += 3) {
            List<AbstractWidget> subWidgets = Lists.newArrayList();
            subWidgets.add(widgets.get(i));
            if(i < widgets.size() - 1) {
                subWidgets.add(widgets.get(i + 1));
                if(i < widgets.size() - 2)
                    subWidgets.add(widgets.get(i + 2));
            }
            this.addEntry(new CapeEntry(subWidgets, this.screen));
        }
    }

    public abstract static class Entry extends ContainerObjectSelectionList.Entry<PrideCapeList.Entry> {
    }

    public static class CapeEntry extends PrideCapeList.Entry {

        private final List<AbstractWidget> children;
        private final Screen screen;
        private static final int X_OFFSET = 74;

        public CapeEntry(List<AbstractWidget> children, Screen screen){
            this.children = children;
            this.screen = screen;
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.unmodifiableList(this.children);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return Collections.unmodifiableList(this.children);
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            int xOffset = 0;
            int x = this.screen.width / 2 - 69 - 32;

            for (AbstractWidget optionInstanceWidget : this.children) {
                if(optionInstanceWidget == null) continue;
                optionInstanceWidget.setPosition(x + xOffset, this.getContentY());
                optionInstanceWidget.extractRenderState(graphics, mouseX, mouseY, a);
                xOffset += X_OFFSET;
            }
        }
    }

    public static class HeaderEntry extends PrideCapeList.Entry {

        private final List<AbstractWidget> children;
        private final Screen screen;
        private static final int X_OFFSET = 160;

        public HeaderEntry(List<AbstractWidget> children, Screen screen){
            this.children = children;
            this.screen = screen;
        }

        @Override
        public List<? extends GuiEventListener> children() {
            return Collections.unmodifiableList(this.children);
        }

        @Override
        public List<? extends NarratableEntry> narratables() {
            return Collections.unmodifiableList(this.children);
        }

        @Override
        public void extractContent(GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a) {
            int xOffset = 0;
            int x = this.screen.width / 2 - 69 - 32;

            for (AbstractWidget optionInstanceWidget : this.children) {
                if(optionInstanceWidget == null) continue;
                optionInstanceWidget.setPosition(x + xOffset, this.getContentY());
                optionInstanceWidget.extractRenderState(graphics, mouseX, mouseY, a);
                xOffset += 112;
            }
        }
    }
}
