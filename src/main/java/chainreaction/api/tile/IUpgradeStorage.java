package chainreaction.api.tile;

public interface IUpgradeStorage {

    int getCapacityMod();

    void setCapacityMod(int amount);

    double getCostMod();

    void setCostMod(double amount);

    double getSpeedMod();

    void setSpeedMod(double amount);

    int getRTGMod();

    void setRTGMod(int amount);

}
