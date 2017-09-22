package chainreaction.api.tile;

import com.zerren.chainreaction.tile.container.ContainerCR;
import net.minecraft.inventory.ICrafting;

public interface IGuiTileData {

    void sendGUINetworkData(ContainerCR container, ICrafting iCrafting);

    void getGUINetworkData(int id, int v);
}
