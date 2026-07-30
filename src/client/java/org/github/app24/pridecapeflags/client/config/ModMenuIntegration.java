package org.github.app24.pridecapeflags.client.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionEventListener;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.utils.OptionUtils;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.impl.controller.StringControllerBuilderImpl;
import dev.isxander.yacl3.impl.controller.TickBoxControllerBuilderImpl;
import net.minecraft.network.chat.Component;

public class ModMenuIntegration implements ModMenuApi {
    private static <T> void updateConfig(Option<T> option, OptionEventListener.Event event) {
        if (event == OptionEventListener.Event.STATE_CHANGE) {
            option.applyValue();
            ModConfig.HANDLER.save();
            ModConfig.updateConfig();
        }
    }

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> {
//            var lib = YetAnotherConfigLib.createBuilder()
//                    .title(Component.literal("Mod Config"))
//                    .category(ConfigCategory.createBuilder()
//                            .name(Component.literal("DS"))
//                            .option(Option.<String>createBuilder()
//                                    .binding("DS", ()->ModConfig.INSTANCE().prideCapeFlag,newVal -> ModConfig.INSTANCE().prideCapeFlag = newVal)
//                                    .customController(ModStringController::new)
//                                    .build())
//                            .build())
//                            .build();
            var lib = ModConfig.HANDLER.generateGui();

            OptionUtils.forEachOptions(lib, option ->
                    option.addEventListener(ModMenuIntegration::updateConfig)
            );

            return lib.generateScreen(parent);
        };
    }
}
