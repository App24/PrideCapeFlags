package org.github.app24.pridecapeflags;

import org.github.app24.pridecapeflags.network.CapeFlagPacket;

public class CapeFlagData {
    private boolean showCape;
    private String capeResourceLocation;
    private boolean useElytra;
    private String elytraResourceLocation;

    public CapeFlagData(boolean showCape, String capeResourceLocation, boolean useElytra, String elytraResourceLocation){
        this.showCape = showCape;
        this.capeResourceLocation = capeResourceLocation;
        this.useElytra = useElytra;
        this.elytraResourceLocation = elytraResourceLocation;
    }

    public static CapeFlagData Empty(){
        return new CapeFlagData(true, "", false, "");
    }

    public static CapeFlagData FromPacket(CapeFlagPacket packet){
        return new CapeFlagData(packet.showCape(), packet.capeResourceLocation(), packet.useElytra(), packet.elytraResourceLocation());
    }

    public CapeFlagPacket ToPacket(){
        return new CapeFlagPacket("", showCape, capeResourceLocation, useElytra, elytraResourceLocation);
    }

    public String getCapeResourceLocation() {
        return capeResourceLocation;
    }

    public void setCapeResourceLocation(String capeResourceLocation) {
        this.capeResourceLocation = capeResourceLocation;
    }

    public boolean useElytra() {
        return useElytra;
    }

    public void setUseElytra(boolean useElytra) {
        this.useElytra = useElytra;
    }

    public String getElytraResourceLocation() {
        return elytraResourceLocation;
    }

    public void setElytraResourceLocation(String elytraResourceLocation) {
        this.elytraResourceLocation = elytraResourceLocation;
    }

    public boolean showCape() {
        return showCape;
    }

    public void setShowCape(boolean showCape) {
        this.showCape = showCape;
    }
}
