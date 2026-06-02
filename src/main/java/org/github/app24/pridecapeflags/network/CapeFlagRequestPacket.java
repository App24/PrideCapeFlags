package org.github.app24.pridecapeflags.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.github.app24.pridecapeflags.PrideCapeFlagsMod;

public record CapeFlagRequestPacket() implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<CapeFlagRequestPacket> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(PrideCapeFlagsMod.MODID, "cape_flag_request"));

    public static final StreamCodec<ByteBuf, CapeFlagRequestPacket> STREAM_CODEC = new StreamCodec<ByteBuf, CapeFlagRequestPacket>() {
        @Override
        public CapeFlagRequestPacket decode(ByteBuf buffer) {
            return new CapeFlagRequestPacket();
        }

        @Override
        public void encode(ByteBuf buffer, CapeFlagRequestPacket value) {

        }
    };

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
