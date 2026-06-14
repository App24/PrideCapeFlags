package org.github.app24.pridecapeflags.client.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.PlayerSkin;
import org.github.app24.pridecapeflags.client.CapeTexture;
import org.github.app24.pridecapeflags.client.PrideCapeFlagsClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin extends Player {
	public AbstractClientPlayerMixin(ClientLevel clientLevel, GameProfile gameProfile) {
		super(clientLevel, gameProfile);
	}

	@Inject(method = "getSkin", at = @At("RETURN"), cancellable = true)
	private void render(CallbackInfoReturnable<PlayerSkin> cir){
		if(!PrideCapeFlagsClient.PLAYER_CAPES.containsKey(this.getStringUUID()) && !this.isLocalPlayer()){
			return;
		}
		var capeFlag = this.isLocalPlayer() ? PrideCapeFlagsClient.CAPE_FLAG : PrideCapeFlagsClient.PLAYER_CAPES.get(this.getStringUUID());
		if(!capeFlag.showCape()) return;
		var value = cir.getReturnValue();
		var capeResourceLocation = Identifier.tryParse(capeFlag.getCapeResourceLocation());
		if(capeResourceLocation == null) return;
		var elytraResourceLocation = capeResourceLocation;
		if(capeFlag.useElytra()){
			elytraResourceLocation = Identifier.tryParse(capeFlag.getElytraResourceLocation());
			if(elytraResourceLocation == null) return;
			if(!PrideCapeFlagsClient.checkFlagValid(elytraResourceLocation)){
				elytraResourceLocation = capeResourceLocation;
			}
		}
		if(!PrideCapeFlagsClient.checkFlagValid(capeResourceLocation)) return;
		value = new PlayerSkin(value.body(), new CapeTexture(capeResourceLocation), new CapeTexture(elytraResourceLocation), value.model(), value.secure());
		cir.setReturnValue(value);
	}
}