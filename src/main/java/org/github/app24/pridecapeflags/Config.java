package org.github.app24.pridecapeflags;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> PRIDE_CAPE_FLAG = BUILDER
            .comment("Pride Cap Flag")
            .define("pride_cape", PrideCapeFlagsMod.MODID+":trans");

    public static final ModConfigSpec.BooleanValue USE_ELYTRA_CAPE = BUILDER
            .comment("Use Elytra Texture")
            .define("use_elytra_cape", false);

    public static final ModConfigSpec.ConfigValue<String> ELYTRA_PRIDE_CAPE_FLAG = BUILDER
            .comment("Elytra Pride Cap Flag")
            .define("elytra_pride_cape", PrideCapeFlagsMod.MODID+":trans");

    static final ModConfigSpec SPEC = BUILDER.build();
}
