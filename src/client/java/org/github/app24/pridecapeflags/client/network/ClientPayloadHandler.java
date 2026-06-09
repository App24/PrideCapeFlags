package org.github.app24.pridecapeflags.client.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import org.github.app24.pridecapeflags.CapeFlagData;
import org.github.app24.pridecapeflags.client.PrideCapeFlagsClient;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;

public class ClientPayloadHandler {
    public static void handleCapeData(final CapeFlagPacket data, final ClientPlayNetworking.Context context){
        PrideCapeFlagsClient.PLAYER_CAPES.put(data.uuid(), CapeFlagData.FromPacket(data));
    }
}
