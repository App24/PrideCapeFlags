package org.github.app24.pridecapeflags.gui.widgets;

import com.google.common.collect.Lists;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.layouts.HeaderAndFooterLayout;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.gui.navigation.ScreenDirection;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import org.jspecify.annotations.Nullable;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

public abstract class AbstractVerticalSelectionList<E extends AbstractVerticalSelectionList.Entry<E>> extends AbstractVerticalContainerWidget{
    private static final Identifier MENU_LIST_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/menu_list_background.png");
    public static final Identifier INWORLD_MENU_LIST_BACKGROUND = Identifier.withDefaultNamespace("textures/gui/inworld_menu_list_background.png");

    private final Minecraft minecraft;
    private final int defaultEntryWidth;
    private final List<E> children = new AbstractVerticalSelectionList.TrackedList();
    private @Nullable E selected;
    private @Nullable E hovered;

    public AbstractVerticalSelectionList(Minecraft minecraft, int width, int height, int y, int defaultEntryWidth) {
        super(0, y, width, height, CommonComponents.EMPTY, AbstractVerticalScrollArea.defaultSettings(defaultEntryWidth / 2));
        this.minecraft = minecraft;
        this.defaultEntryWidth = defaultEntryWidth;
    }

    public @Nullable E getSelected() {
        return this.selected;
    }

    public void setSelected(@Nullable E selected) {
        this.selected = selected;
        if (selected != null) {
            boolean leftClipped = selected.getContentX() < this.getX();
            boolean rightClipped = selected.getContentRight() > this.getRight();
            if (this.minecraft.getLastInputType().isKeyboard() || leftClipped || rightClipped) {
                this.scrollToEntry(selected);
            }
        }
    }

    public @Nullable E getFocused() {
        return (E)super.getFocused();
    }

    @Override
    public final List<E> children() {
        return Collections.unmodifiableList(this.children);
    }

    protected void sort(Comparator<E> comparator) {
        this.children.sort(comparator);
        this.repositionEntries();
    }

    protected void swap(int firstIndex, int secondIndex) {
        Collections.swap(this.children, firstIndex, secondIndex);
        this.repositionEntries();
        this.scrollToEntry(this.children.get(secondIndex));
    }

    public void clearEntries() {
        this.children.clear();
        this.selected = null;
    }

    protected void clearEntriesExcept(E exception) {
        this.children.removeIf(entry -> entry != exception);
        if (this.selected != exception) {
            this.setSelected(null);
        }
    }

    public void replaceEntries(Collection<E> newChildren) {
        this.clearEntries();

        for (E newChild : newChildren) {
            this.addEntry(newChild);
        }
    }

    private int getFirstEntryX() {
        return this.getX() + 2;
    }

    public int getNextX() {
        int x = this.getFirstEntryX() - (int)this.scrollAmount();

        for (E child : this.children) {
            x += child.getWidth();
        }

        return x;
    }

    protected int addEntry(E entry) {
        return this.addEntry(entry, this.defaultEntryWidth);
    }

    protected int addEntry(E entry, int width) {
//        entry.setX(this.getRowLeft());
        entry.setX(this.getNextX());
        entry.setWidth(width);
//        entry.setWidth(this.getRowWidth());
//        entry.setY(this.getNextY());
        entry.setY(this.getColumnTop());
        entry.setHeight(this.getColumnHeight());
//        entry.setHeight(height);
        this.children.add(entry);
        return this.children.size() - 1;
    }

    protected void addEntryToTop(E entry) {
        this.addEntryToTop(entry, this.defaultEntryWidth);
    }

    protected void addEntryToTop(E entry, int width) {
//        double scrollFromBottom = this.maxScrollAmount() - this.scrollAmount();
        double scrollFromRight = this.maxScrollAmount() - this.scrollAmount();
//        entry.setHeight(height);
        entry.setWidth(width);
        this.children.addFirst(entry);
        this.repositionEntries();
        this.setScrollAmount(this.maxScrollAmount() - scrollFromRight);
    }

