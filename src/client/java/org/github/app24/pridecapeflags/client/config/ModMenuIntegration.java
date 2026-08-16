package org.github.app24.pridecapeflags.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.network.chat.Component;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.gui.ConfigScreen;

public class ModMenuIntegration implements ModMenuApi {    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> new ConfigScreen(parent, PrideCapeFlags.MOD_ID);
    }
}
