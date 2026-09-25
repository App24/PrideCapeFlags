package org.github.app24.pridecapeflags;

import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import org.github.app24.pridecapeflags.config.NeoForgeModConfig;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;
import org.github.app24.pridecapeflags.network.ClientPayloadHandler;
import org.github.app24.pridecapeflags.network.ServerPayloadHandler;

import java.util.Objects;

@Mod(Constants.MOD_ID)
public class PrideCapeFlagsNeoForge {

    public PrideCapeFlagsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        PrideCapeFlags.init();

        modContainer.registerConfig(ModConfig.Type.CLIENT, NeoForgeModConfig.SPEC);

        PrideCapeFlags.s2c = PacketDistributor::sendToPlayer;
        PrideCapeFlags.getPlayers = (_)->{
            MinecraftServer server = Objects.requireNonNull(ServerLifecycleHooks.getCurrentServer(), "Cannot send clientbound payloads on the client");
            return server.getPlayerList().getPlayers();
        };

        eventBus.addListener((RegisterPayloadHandlersEvent event)->{
            PayloadRegistrar registrar = event.registrar(Constants.MOD_ID);
            registrar.playBidirectional(
                    CapeFlagPacket.TYPE,
                    CapeFlagPacket.STREAM_CODEC,
                    (payload, context) -> ServerPayloadHandler.handleCapeData(payload, (ServerPlayer) context.player(), null),
                    (payload, context) -> ClientPayloadHandler.handleCapeData(payload)
            );

            registrar.playToServer(
                    CapeFlagRequestPacket.TYPE,
                    CapeFlagRequestPacket.STREAM_CODEC,
                    (payload, context) -> ServerPayloadHandler.handleCapeFlagRequestOnMain(payload, (ServerPlayer) context.player())
            );
        });
    }
}