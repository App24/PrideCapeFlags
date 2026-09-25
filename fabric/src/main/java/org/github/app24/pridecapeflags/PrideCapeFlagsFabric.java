package org.github.app24.pridecapeflags;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;
import org.github.app24.pridecapeflags.network.ServerPayloadHandler;

public class PrideCapeFlagsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        PrideCapeFlags.init();

        PrideCapeFlags.s2c = ServerPlayNetworking::send;
        PrideCapeFlags.getPlayers = PlayerLookup::all;

        var c2s = PayloadTypeRegistry.serverboundPlay();
        var s2c = PayloadTypeRegistry.clientboundPlay();

        c2s.register(CapeFlagPacket.TYPE, CapeFlagPacket.STREAM_CODEC);
        c2s.register(CapeFlagRequestPacket.TYPE, CapeFlagRequestPacket.STREAM_CODEC);

        s2c.register(CapeFlagPacket.TYPE, CapeFlagPacket.STREAM_CODEC);

        ServerPlayNetworking.registerGlobalReceiver(
                CapeFlagPacket.TYPE,
                (payload, context) -> ServerPayloadHandler.handleCapeData(payload, context.player(), context.server())
        );

        ServerPlayNetworking.registerGlobalReceiver(
                CapeFlagRequestPacket.TYPE,
                (payload, context) -> ServerPayloadHandler.handleCapeFlagRequestOnMain(payload, context.player())
        );
    }
}
