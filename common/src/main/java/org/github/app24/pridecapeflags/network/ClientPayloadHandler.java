package org.github.app24.pridecapeflags.network;

import org.github.app24.pridecapeflags.CapeFlagData;
import org.github.app24.pridecapeflags.PrideCapeFlagsClient;

public class ClientPayloadHandler {
    public static void handleCapeData(final CapeFlagPacket data){
        PrideCapeFlagsClient.PLAYER_CAPES.put(data.uuid(), CapeFlagData.FromPacket(data));
    }
}
