package chainreaction.api.block;

/**
 * Created by Zerren on 4/11/2015.
 */
public interface IThermalTile {

    /**
     * Gets the arbitrary thermal 'units' that are stored in this tile entity: this is usable heat, unlike wasteHeatUnits
     * @return thermalUnits
     */
    float getThermalUnits();

    /**
     * Sets thermal 'units' that are stored in this tile entity: this is usable heat, unlike wasteHeatUnits
     * @param units thermalUnits
     */
    void setThermalUnits(float units);

    /**
     * Gets the arbitrary waste heat 'units' that are stored in this tile entity: this is usually heat on the 'cold' side of a system, and as such to maintain maximum
     * thermal equilibrium, must be removed to maintain efficiency.
     * @return wasteHeat
     */
    int getWasteHeat();

    /**
     * Sets waste heat 'units' that are stored in this tile entity: this is not usable heat, unlike thermalUnits
     * @param units wasteHeat
     */
    void setWasteHeat(int units);
}
