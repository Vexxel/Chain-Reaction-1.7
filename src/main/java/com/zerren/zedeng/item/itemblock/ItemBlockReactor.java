package com.zerren.zedeng.item.itemblock;

import zedeng.api.block.ZedBlocks;
import com.zerren.zedeng.reference.MultiblockCost;
import com.zerren.zedeng.reference.Names;
import com.zerren.zedeng.utility.TooltipHelper;
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
        super(ZedBlocks.reactor, ZedBlocks.reactor, Names.Blocks.REACTOR_SUBTYPES);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 0) {
            TooltipHelper.addSizeInfo(list, "3x7x3", "gui.multiblock.wrench.center_top.name");
            TooltipHelper.addMaterialCostInfo(list, MultiblockCost.PRESSURIZED_WATER_REACTOR);
        }
    }
}