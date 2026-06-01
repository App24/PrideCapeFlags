package org.github.app24.pridecapeflags.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.github.app24.pridecapeflags.PrideCapeFlagsModClient;

public class ClientPayloadHandler {
    public static void handleCapeData(final CapeFlagPacket data, final IPayloadContext context){
        PrideCapeFlagsModClient.PLAYER_CAPES.put(data.uuid(), data.resourceLocation());
    }
}
