package org.github.app24.pridecapeflags.client;

import net.minecraft.core.ClientAsset;
import net.minecraft.resources.Identifier;

public record CapeTexture(Identifier id, Identifier texturePath) implements ClientAsset.Texture {
    public CapeTexture(Identifier texture) {
        this(texture, texture.withPath(path -> "flags/" + path + ".png"));
    }
}
