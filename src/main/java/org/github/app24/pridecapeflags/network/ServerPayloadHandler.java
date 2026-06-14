package org.github.app24.pridecapeflags.network;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import org.github.app24.pridecapeflags.CapeFlagData;

import java.util.HashMap;

public class ServerPayloadHandler {
    public static final HashMap<String, CapeFlagData> PLAYER_CAPES = Maps.newHashMap();

    public static void handleCapeData(CapeFlagPacket data, final ServerPlayNetworking.Context context){
        data = new CapeFlagPacket(context.player().getStringUUID(), data.showCape(), data.capeResourceLocation(), data.useElytra(), data.elytraResourceLocation());
        PLAYER_CAPES.put(data.uuid(), CapeFlagData.FromPacket(data));
        //MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer(), "Cannot send clientbound payloads on the client");
        //var players = server.getPlayerList().getPlayers();
        for (ServerPlayer serverplayer : PlayerLookup.all(context.server())) {
            if(serverplayer != context.player())
                ServerPlayNetworking.send(serverplayer, data);
        }
    }

    public static void handleCapeFlagRequestOnMain(final CapeFlagRequestPacket data, final ServerPlayNetworking.Context context){
        PLAYER_CAPES.forEach((uuid, capeData)->{
            var capeFlag = new CapeFlagPacket(uuid, capeData.showCape(), capeData.getCapeResourceLocation(), capeData.useElytra(), capeData.getElytraResourceLocation());
            //PacketDistributor.sendToPlayer((ServerPlayer) context.player(), capeFlag);
            ServerPlayNetworking.send(context.player(), capeFlag);
        });
    }
}