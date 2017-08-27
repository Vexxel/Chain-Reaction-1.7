package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.ItemRetriever;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.MinecraftForge;

/**
 * Created by Zerren on 8/24/2017.
 */
public class HasteRing extends BaubleCore {

    public HasteRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "hasteRing";

        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onBreaking(net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed event) {
        if (event.entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entityLiving;

            ItemStack held = player.getHeldItem();

            if (held != null && held.getItem() instanceof ItemTool && BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble("hasteRing"))) {
                float haste = 1.5F;
                float f = event.originalSpeed * haste;
                event.newSpeed = f;
            }
        }
    }
}
