package org.github.app24.pridecapeflags.network;

import com.google.common.collect.Maps;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.github.app24.pridecapeflags.CapeFlagData;

import java.util.HashMap;
import java.util.Objects;

public class ServerPayloadHandler {
    public static final HashMap<String, CapeFlagData> PLAYER_CAPES = Maps.newHashMap();

    public static void handleCapeData(CapeFlagPacket data, final IPayloadContext context){
        data = new CapeFlagPacket(context.player().getStringUUID(), data.showCape(), data.capeResourceLocation(), data.useElytra(), data.elytraResourceLocation());
        PLAYER_CAPES.put(data.uuid(), CapeFlagData.FromPacket(data));
        MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer(), "Cannot send clientbound payloads on the client");
        var players = server.getPlayerList().getPlayers();
        for (ServerPlayer serverplayer : players) {
            if(serverplayer != context.player())
                serverplayer.connection.send(data);
        }
    }

    public static void handleCapeFlagRequestOnMain(final CapeFlagRequestPacket data, final IPayloadContext context){
        PLAYER_CAPES.forEach((uuid, capeData)->{
            var capeFlag = new CapeFlagPacket(uuid, capeData.showCape(), capeData.getCapeResourceLocation(), capeData.useElytra(), capeData.getElytraResourceLocation());
            PacketDistributor.sendToPlayer((ServerPlayer) context.player(), capeFlag);
        });
    }
}
