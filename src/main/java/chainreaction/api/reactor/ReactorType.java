package chainreaction.api.reactor;

/**
 * Created by Zerren on 4/23/2015.
 */
public enum ReactorType {
    PWR(FuelType.FISSION_ROD),
    PEBBLE_BED(FuelType.FISSION_PEBBLE),
    GAS_CORE(FuelType.FISSION_GAS),
    MOLTEN_SALT(FuelType.FISSION_LIQUID),
    TOROIDAL_FUSION(FuelType.FUSION_FLUID),
    LASER_FUSION(FuelType.FUSION_PELLET),
    ANTIMATTER(FuelType.ANTIMATTER),
    RTG(FuelType.RTG_FUEL);

    private final FuelType type;

    ReactorType(FuelType type) {
        this.type = type;
    }

    public enum FuelType {
        FISSION_ROD,
        FISSION_PEBBLE,
        FISSION_LIQUID,
        FISSION_GAS,
        FUSION_FLUID,
        FUSION_PELLET,
        ANTIMATTER,
        RTG_FUEL
    }

    public FuelType getFuelType(ReactorType reactor) {
        return reactor.type;
    }

}
