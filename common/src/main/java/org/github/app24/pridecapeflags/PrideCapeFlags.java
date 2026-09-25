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

// This class is part of the common project meaning it is shared between all supported loaders. Code written here can only
// import and access the vanilla codebase, libraries used by vanilla, and optionally third party libraries that provide
// common compatible binaries. This means common code can not directly use loader specific concepts such as NeoForge events
// however it will be compatible with all supported mod loaders.
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