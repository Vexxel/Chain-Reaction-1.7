package chainreaction.api.tile;

import com.zerren.chainreaction.tile.container.ContainerCR;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.inventory.ICrafting;

@SideOnly(Side.CLIENT)
public interface IGuiTileData {

    @SideOnly(Side.CLIENT)
    void sendGUINetworkData(ContainerCR container, ICrafting iCrafting);

    @SideOnly(Side.CLIENT)
    void getGUINetworkData(int id, int v);
}
