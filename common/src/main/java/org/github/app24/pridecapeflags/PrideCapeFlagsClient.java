package org.github.app24.pridecapeflags;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.config.IModConfig;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;

import java.util.HashMap;

public class PrideCapeFlagsClient {
    public static IModConfig MOD_CONFIG;

    public static CapeFlagData CAPE_FLAG = CapeFlagData.Empty();
    public static final HashMap<String, CapeFlagData> PLAYER_CAPES = Maps.newHashMap();

    public static void updateConfig(){
        updateCapeFlagConfig();
        if(Minecraft.getInstance().getConnection() != null) {
            sendCapeToServer();
        }
    }

    public static void onPlayerJoinServer(){
        PLAYER_CAPES.clear();
        receiveCapesFromServer();
        sendCapeToServer();
    }

    public static void updateCapeFlagConfig(){
        CAPE_FLAG.setShowCape(MOD_CONFIG.showPrideCape());
        CAPE_FLAG.setCapeResourceLocation(MOD_CONFIG.getPrideCapeFlag());
        CAPE_FLAG.setUseElytra(MOD_CONFIG.useElytraCape());
        CAPE_FLAG.setElytraResourceLocation(MOD_CONFIG.getElytraCapeFlag());
    }

    public static void sendCapeToServer(){
        try {
            PrideCapeFlags.c2s.accept(CAPE_FLAG.ToPacket());
        }catch(Exception ignored){}
    }

    public static void receiveCapesFromServer(){
        try {
            PrideCapeFlags.c2s.accept(new CapeFlagRequestPacket());
        }catch(Exception ignored){}
    }

    public static boolean checkFlagValid(Identifier textureId){
        return Minecraft.getInstance().getResourceManager().getResource(textureId.withPath(path->"flags/"+path+".png")).isPresent();
    }
}
