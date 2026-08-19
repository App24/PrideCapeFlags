package org.github.app24.pridecapeflags;

import net.minecraft.client.gui.screens.Screen;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

public class PrideConfigScreenFactory implements IConfigScreenFactory {
    @Override
    public Screen createScreen(ModContainer container, Screen modListScreen) {
        return new ConfigurationScreen(container, modListScreen);
    }
}
