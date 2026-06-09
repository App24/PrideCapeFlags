package org.github.app24.pridecapeflags;

import org.github.app24.pridecapeflags.network.CapeFlagPacket;

public class CapeFlagData {
    private String capeResourceLocation;
    private boolean useElytra;
    private String elytraResourceLocation;

    public CapeFlagData(String capeResourceLocation, boolean useElytra, String elytraResourceLocation){
        this.capeResourceLocation = capeResourceLocation;
        this.useElytra = useElytra;
        this.elytraResourceLocation = elytraResourceLocation;
    }

    public static CapeFlagData Empty(){
        return new CapeFlagData("", false, "");
    }

    public static CapeFlagData FromPacket(CapeFlagPacket packet){
        return new CapeFlagData(packet.capeResourceLocation(), packet.useElytra(), packet.elytraResourceLocation());
    }


    public String getCapeResourceLocation() {
        return capeResourceLocation;
    }

    public void setCapeResourceLocation(String capeResourceLocation) {
        this.capeResourceLocation = capeResourceLocation;
    }

    public boolean isUseElytra() {
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
}
