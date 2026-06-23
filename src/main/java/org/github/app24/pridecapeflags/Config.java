package org.github.app24.pridecapeflags;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.ModConfigSpec;

public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_PRIDE_TITLE = BUILDER
            .comment("Show Pride Title during Pride Month")
            .define("show_pride_title", true);

    public static final ModConfigSpec.BooleanValue SHOW_PRIDE_CAPE = BUILDER
            .comment("Show Pride Cape")
            .define("show_pride_cape", true);

    public static final ModConfigSpec.ConfigValue<String> PRIDE_CAPE_FLAG = BUILDER
            .comment("Pride Cape Flag")
            .define("pride_cape", PrideCapeFlagsMod.MODID+":pride", Config::validateFlagName);

    public static final ModConfigSpec.BooleanValue USE_ELYTRA_CAPE = BUILDER
            .comment("Use Elytra Texture")
            .define("use_elytra_cape", false);

    public static final ModConfigSpec.ConfigValue<String> ELYTRA_PRIDE_CAPE_FLAG = BUILDER
            .comment("Elytra Pride Cap Flag")
            .define("elytra_pride_cape", PrideCapeFlagsMod.MODID+":pride", Config::validateFlagName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateFlagName(final Object obj){
        if(!(obj instanceof String flagName)) return false;
        var identifier = Identifier.tryParse(flagName);
        if(identifier == null) return false;
        if(Minecraft.getInstance() == null || !Minecraft.getInstance().isGameLoadFinished()) return true;
        return PrideCapeFlagsModClient.checkFlagValid(identifier);
    }
}
