package org.github.app24.pridecapeflags.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;
import org.github.app24.pridecapeflags.CapeTexture;
import org.github.app24.pridecapeflags.PrideCapeFlagsModClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player implements ClientAvatarEntity {
    public AbstractClientPlayerMixin(ClientLevel level, GameProfile gameProfile) {
        super(level, gameProfile);
    }

    @Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
    private void render(CallbackInfoReturnable<PlayerSkin> cir){
        if(!PrideCapeFlagsModClient.PLAYER_CAPES.containsKey(this.getStringUUID()) && !this.isLocalPlayer()){
            return;
        }
        var capeFlag = this.isLocalPlayer() ? PrideCapeFlagsModClient.CAPE_FLAG : PrideCapeFlagsModClient.PLAYER_CAPES.get(this.getStringUUID());
        var value = cir.getReturnValue();
        var capeResourceLocation = Identifier.tryParse(capeFlag.getCapeResourceLocation());
        if(capeResourceLocation == null) return;
        var elytraResourceLocation = capeResourceLocation;
        if(capeFlag.isUseElytra()){
            elytraResourceLocation = Identifier.tryParse(capeFlag.getElytraResourceLocation());
            if(elytraResourceLocation == null) return;
            if(!PrideCapeFlagsModClient.checkFlagValid(elytraResourceLocation)){
                elytraResourceLocation = capeResourceLocation;
            }
        }
        if(!PrideCapeFlagsModClient.checkFlagValid(capeResourceLocation)) return;
        value = new PlayerSkin(value.body(), new CapeTexture(capeResourceLocation), new CapeTexture(elytraResourceLocation), value.model(), value.secure());
        cir.setReturnValue(value);
    }
}
