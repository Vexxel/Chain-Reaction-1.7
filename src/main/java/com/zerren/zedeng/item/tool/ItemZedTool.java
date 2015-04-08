package com.zerren.zedeng.item.tool;

import buildcraft.api.tools.IToolWrench;
import com.zerren.zedeng.core.IThermometer;
import com.zerren.zedeng.item.ItemZE;
import com.zerren.zedeng.reference.Names;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidRegistry;

/**
 * Created by Zerren on 4/1/2015.
 */
public class ItemZedTool extends ItemZE implements IToolWrench, IThermometer {
    public ItemZedTool(String name, String[] subtypes, int stacksize, String folder, CreativeTabs tab) {
        super(name, subtypes, stacksize, folder, tab);
        setFull3D();

        setHarvestLevel("wrench", 0);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);

        System.out.println(FluidRegistry.getFluid(Names.Fluids.STEAM));

        if (block == null) {
            return false;
        }

        if (block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
            player.swingItem();
            return !world.isRemote;
        }
        return false;
    }

    @Override
    public boolean canWrench(EntityPlayer player, int x, int y, int z) {
        return player.inventory.getCurrentItem().getItemDamage() == 0;
    }

    @Override
    public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
        player.swingItem();
    }

    @Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
        return true;
    }

    @Override
    public boolean canReadTemperature(EntityPlayer player, int x, int y, int z) {
        return player.inventory.getCurrentItem().getItemDamage() == 1;
    }

    @Override
    public void readTemperature(EntityPlayer player, int x, int y, int z) {
        player.swingItem();
    }
}
