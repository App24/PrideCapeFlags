package org.github.app24.pridecapeflags.network;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.github.app24.pridecapeflags.PrideCapeFlags;

public record CapeFlagRequestPacket() {
    public static final ResourceLocation PACKET_ID = ResourceLocation.tryBuild(PrideCapeFlags.MOD_ID, "cape_flag_request");

    public FriendlyByteBuf ToByteBuf(){
        FriendlyByteBuf buf = PacketByteBufs.empty();
        return buf;
    }
}