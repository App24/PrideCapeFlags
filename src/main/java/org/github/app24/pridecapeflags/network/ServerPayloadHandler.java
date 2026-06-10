package org.github.app24.pridecapeflags.network;

import com.google.common.collect.Maps;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import org.github.app24.pridecapeflags.CapeFlagData;
import org.github.app24.pridecapeflags.PrideCapeFlags;

import java.util.HashMap;
import java.util.Objects;

public class ServerPayloadHandler {
    public static final HashMap<String, CapeFlagData> PLAYER_CAPES = Maps.newHashMap();

    public static void handleCapeData(final MinecraftServer minecraftServer, final ServerPlayer player, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf dataBuf, PacketSender packetSender){
        dataBuf.readUtf();
        String capeResourceLocation = dataBuf.readUtf();
        boolean useElytra = dataBuf.readBoolean();
        String elytraResourceLocation = dataBuf.readUtf();
        var data = new CapeFlagPacket(player.getStringUUID(), capeResourceLocation, useElytra, elytraResourceLocation);
        PLAYER_CAPES.put(data.uuid(), CapeFlagData.FromPacket(data));
        //MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer(), "Cannot send clientbound payloads on the client");
        //var players = server.getPlayerList().getPlayers();
        FriendlyByteBuf buf = data.ToByteBuf();
        for (ServerPlayer serverplayer : PlayerLookup.all(minecraftServer)) {
            if(serverplayer != player)
                ServerPlayNetworking.send(serverplayer, CapeFlagPacket.PACKET_ID, buf);
                //ServerPlayNetworking.send(serverplayer, data);
        }
    }

    public static void handleCapeFlagRequestOnMain(final MinecraftServer minecraftServer, final ServerPlayer player, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf dataBuf, PacketSender packetSender){
        PLAYER_CAPES.forEach((uuid, capeData)->{
            var capeFlag = new CapeFlagPacket(uuid, capeData.getCapeResourceLocation(), capeData.isUseElytra(), capeData.getElytraResourceLocation());
            //PacketDistributor.sendToPlayer((ServerPlayer) context.player(), capeFlag);
            ServerPlayNetworking.send(player, CapeFlagPacket.PACKET_ID, capeFlag.ToByteBuf());
        });
    }
}