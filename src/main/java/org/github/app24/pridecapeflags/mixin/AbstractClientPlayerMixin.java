package org.github.app24.pridecapeflags.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.texture.MissingTextureAtlasSprite;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import org.github.app24.pridecapeflags.PrideCapeFlagsMod;
import org.github.app24.pridecapeflags.PrideCapeFlagsModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {
    public AbstractClientPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
        super(clientLevel, clientLevel.getSharedSpawnPos(), clientLevel.getSharedSpawnAngle(), gameProfile);
    }

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    private void render(CallbackInfoReturnable<PlayerSkin> cir){
        if(!PrideCapeFlagsModClient.PLAYER_CAPES.containsKey(this.getStringUUID()) && !this.isLocalPlayer()){
            return;
        }
        var capeFlag = this.isLocalPlayer() ? PrideCapeFlagsModClient.CAPE_FLAG : PrideCapeFlagsModClient.PLAYER_CAPES.get(this.getStringUUID());
        var value = cir.getReturnValue();
        var capeResourceLocation = ResourceLocation.tryParse(capeFlag.getCapeResourceLocation());
        if(capeResourceLocation == null) return;
        var elytraResourceLocation = capeResourceLocation;
        if(capeFlag.isUseElytra()){
            elytraResourceLocation = ResourceLocation.tryParse(capeFlag.getElytraResourceLocation());
            if(elytraResourceLocation == null) return;
            if(Minecraft.getInstance().getTextureManager().getTexture(elytraResourceLocation) == MissingTextureAtlasSprite.getTexture()){
                elytraResourceLocation = capeResourceLocation;
            }
        }
        if(Minecraft.getInstance().getTextureManager().getTexture(capeResourceLocation) == MissingTextureAtlasSprite.getTexture()) return;
        value = new PlayerSkin(value.texture(), value.textureUrl(), capeResourceLocation, elytraResourceLocation, value.model(), value.secure());
        cir.setReturnValue(value);
    }
}
