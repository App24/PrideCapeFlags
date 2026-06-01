package org.github.app24.pridecapeflags;

import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.network.PacketDistributor;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;

import java.util.HashMap;

// This class will not load on dedicated servers. Accessing client side code from here is safe.
@Mod(value = PrideCapeFlagsMod.MODID, dist = Dist.CLIENT)
// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@EventBusSubscriber(modid = PrideCapeFlagsMod.MODID, value = Dist.CLIENT)
public class PrideCapeFlagsModClient {
    public static String CAPE_FLAG = "";
    public static final HashMap<String, String> PLAYER_CAPES = Maps.newHashMap();

    public PrideCapeFlagsModClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(FMLClientSetupEvent event) {

    }

    @SubscribeEvent
    static void OnConfigurationLoaded(ModConfigEvent.Loading event){
        CAPE_FLAG = Config.PRIDE_CAPE_FLAG.get();
    }

    @SubscribeEvent
    static void OnConfigurationReloaded(ModConfigEvent.Reloading event){
        CAPE_FLAG = Config.PRIDE_CAPE_FLAG.get();
        if(Minecraft.getInstance().getConnection() !=null){
            sendCapeToServer();
        }
    }

    @SubscribeEvent
    static void OnClientLogIn(ClientPlayerNetworkEvent.LoggingIn event){
        PLAYER_CAPES.clear();
        receiveCapesFromServer();
        sendCapeToServer();
    }

    static void sendCapeToServer(){
        PacketDistributor.sendToServer(new CapeFlagPacket(Minecraft.getInstance().getGameProfile().getId().toString(), Config.PRIDE_CAPE_FLAG.get()));
    }

    static void receiveCapesFromServer(){
        PacketDistributor.sendToServer(new CapeFlagRequestPacket());
    }
}
