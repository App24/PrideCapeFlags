package org.github.app24.pridecapeflags.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.network.chat.Component;
import org.github.app24.pridecapeflags.PrideCapeFlags;

public class ModMenuIntegration implements ModMenuApi {    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> MidnightConfig.getScreen(parent, PrideCapeFlags.MOD_ID);
    }
}
