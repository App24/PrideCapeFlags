package org.github.app24.pridecapeflags.client.gui;

import com.google.common.collect.Lists;
import eu.midnightdust.lib.config.EntryInfo;
import eu.midnightdust.lib.config.MidnightConfig;
import eu.midnightdust.lib.config.MidnightConfigScreen;
import eu.midnightdust.lib.config.MidnightSliderWidget;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.config.ModConfig;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConfigScreen extends MidnightConfigScreen {
    public ConfigScreen(Screen parent, String modid) {
        super(parent, modid);
    }

    @Override
    public void updateList() {
        super.updateList();

        AbstractWidget widget = Button.builder(Component.translatable("pridecapeflags.config.editCape"), button ->
                this.minecraft.setScreen(new PrideCapeFlagsScreen(this))).bounds(12, 0, width-24, 20).build();

        this.list.addButton(Lists.newArrayList(widget), Component.empty(), null);
    }
}
