package org.github.app24.pridecapeflags.client.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.MasterTickBox;
import dev.isxander.yacl3.config.v2.api.autogen.StringField;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.PrideCapeFlagsClient;

public class ModConfig {
    public static final ConfigClassHandler<ModConfig> HANDLER = ConfigClassHandler.createBuilder(ModConfig.class)
            .id(Identifier.fromNamespaceAndPath(PrideCapeFlags.MOD_ID, "pridecapeflags"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("pridecapeflags.json"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .build()
            )
            .build();

    public static ModConfig INSTANCE() { return HANDLER.instance();}

//    @Entry()
    @SerialEntry
    @AutoGen(category = "pridecapeflags")
    @TickBox
    public boolean showPrideTitle = true;
//    @Entry()
    @SerialEntry
    @AutoGen(category = "pridecapeflags")
    @TickBox
    public boolean showPrideCape = true;
//    @Entry()
    @SerialEntry
    @AutoGen(category = "pridecapeflags")
    @StringField
    public String prideCapeFlag = PrideCapeFlags.MOD_ID + ":pride";
//    @Entry()
    @SerialEntry
    @AutoGen(category = "pridecapeflags")
    @MasterTickBox(value = {"elytraPrideCapeFlag"})
    public boolean useElytraCape = false;
//    @Condition(requiredOption = PrideCapeFlags.MOD_ID+":useElytraCape", visibleButLocked = true)
//    @Entry()
    @SerialEntry
    @AutoGen(category = "pridecapeflags")
    @StringField
    public String elytraPrideCapeFlag = PrideCapeFlags.MOD_ID + ":pride";

//    @Override
//    public void loadValuesFromJson() {
//        super.loadValuesFromJson();
//
//        PrideCapeFlagsClient.updateConfig();
//    }
//
//    @Override
//    public void writeChanges() {
//        super.writeChanges();
//
//        PrideCapeFlagsClient.updateConfig();
//        if(Minecraft.getInstance().getConnection() != null){
//            PrideCapeFlagsClient.sendCapeToServer();
//        }
//    }

    public static void updateConfig() {
        PrideCapeFlagsClient.updateConfig();
        if (Minecraft.getInstance().getConnection() != null) {
            PrideCapeFlagsClient.sendCapeToServer();
        }
    }
}
