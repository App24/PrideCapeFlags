package org.github.app24.pridecapeflags;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.github.app24.pridecapeflags.config.FabricModConfig;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.ClientPayloadHandler;

public class PrideCapeFlagsClientFabric implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        PrideCapeFlagsClient.MOD_CONFIG = new FabricModConfig();

        FabricModConfig.HANDLER.load();
        PrideCapeFlagsClient.updateCapeFlagConfig();

        PrideCapeFlags.c2s = ClientPlayNetworking::send;

        ClientPlayNetworking.registerGlobalReceiver(CapeFlagPacket.TYPE,
                (payload, context) -> ClientPayloadHandler.handleCapeData(payload));

        ClientPlayConnectionEvents.JOIN.register((listener, sender, client) -> {
            PrideCapeFlagsClient.onPlayerJoinServer();
        });
    }
}
