package org.github.app24.pridecapeflags.config;

import com.google.gson.GsonBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.TickBox;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.Constants;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;

public class FabricModConfig implements IModConfig{
    public static final ConfigClassHandler<FabricModConfig> HANDLER = ConfigClassHandler.createBuilder(FabricModConfig.class)
            .id(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "pridecapeflags"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("pridecapeflags.json"))
                    .appendGsonBuilder(GsonBuilder::setPrettyPrinting)
                    .build()
            )
            .build();


    private static FabricModConfig INSTANCE() { return HANDLER.instance();}

    @SerialEntry
    @AutoGen(category = "pridecapeflags")
    @TickBox
    private boolean showPrideTitle = true;
    //    @Entry()
    @SerialEntry
//    @AutoGen(category = "pridecapeflags")
//    @TickBox
    private boolean showPrideCape = true;
    //    @Entry()
    @SerialEntry
//    @AutoGen(category = "pridecapeflags")
//    @StringField
    private String prideCapeFlag = Constants.MOD_ID + ":pride";
    //    @Entry()
    @SerialEntry
//    @AutoGen(category = "pridecapeflags")
//    @MasterTickBox(value = {"elytraPrideCapeFlag"})
    private boolean useElytraCape = false;
    //    @Condition(requiredOption = PrideCapeFlags.MOD_ID+":useElytraCape", visibleButLocked = true)
//    @Entry()
    @SerialEntry
//    @AutoGen(category = "pridecapeflags")
//    @StringField
    private String elytraPrideCapeFlag = Constants.MOD_ID + ":pride";

    @Override
    public boolean showPrideTitle() {
        return INSTANCE().showPrideTitle;
    }

    @Override
    public void setPrideTitle(boolean show) {
        INSTANCE().showPrideTitle = show;
    }

    @Override
    public boolean showPrideCape() {
        return INSTANCE().showPrideCape;
    }

    @Override
    public void setPrideCape(boolean show) {
        INSTANCE().showPrideCape = show;
    }

    @Override
    public String getPrideCapeFlag() {
        return INSTANCE().prideCapeFlag;
    }

    @Override
    public void setPrideCapeFlag(String flag) {
        INSTANCE().prideCapeFlag = flag;
    }

    @Override
    public boolean useElytraCape() {
        return INSTANCE().useElytraCape;
    }

    @Override
    public void setElytraCape(boolean use) {
        INSTANCE().useElytraCape = use;
    }

    @Override
    public String getElytraCapeFlag() {
        return INSTANCE().elytraPrideCapeFlag;
    }

    @Override
    public void setElytraCapeFlag(String flag) {
        INSTANCE().elytraPrideCapeFlag = flag;
    }

    @Override
    public void saveConfig() {
        HANDLER.save();
    }

    @Override
    public void updateConfig() {
        PrideCapeFlagsClient.updateConfig();
    }
}
