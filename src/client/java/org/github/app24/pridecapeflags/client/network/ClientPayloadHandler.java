package org.github.app24.pridecapeflags.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import org.github.app24.pridecapeflags.CapeFlagData;
import org.github.app24.pridecapeflags.client.PrideCapeFlagsClient;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;

public class ClientPayloadHandler {
    public static void handleCapeData(final Minecraft minecraft, ClientPacketListener packetListener, FriendlyByteBuf dataBuf, PacketSender packetSender){
        FriendlyByteBuf buff = new FriendlyByteBuf(dataBuf.copy());
        var uuid = dataBuf.readUtf();
        PrideCapeFlagsClient.PLAYER_CAPES.put(uuid, CapeFlagData.FromPacket(CapeFlagPacket.FromBuff(buff)));
    }
}
