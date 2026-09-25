package org.github.app24.pridecapeflags.network;

import com.google.common.collect.Maps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.github.app24.pridecapeflags.CapeFlagData;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.jspecify.annotations.Nullable;

import java.util.HashMap;

public class ServerPayloadHandler {
    public static final HashMap<String, CapeFlagData> PLAYER_CAPES = Maps.newHashMap();

    public static void handleCapeData(CapeFlagPacket data, ServerPlayer serverPlayer, @Nullable MinecraftServer server){
        data = new CapeFlagPacket(serverPlayer.getStringUUID(), data.showCape(), data.capeResourceLocation(), data.useElytra(), data.elytraResourceLocation());
        PLAYER_CAPES.put(data.uuid(), CapeFlagData.FromPacket(data));

        var players = PrideCapeFlags.getPlayers.apply(server);
        for(ServerPlayer player : players){
            if(player != serverPlayer){
                PrideCapeFlags.s2c.accept(player, data);
            }
        }
    }

    public static void handleCapeFlagRequestOnMain(final CapeFlagRequestPacket data, ServerPlayer serverPlayer){
        PLAYER_CAPES.forEach((uuid, capeData)->{
            var capeFlag = new CapeFlagPacket(uuid, capeData.showCape(), capeData.getCapeResourceLocation(), capeData.useElytra(), capeData.getElytraResourceLocation());
            PrideCapeFlags.s2c.accept(serverPlayer, capeFlag);
        });
    }
}
