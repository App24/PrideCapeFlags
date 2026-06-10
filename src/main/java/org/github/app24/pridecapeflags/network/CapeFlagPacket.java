package org.github.app24.pridecapeflags.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.PrideCapeFlags;

public record CapeFlagPacket(String uuid, String capeResourceLocation, boolean useElytra, String elytraResourceLocation) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CapeFlagPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(PrideCapeFlags.MOD_ID, "cape_flag"));

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
    }
}