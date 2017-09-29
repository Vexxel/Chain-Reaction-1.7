package chainreaction.api.tile;

public interface IUpgradeStorage {

    int getCapacityMod();

    void setCapacityMod(int amount);

    float getCostMod();

    void setCostMod(float amount);

    float getSpeedMod();

    void setSpeedMod(float amount);

    int getRTGMod();

    void setRTGMod(int amount);

}
