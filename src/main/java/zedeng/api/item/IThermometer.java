package zedeng.api.item;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Zerren on 4/1/2015.
 */
public interface IThermometer {

    boolean canReadTemperature(EntityPlayer player, int x, int y, int z);

    void readTemperature(EntityPlayer player, int x, int y, int z);
}