    private void repositionEntries() {
        int x = this.getFirstEntryX() - (int)this.scrollAmount();

        for (E child : this.children) {
            child.setY(x);
            x += child.getWidth();
            child.setY(this.getColumnTop());
            child.setHeight(this.getColumnHeight());
        }
    }

    protected void removeEntryFromTop(E entry) {
        double scrollFromRight = this.maxScrollAmount() - this.scrollAmount();
        this.removeEntry(entry);
        this.setScrollAmount(this.maxScrollAmount() - scrollFromRight);
    }

    protected int getItemCount() {
        return this.children().size();
    }

    protected boolean entriesCanBeSelected() {
        return true;
    }

    protected final @Nullable E getEntryAtPosition(double posX, double posY) {
        for (E child : this.children) {
            if (child.isMouseOver(posX, posY)) {
                return child;
            }
        }

        return null;
    }

    public void updateSize(int width, HeaderAndFooterLayout layout) {
        this.updateSizeAndPosition(width, layout.getContentHeight(), layout.getHeaderHeight());
    }

    public void updateSizeAndPosition(int width, int height, int y) {
        this.updateSizeAndPosition(width, height, 0, y);
    }

    public void updateSizeAndPosition(int width, int height, int x, int y) {
        this.setSize(width, height);
        this.setPosition(x, y);
        this.repositionEntries();
        if (this.getSelected() != null) {
            this.scrollToEntry(this.getSelected());
        }

        this.refreshScrollAmount();
    }

    @Override
    protected int contentWidth() {
        int totalWidth = 0;

        for (E child : this.children) {
            totalWidth += child.getWidth();
        }

        return totalWidth + 4;
    }

