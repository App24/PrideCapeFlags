package org.github.app24.pridecapeflags.gui;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.github.app24.pridecapeflags.config.NeoForgeModConfig;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class ConfigScreen extends ConfigurationScreen.ConfigurationSectionScreen {

    public ConfigScreen(Screen parent, ModConfig.Type type, ModConfig modConfig, Component title) {
        super(parent, type, modConfig, title);
    }

    @Override
    protected void init() {
        this.minecraft.gui.setScreen(new PrideCapeConfigScreen(this.lastScreen));
    }

    //    @Override
//    protected @Nullable Element createBooleanValue(String key, ModConfigSpec.ValueSpec spec, Supplier<Boolean> source, Consumer<Boolean> target) {
//        if(!key.equals(NeoForgeModConfig.SHOW_PRIDE_TITLE.getPath().getFirst()))
//            return null;
//        return super.createBooleanValue(key, spec, source, target);
//    }
//
//    @Override
//    protected @Nullable Element createStringValue(String key, Predicate<String> tester, Supplier<String> source, Consumer<String> target) {
//        return null;
//    }
//
//    @Override
//    protected Collection<? extends Element> createSyntheticValues() {
//        var editCapeButton = new Element(null, null,
//                Button.builder(Component.translatable("pridecapeflags.menu.config.editCape"),
//                        button -> this.minecraft.gui.setScreen(new PrideCapeSelectionScreen(this))).build(), true);
//        return Collections.singletonList(editCapeButton);
//    }
}
