package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.tile.plumbing.TEGasTank;
import com.zerren.chainreaction.utility.NBTHelper;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

/**
 * Created by Zerren on 3/6/2015.
 */
public class ItemBlockExchanger extends ItemMultiTexture {
    public ItemBlockExchanger(Block block) {
        super(CRBlocks.plumbing, CRBlocks.plumbing, Names.Blocks.PLUMBING_SUBTYPES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 0) {
            TooltipHelper.addSizeInfo(list, "5x1x1", "gui.multiblock.wrench.center.name");
            TooltipHelper.addMaterialCostInfo(list, MultiblockCost.LIQUID_HEAT_EXCHANGER);
        }

        //Gas Tank
        if (stack.getItemDamage() == 3) {
            if (stack.hasTagCompound()) {
                NBTTagCompound tag = stack.getTagCompound().getCompoundTag(Names.NBT.TANK);
                FluidStack fluid = FluidStack.loadFluidStackFromNBT(tag);

                list.add(fluid.getLocalizedName() + ": " + fluid.amount + "mB");
            }
        }
    }

    @Override
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta) {
        super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, meta);

        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (tileEntity instanceof TEGasTank) {
            if (stack.hasTagCompound()) {
                ((TEGasTank) tileEntity).tank.readFromNBT(NBTHelper.getTagCompound(stack, Names.NBT.TANK));
            }
        }
        return true;
    }
}