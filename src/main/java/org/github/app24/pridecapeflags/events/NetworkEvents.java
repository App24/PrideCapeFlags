package org.github.app24.pridecapeflags.events;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.github.app24.pridecapeflags.PrideCapeFlagsMod;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;
import org.github.app24.pridecapeflags.network.ClientPayloadHandler;
import org.github.app24.pridecapeflags.network.ServerPayloadHandler;

@EventBusSubscriber(modid = PrideCapeFlagsMod.MODID)
public class NetworkEvents {
    @SubscribeEvent
    static void PayloadHandlerEvent(final RegisterPayloadHandlersEvent event){
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playBidirectional(
                CapeFlagPacket.TYPE,
                CapeFlagPacket.STREAM_CODEC,
                new DirectionalPayloadHandler<>(
                        ClientPayloadHandler::handleCapeData,
                        ServerPayloadHandler::handleCapeData
                )
        );

        registrar.commonToServer(
                CapeFlagRequestPacket.TYPE,
                CapeFlagRequestPacket.STREAM_CODEC,
                ServerPayloadHandler::handleCapeFlagRequestOnMain
        );
    }
}
