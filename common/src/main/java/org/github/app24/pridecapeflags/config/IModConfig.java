package org.github.app24.pridecapeflags.config;

public interface IModConfig {

    boolean showPrideTitle();
    void setPrideTitle(boolean show);

    boolean showPrideCape();
    void setPrideCape(boolean show);

    String getPrideCapeFlag();
    void setPrideCapeFlag(String flag);

    boolean useElytraCape();
    void setElytraCape(boolean use);

    String getElytraCapeFlag();
    void setElytraCapeFlag(String flag);

    void saveConfig();
    void updateConfig();
}
