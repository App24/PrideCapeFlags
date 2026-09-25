package org.github.app24.pridecapeflags;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import org.github.app24.pridecapeflags.config.NeoForgeModConfig;
import org.github.app24.pridecapeflags.gui.ConfigScreen;

@Mod(value = Constants.MOD_ID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class PrideCapeFlagsClientNeoForge {
    public PrideCapeFlagsClientNeoForge(ModContainer container) {
        PrideCapeFlagsClient.MOD_CONFIG = new NeoForgeModConfig();

        PrideCapeFlags.c2s = ClientPacketDistributor::sendToServer;

        container.registerExtensionPoint(IConfigScreenFactory.class, (container1, modListScreen) ->
                new ConfigurationScreen(container1, modListScreen, ConfigScreen::new));
    }

    @SubscribeEvent
    static void OnConfigurationLoaded(ModConfigEvent.Loading event){
        PrideCapeFlagsClient.updateCapeFlagConfig();
    }

    @SubscribeEvent
    static void OnConfigurationReloaded(ModConfigEvent.Reloading event){
        PrideCapeFlagsClient.updateConfig();
//        updateConfig();
//        if(Minecraft.getInstance().getConnection() !=null){
//            sendCapeToServer();
//        }
    }

    @SubscribeEvent
    static void OnClientLogIn(ClientPlayerNetworkEvent.LoggingIn event){
        PrideCapeFlagsClient.onPlayerJoinServer();
    }
}
