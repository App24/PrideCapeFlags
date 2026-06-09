package org.github.app24.pridecapeflags.client.config;

import eu.midnightdust.lib.config.EntryInfo;
import eu.midnightdust.lib.config.MidnightConfig;
import net.minecraft.client.Minecraft;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.PrideCapeFlagsClient;

import java.lang.reflect.Field;
import java.util.Objects;
import java.util.regex.Pattern;

public class ModConfig extends MidnightConfig {
    @Entry()
    public static String prideCapeFlag = PrideCapeFlags.MOD_ID + ":pride";
    @Entry()
    public static boolean useElytraCape = false;
    @Condition(requiredOption = PrideCapeFlags.MOD_ID+":useElytraCape", visibleButLocked = true)
    @Entry()
    public static String elytraPrideCapeFlag = PrideCapeFlags.MOD_ID + ":pride";

    @Override
    public void loadValuesFromJson() {
        super.loadValuesFromJson();

        PrideCapeFlagsClient.updateConfig();
    }

    @Override
    public void writeChanges() {
        super.writeChanges();

        PrideCapeFlagsClient.updateConfig();
        if(Minecraft.getInstance().getConnection() != null){
            PrideCapeFlagsClient.sendCapeToServer();
        }
    }
}
