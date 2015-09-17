package com.zerren.chainreaction.core.tick;

import com.zerren.chainreaction.item.armor.ItemOxygenMask;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 9/16/2015.
 */
public class ArmorTickHandler {

    @SubscribeEvent
    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            EntityPlayer player = event.player;
            ItemStack headslot = player.inventory.armorInventory[3];

            if (headslot != null && headslot.getItem() instanceof ItemOxygenMask) {
                player.setAir(300);
            }
        }
    }
}
