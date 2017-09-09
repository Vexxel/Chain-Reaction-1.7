package com.zerren.chainreaction.item.tool;

import buildcraft.api.tools.IToolWrench;
import chainreaction.api.heat.IHeatHandler;
import chainreaction.api.item.IScanner;
import com.zerren.chainreaction.item.ItemCRBase;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 4/1/2015.
 */
public class ItemToolCR extends ItemCRBase implements IToolWrench, IScanner {
    public ItemToolCR(String name, String[] subtypes, String folder, CreativeTabs tab) {
        super(name, subtypes, 1, folder, tab);
        setFull3D();

        setHarvestLevel("wrench", 0);
    }

    @Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);
        if (block == null) {
            return false;
        }

        if (!world.isRemote) {
            if (canWrench(player, x, y, z) && block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
                wrenchUsed(player, x, y, z);
                return true;
            }

            if (player.isSneaking() && canScan(player, x, y, z)) {
                byte next = (byte)(NBTHelper.getByte(stack, Names.NBT.SCANNER_MODE) + 1);
                setMode(stack, next <= 2 ? next : 0);
                return true;
            }

            if (stack.getItemDamage() == 3) {
                TileEntity tile = world.getTileEntity(x, y, z);
                if (tile != null && tile instanceof IHeatHandler) {
                    IHeatHandler heatTile = (IHeatHandler) tile;
                    CoreUtility.addChat("Heat Stored: " + heatTile.getHeatStored(ForgeDirection.UNKNOWN) + "/" +heatTile.getMaxHeatStored(ForgeDirection.UNKNOWN) + " HU", player);
                }
                return true;
            }
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
    public boolean canScan(EntityPlayer player, int x, int y, int z) {
        return player.inventory.getCurrentItem().getItemDamage() == 1;
    }

    @Override
    public void scan(EntityPlayer player, int x, int y, int z) {
        player.swingItem();
    }

    @Override
    public byte getMode(ItemStack stack) {
        return NBTHelper.getByte(stack, Names.NBT.SCANNER_MODE);
    }

    @Override
    public void setMode(ItemStack stack, byte mode) {
        NBTHelper.setByte(stack, Names.NBT.SCANNER_MODE, mode);
    }
}
