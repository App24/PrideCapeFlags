package org.github.app24.pridecapeflags;

import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import org.github.app24.pridecapeflags.platform.Services;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Items;

import java.util.Calendar;
import java.util.Collection;
import java.util.function.*;

public class PrideCapeFlags {

    public static Consumer<CustomPacketPayload> c2s;
    public static BiConsumer<ServerPlayer, CustomPacketPayload> s2c;
    public static Function<MinecraftServer, Collection<ServerPlayer>> getPlayers;

    static Calendar calendar;

    public static boolean IS_PRIDE = false;

    public static void init() {
        calendar = Calendar.getInstance();
        if (calendar.get(Calendar.MONTH) + 1 == 6) {
            IS_PRIDE = true;
        }
    }
}