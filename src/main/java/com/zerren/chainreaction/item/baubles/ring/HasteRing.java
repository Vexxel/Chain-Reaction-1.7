package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.TooltipHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.List;

/**
 * Created by Zerren on 8/24/2017.
 */
public class HasteRing extends BaubleCore {

    public HasteRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "hasteRing";
        extraTooltipValue = " +" + Math.round(ConfigHandler.hasteModifier * 100) + "%";

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBreaking(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            ItemStack held = player.getHeldItem();

            if (held != null && held.getItem() instanceof ItemTool && BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble("hasteRing"))) {
                event.newSpeed = event.originalSpeed * (1 + (ConfigHandler.hasteModifier));
            }
        }
    }

    public void addTooltip(ItemStack stack, EntityPlayer player, List list, boolean par4) {
        String s1 = EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.bauble." + name + ".name") + " +" + (Math.round(ConfigHandler.hasteModifier * 100))+ "%";
        list.add(s1);
    }
}
