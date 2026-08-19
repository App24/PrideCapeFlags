package org.github.app24.pridecapeflags.gui;

import net.minecraft.client.gui.components.*;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.github.app24.pridecapeflags.Config;
import org.github.app24.pridecapeflags.PrideCapeFlagsMod;
import org.jspecify.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConfigScreen extends ConfigurationScreen.ConfigurationSectionScreen {

    public ConfigScreen(Screen parent, ModConfig.Type type, ModConfig modConfig, Component title) {
        super(parent, type, modConfig, title);
    }

    @Override
    protected @Nullable Element createBooleanValue(String key, ModConfigSpec.ValueSpec spec, Supplier<Boolean> source, Consumer<Boolean> target) {
        if(!key.equals(Config.SHOW_PRIDE_TITLE.getPath().getFirst()))
            return null;
        return super.createBooleanValue(key, spec, source, target);
    }

    @Override
    protected @Nullable Element createStringValue(String key, Predicate<String> tester, Supplier<String> source, Consumer<String> target) {
        return null;
    }

    @Override
    protected Collection<? extends Element> createSyntheticValues() {
        var editCapeButton = new Element(null, null,
                Button.builder(Component.translatable("pridecapeflags.config.editCape"),
                        button -> this.minecraft.gui.setScreen(new PrideCapeFlagsScreen(this))).build(), true);
        return Collections.singletonList(editCapeButton);
    }
}