    @Override
    public void extractWidgetRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        this.hovered = this.isMouseOver(mouseX, mouseY) ? this.getEntryAtPosition(mouseX, mouseY) : null;
        this.extractListBackground(graphics);
        this.enableScissor(graphics);
        this.extractListItems(graphics, mouseX, mouseY, a);
        graphics.disableScissor();
        this.extractListSeparators(graphics);
        this.extractScrollbar(graphics, mouseX, mouseY);
    }

    protected void extractListSeparators(GuiGraphicsExtractor graphics) {
        Identifier headerSeparator = this.minecraft.level == null ? Screen.HEADER_SEPARATOR : Screen.INWORLD_HEADER_SEPARATOR;
        Identifier footerSeparator = this.minecraft.level == null ? Screen.FOOTER_SEPARATOR : Screen.INWORLD_FOOTER_SEPARATOR;
        graphics.blit(RenderPipelines.GUI_TEXTURED, headerSeparator, this.getX(), this.getY() - 2, 0.0F, 0.0F, this.getWidth(), 2, 32, 2);
        graphics.blit(RenderPipelines.GUI_TEXTURED, footerSeparator, this.getX(), this.getBottom(), 0.0F, 0.0F, this.getWidth(), 2, 32, 2);
    }

    protected void extractListBackground(GuiGraphicsExtractor graphics) {
        Identifier menuListBackground = this.minecraft.level == null ? MENU_LIST_BACKGROUND : INWORLD_MENU_LIST_BACKGROUND;
        graphics.blit(
                RenderPipelines.GUI_TEXTURED,
                menuListBackground,
                this.getX(),
                this.getY(),
                this.getRight(),
                this.getBottom() + (int)this.scrollAmount(),
                this.getWidth(),
                this.getHeight(),
                32,
                32
        );
    }

    protected void enableScissor(GuiGraphicsExtractor graphics) {
        graphics.enableScissor(
                Mth.clamp(this.getX(), 0, graphics.guiWidth()),
                Mth.clamp(this.getY(), 0, graphics.guiHeight()),
                Mth.clamp(this.getRight(), 0, graphics.guiWidth()),
                Mth.clamp(this.getBottom(), 0, graphics.guiHeight())
        );
    }

    protected void scrollToEntry(E entry) {
        int topDelta = entry.getX() - this.getX() - 2;
        if (topDelta < 0) {
            this.scroll(topDelta);
        }

        int bottomDelta = this.getRight() - entry.getX() - entry.getWidth() - 2;
        if (bottomDelta < 0) {
            this.scroll(-bottomDelta);
        }
    }

    protected void centerScrollOn(E entry) {
        int x = 0;

        for (E child : this.children) {
            if (child == entry) {
                x += child.getWidth() / 2;
                break;
            }

            x += child.getWidth();
        }

        this.setScrollAmount(x - this.width / 2.0);
    }

    private void scroll(int amount) {
        this.setScrollAmount(this.scrollAmount() + amount);
    }

    @Override
    public void setScrollAmount(double scrollAmount) {
        super.setScrollAmount(scrollAmount);
        this.repositionEntries();
    }

    @Override
    protected int scrollBarX() {
        return this.getColumnBottom() + this.scrollbarHeight() + 2;
    }

    @Override
    public Optional<GuiEventListener> getChildAt(double x, double y) {
        return Optional.ofNullable(this.getEntryAtPosition(x, y));
    }

    @Override
    public void setFocused(@Nullable GuiEventListener focused) {
        E oldFocus = this.getFocused();
        if (oldFocus != focused && oldFocus instanceof ContainerEventHandler oldFocusContainer) {
            oldFocusContainer.setFocused(null);
        }

        super.setFocused(focused);
        int index = this.children.indexOf(focused);
        if (index >= 0) {
            E magicallyCastEntry = this.children.get(index);
            this.setSelected(magicallyCastEntry);
        }
    }

    protected @Nullable E nextEntry(ScreenDirection dir) {
        return this.nextEntry(dir, entry -> true);
    }

    protected @Nullable E nextEntry(ScreenDirection dir, Predicate<E> canSelect) {
        return this.nextEntry(dir, canSelect, this.getSelected());
    }

    protected @Nullable E nextEntry(ScreenDirection dir, Predicate<E> canSelect, @Nullable E startEntry) {
        int delta = switch (dir) {
            case RIGHT -> 1;
            case LEFT -> -1;
            case UP, DOWN -> 0;
        };
        if (!this.children().isEmpty() && delta != 0) {
            int index;
            if (startEntry == null) {
                index = delta > 0 ? 0 : this.children().size() - 1;
            } else {
                index = this.children().indexOf(startEntry) + delta;
            }

            for (int i = index; i >= 0 && i < this.children.size(); i += delta) {
                E selected = this.children().get(i);
                if (canSelect.test(selected)) {
                    return selected;
                }
            }
        }

        return null;
    }

    protected void extractListItems(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        for (E child : this.children) {
            if (child.getX() + child.getWidth() >= this.getX() && child.getX() <= this.getRight()) {
                this.extractItem(graphics, mouseX, mouseY, a, child);
            }
        }
    }

    protected void extractItem(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a, E entry) {
        if (this.entriesCanBeSelected() && this.getSelected() == entry) {
            int outlineColor = this.isFocused() ? -1 : -8355712;
            this.extractSelection(graphics, entry, outlineColor);
        }

        entry.extractContent(graphics, mouseX, mouseY, Objects.equals(this.hovered, entry), a);
    }

    protected void extractSelection(GuiGraphicsExtractor graphics, E entry, int outlineColor) {
        int outlineX0 = entry.getX();
        int outlineY0 = entry.getY();
        int outlineX1 = outlineX0 + entry.getWidth();
        int outlineY1 = outlineY0 + entry.getHeight();
        graphics.fill(outlineX0, outlineY0, outlineX1, outlineY1, outlineColor);
        graphics.fill(outlineX0 + 1, outlineY0 + 1, outlineX1 - 1, outlineY1 - 1, -16777216);
    }

    public int getColumnTop(){
        return this.getY() + this.height /2 - this.getColumnHeight() /2;
    }

    public int getColumnBottom(){
        return this.getColumnTop() + this.getColumnHeight();
    }

    public int getColumnLeft(int column){
        return this.children.get(column).getX();
    }

    public int getColumnRight(int column){
        E child = this.children.get(column);
        return child.getX() + child.getWidth();
    }

    public int getColumnHeight(){
        return 220;
    }

    @Override
    public NarratableEntry.NarrationPriority narrationPriority() {
        if (this.isFocused()) {
            return NarratableEntry.NarrationPriority.FOCUSED;
        } else {
            return this.hovered != null ? NarratableEntry.NarrationPriority.HOVERED : NarratableEntry.NarrationPriority.NONE;
        }
    }

    protected void removeEntries(List<E> entries) {
        entries.forEach(this::removeEntry);
    }

    protected void removeEntry(E entry) {
        boolean removed = this.children.remove(entry);
        if (removed) {
            this.repositionEntries();
            if (entry == this.getSelected()) {
                this.setSelected(null);
            }
        }
    }

    protected @Nullable E getHovered() {
        return this.hovered;
    }

    private void bindEntryToSelf(AbstractVerticalSelectionList.Entry<E> entry) {
        entry.list = this;
    }

    protected void narrateListElementPosition(NarrationElementOutput output, E element) {
        List<E> children = this.children();
        if (children.size() > 1) {
            int index = children.indexOf(element);
            if (index != -1) {
                output.add(NarratedElementType.POSITION, Component.translatable("narrator.position.list", index + 1, children.size()));
            }
        }
    }

    protected abstract static class Entry<E extends AbstractVerticalSelectionList.Entry<E>> implements LayoutElement, GuiEventListener {
        public static final int CONTENT_PADDING = 2;
        private int x = 0;
        private int y = 0;
        private int width = 0;
        private int height;
        @Deprecated
        protected AbstractVerticalSelectionList<E> list;

        @Override
        public void setFocused(boolean focused) {
        }

        @Override
        public boolean isFocused() {
            return this.list.getFocused() == this;
        }

        public abstract void extractContent(final GuiGraphicsExtractor graphics, int mouseX, int mouseY, boolean hovered, float a);

        @Override
        public boolean isMouseOver(double mx, double my) {
            return this.getRectangle().containsPoint((int)mx, (int)my);
        }

        @Override
        public void setX(int x) {
            this.x = x;
        }

        @Override
        public void setY(int y) {
            this.y = y;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getContentX() {
            return this.getX() + 2;
        }

        public int getContentY() {
            return this.getY() + 2;
        }

        public int getContentHeight() {
            return this.getHeight() - 4;
        }

        public int getContentYMiddle() {
            return this.getContentY() + this.getContentHeight() / 2;
        }

        public int getContentBottom() {
            return this.getContentY() + this.getContentHeight();
        }

        public int getContentWidth() {
            return this.getWidth() - 4;
        }

        public int getContentXMiddle() {
            return this.getContentX() + this.getContentWidth() / 2;
        }

        public int getContentRight() {
            return this.getContentX() + this.getContentWidth();
        }

        @Override
        public int getX() {
            return this.x;
        }

        @Override
        public int getY() {
            return this.y;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public void visitWidgets(Consumer<AbstractWidget> widgetVisitor) {
        }

        @Override
        public ScreenRectangle getRectangle() {
            return LayoutElement.super.getRectangle();
        }
    }

    private class TrackedList extends AbstractList<E> {
        private final List<E> delegate = Lists.newArrayList();

        public E get(int index) {
            return this.delegate.get(index);
        }

        @Override
        public int size() {
            return this.delegate.size();
        }

        public E set(int index, E element) {
            E entry = this.delegate.set(index, element);
            AbstractVerticalSelectionList.this.bindEntryToSelf(element);
            return entry;
        }

        public void add(int index, E element) {
            this.delegate.add(index, element);
            AbstractVerticalSelectionList.this.bindEntryToSelf(element);
        }

        public E remove(int index) {
            return this.delegate.remove(index);
        }
    }
}
