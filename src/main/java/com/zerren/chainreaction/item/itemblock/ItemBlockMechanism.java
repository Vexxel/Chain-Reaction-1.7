package com.zerren.chainreaction.item.itemblock;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemMultiTexture;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;

import java.util.List;

/**
 * Created by Zerren on 9/22/2015.
 */
public class ItemBlockMechanism extends ItemMultiTexture {
    public ItemBlockMechanism(Block block) {
        super(CRBlocks.mechanism, CRBlocks.mechanism, Names.Blocks.MECHANISM_SUBTYPES);
    }

    /*@Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 2) {
            String rtgFuel = CoreUtility.translate("gui.rtg.pu238.name");
            list.add(EnumChatFormatting.RED + rtgFuel);
        }
    }*/
}
