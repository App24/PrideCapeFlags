package org.github.app24.pridecapeflags.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionEventListener;
import dev.isxander.yacl3.api.utils.OptionUtils;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;
import org.github.app24.pridecapeflags.gui.ConfigScreen;

public class ModMenuIntegration implements ModMenuApi {
    private static <T> void updateConfig(Option<T> option, OptionEventListener.Event event) {
        if (event == OptionEventListener.Event.STATE_CHANGE) {
            option.applyValue();
            PrideCapeFlagsClient.MOD_CONFIG.saveConfig();
            PrideCapeFlagsClient.MOD_CONFIG.updateConfig();
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
            var lib = FabricModConfig.HANDLER.generateGui();

            OptionUtils.forEachOptions(lib, option ->
                    option.addEventListener(ModMenuIntegration::updateConfig)
            );

            return new ConfigScreen(lib, parent);
        };
    }
}
