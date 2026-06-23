package org.github.app24.pridecapeflags;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import org.github.app24.pridecapeflags.network.CapeFlagPacket;
import org.github.app24.pridecapeflags.network.CapeFlagRequestPacket;
import org.github.app24.pridecapeflags.network.ServerPayloadHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;

public class PrideCapeFlags implements ModInitializer {
	public static final String MOD_ID = "pridecapeflags";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	static Calendar calendar;

	public static boolean IS_PRIDE = false;

	@Override
	public void onInitialize() {
		/*var c2s = PayloadTypeRegistry.playC2S();
		var s2c = PayloadTypeRegistry.playS2C();

		c2s.register(CapeFlagPacket.TYPE, CapeFlagPacket.STREAM_CODEC);
		c2s.register(CapeFlagRequestPacket.TYPE, CapeFlagRequestPacket.STREAM_CODEC);

		s2c.register(CapeFlagPacket.TYPE, CapeFlagPacket.STREAM_CODEC);*/

		ServerPlayNetworking.registerGlobalReceiver(CapeFlagPacket.PACKET_ID, ServerPayloadHandler::handleCapeData);
		ServerPlayNetworking.registerGlobalReceiver(CapeFlagRequestPacket.PACKET_ID, ServerPayloadHandler::handleCapeFlagRequestOnMain);

		calendar = Calendar.getInstance();
		if (calendar.get(Calendar.MONTH) + 1 == 6) {
			IS_PRIDE = true;
		}
	}
}