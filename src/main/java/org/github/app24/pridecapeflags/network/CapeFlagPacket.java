package org.github.app24.pridecapeflags.network;

import io.netty.buffer.ByteBuf;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.github.app24.pridecapeflags.PrideCapeFlags;

public record CapeFlagPacket(String uuid, String capeResourceLocation, boolean useElytra, String elytraResourceLocation) {
    public static final ResourceLocation PACKET_ID = ResourceLocation.tryBuild(PrideCapeFlags.MOD_ID, "cape_flag");

    public FriendlyByteBuf ToByteBuf(){
        FriendlyByteBuf buf = PacketByteBufs.create();
        buf.writeUtf(uuid);
        buf.writeUtf(capeResourceLocation);
        buf.writeBoolean(useElytra);
        buf.writeUtf(elytraResourceLocation);
        return buf;
    }

    public static CapeFlagPacket FromBuff(FriendlyByteBuf buff){
        var uuid = buff.readUtf();
        var capeResourceLocation = buff.readUtf();
        var useElytra = buff.readBoolean();
        var elytraResourceLocation = buff.readUtf();
        return new CapeFlagPacket(uuid,capeResourceLocation,useElytra,elytraResourceLocation);
    }

    /*public static final CustomPacketPayload.Type<CapeFlagPacket> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(PrideCapeFlags.MOD_ID, "cape_flag"));

    public static final StreamCodec<ByteBuf, CapeFlagPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.STRING_UTF8,
            CapeFlagPacket::uuid,
            ByteBufCodecs.STRING_UTF8,
            CapeFlagPacket::capeResourceLocation,
            ByteBufCodecs.BOOL,
            CapeFlagPacket::useElytra,
            ByteBufCodecs.STRING_UTF8,
            CapeFlagPacket::elytraResourceLocation,
            CapeFlagPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }*/
}