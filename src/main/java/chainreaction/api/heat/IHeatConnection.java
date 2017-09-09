package chainreaction.api.heat;

import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 9/8/2017.
 */
public interface IHeatConnection {

    /**
     * Returns TRUE if the TileEntity can connect on a given side.
     */
    boolean canConnectHeat(ForgeDirection from);
}
