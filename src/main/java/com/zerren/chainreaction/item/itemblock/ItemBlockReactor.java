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
 * Created by Zerren on 2/19/2015.
 */
public class ItemBlockReactor extends ItemMultiTexture {
    public ItemBlockReactor(Block block) {
        super(CRBlocks.reactor, CRBlocks.reactor, Names.Blocks.REACTOR_SUBTYPES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 0) {
            TooltipHelper.addSizeInfo(list, "3x7x3", "gui.multiblock.wrench.center_assembly.name");
            TooltipHelper.addMaterialCostInfo(list, MultiblockCost.PRESSURIZED_WATER_REACTOR);
        }
    }
}