package org.github.app24.pridecapeflags;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.ClientAsset;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;

public record CapeTexture(Identifier id, Identifier texturePath) implements ClientAsset.Texture {
    public CapeTexture(Identifier texture) {
        this(texture, texture.withPath(path -> "flags/" + path + ".png"));
    }
}
