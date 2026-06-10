package org.github.app24.pridecapeflags.client;

import com.google.common.collect.Maps;
import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import org.github.app24.pridecapeflags.CapeFlagData;
import org.github.app24.pridecapeflags.PrideCapeFlags;
import org.github.app24.pridecapeflags.client.config.ModConfig;
import org.github.app24.pridecapeflags.client.network.ClientPayloadHandler;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;

import java.util.HashMap;

public class PrideCapeFlagsClient implements ClientModInitializer {
	public static CapeFlagData CAPE_FLAG = CapeFlagData.Empty();
	public static final HashMap<String, CapeFlagData> PLAYER_CAPES = Maps.newHashMap();

	@Override
	public void onInitializeClient() {
		ClientPlayNetworking.registerGlobalReceiver(CapeFlagPacket.PACKET_ID, ClientPayloadHandler::handleCapeData);

		MidnightConfig.init(PrideCapeFlags.MOD_ID, ModConfig.class);

		ClientPlayConnectionEvents.JOIN.register((clientPacketListener, packetSender, minecraft) -> {
			PLAYER_CAPES.clear();
			receiveCapesFromServer();
			sendCapeToServer();
		});
	}

	public static void updateConfig(){
		CAPE_FLAG.setCapeResourceLocation(ModConfig.prideCapeFlag);
		CAPE_FLAG.setUseElytra(ModConfig.useElytraCape);
		CAPE_FLAG.setElytraResourceLocation(ModConfig.elytraPrideCapeFlag);
	}

	public static void sendCapeToServer(){
		try {
			ClientPlayNetworking.send(CapeFlagPacket.PACKET_ID, new CapeFlagPacket(Minecraft.getInstance().getUser().getUuid(), CAPE_FLAG.getCapeResourceLocation(), CAPE_FLAG.isUseElytra(), CAPE_FLAG.getElytraResourceLocation()).ToByteBuf());
		}catch(Exception ignored){}
	}

	static void receiveCapesFromServer(){
		try {
			ClientPlayNetworking.send(CapeFlagRequestPacket.PACKET_ID, new CapeFlagRequestPacket().ToByteBuf());
		}catch(Exception ignored){}
	}

	public static boolean checkFlagValid(ResourceLocation textureId){
		return Minecraft.getInstance().getTextureManager().getTexture(textureId.withPath(path->"flags/"+path+".png")) != MissingTextureAtlasSprite.getTexture();
	}
}