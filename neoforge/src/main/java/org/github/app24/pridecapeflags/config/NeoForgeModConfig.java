package org.github.app24.pridecapeflags.config;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.github.app24.pridecapeflags.Constants;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;

public class NeoForgeModConfig implements IModConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_PRIDE_TITLE = BUILDER
            .comment("Show Pride Title during Pride Month")
            .define("show_pride_title", true);

    public static final ModConfigSpec.BooleanValue SHOW_PRIDE_CAPE = BUILDER
            .comment("Show Pride Cape")
            .define("show_pride_cape", true);

    public static final ModConfigSpec.ConfigValue<String> PRIDE_CAPE_FLAG = BUILDER
            .comment("Pride Cape Flag")
            .define("pride_cape", Constants.MOD_ID+":pride");

    public static final ModConfigSpec.BooleanValue USE_ELYTRA_CAPE = BUILDER
            .comment("Use Elytra Texture")
            .define("use_elytra_cape", false);

    public static final ModConfigSpec.ConfigValue<String> ELYTRA_PRIDE_CAPE_FLAG = BUILDER
            .comment("Elytra Pride Cap Flag")
            .define("elytra_pride_cape", Constants.MOD_ID+":pride");

    public static final ModConfigSpec SPEC = BUILDER.build();

//    private static boolean validateFlagName(final Object obj){
//        if(!(obj instanceof String flagName)) return false;
//        var identifier = Identifier.tryParse(flagName);
//        if(identifier == null) return false;
//        if(Minecraft.getInstance() == null || !Minecraft.getInstance().isGameLoadFinished()) return true;
//        return PrideCapeFlagsClient.checkFlagValid(identifier);
//    }

    @Override
    public boolean showPrideTitle() {
        return SHOW_PRIDE_TITLE.get();
    }

    @Override
    public void setShowPrideTitle(boolean show) {
        SHOW_PRIDE_TITLE.set(show);
    }

    @Override
    public boolean showPrideCape() {
        return SHOW_PRIDE_CAPE.get();
    }

    @Override
    public void setShowPrideCape(boolean show) {
        SHOW_PRIDE_CAPE.set(show);
    }

    @Override
    public String getPrideCapeFlag() {
        return PRIDE_CAPE_FLAG.get();
    }

    @Override
    public void setPrideCapeFlag(String flag) {
        PRIDE_CAPE_FLAG.set(flag);
    }

    @Override
    public boolean useElytraCape() {
        return USE_ELYTRA_CAPE.get();
    }

    @Override
    public void setUseElytraCape(boolean use) {
        USE_ELYTRA_CAPE.set(use);
    }

    @Override
    public String getElytraCapeFlag() {
        return ELYTRA_PRIDE_CAPE_FLAG.get();
    }

    @Override
    public void setElytraCapeFlag(String flag) {
        ELYTRA_PRIDE_CAPE_FLAG.set(flag);
    }

    @Override
    public void saveConfig() {
        SPEC.save();
    }

    @Override
    public void updateConfig() {

    }
}
