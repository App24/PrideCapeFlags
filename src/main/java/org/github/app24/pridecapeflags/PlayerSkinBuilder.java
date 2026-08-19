package org.github.app24.pridecapeflags;

import net.minecraft.core.ClientAsset;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.entity.player.PlayerSkin;
import org.jspecify.annotations.Nullable;

public class PlayerSkinBuilder {
    private ClientAsset.Texture body;
    private ClientAsset.@Nullable Texture cape;
    private ClientAsset.@Nullable Texture elytra;
    private PlayerModelType model;
    private boolean secure;

    public PlayerSkinBuilder(ClientAsset.Texture body, ClientAsset.@Nullable Texture cape, ClientAsset.@Nullable Texture elytra, PlayerModelType model, boolean secure){
        this.body = body;
        this.cape = cape;
        this.elytra = elytra;
        this.model = model;
        this.secure = secure;
    }

    public PlayerSkinBuilder(PlayerSkin playerSkin){
        this(playerSkin.body(), playerSkin.cape(), playerSkin.elytra(), playerSkin.model(), playerSkin.secure());
    }

    public PlayerSkin build(){
        return new PlayerSkin(body, cape, elytra, model, secure);
    }

    public boolean isSecure() {
        return secure;
    }

    public void setSecure(boolean secure) {
        this.secure = secure;
    }

    public PlayerModelType getModel() {
        return model;
    }

    public void setModel(PlayerModelType model) {
        this.model = model;
    }

    public ClientAsset.@Nullable Texture getElytra() {
        return elytra;
    }

    public void setElytra(ClientAsset.@Nullable Texture elytra) {
        this.elytra = elytra;
    }

    public ClientAsset.@Nullable Texture getCape() {
        return cape;
    }

    public void setCape(ClientAsset.@Nullable Texture cape) {
        this.cape = cape;
    }

    public ClientAsset.Texture getBody() {
        return body;
    }

    public void setBody(ClientAsset.Texture body) {
        this.body = body;
    }
}
