package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;

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
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 0) {
            TooltipHelper.addSizeInfo(list, "5x1x1", "gui.multiblock.wrench.center.name");
            TooltipHelper.addMaterialCostInfo(list, MultiblockCost.LIQUID_HEAT_EXCHANGER);
        }
    }
}