package com.zerren.chainreaction.core.tick;

import com.sun.net.httpserver.Filter;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.item.armor.ItemOxygenMask;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.Random;

/**
 * Created by Zerren on 9/16/2015.
 */
public class ArmorTickHandler {

    Random random = new Random();

    public void onTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && event.side == Side.SERVER) {
            EntityPlayer player = event.player;
            ItemStack headslot = player.inventory.armorInventory[3];

            int breathEnergy = 500;

            if (headslot != null && headslot.getItem() instanceof ItemOxygenMask) {
                if (Loader.isModLoaded("IC2")) {
                    double charge = ElectricItem.manager.getCharge(headslot);
                    int air = player.getAir();

                    if (air <= 150 && charge >= breathEnergy) {
                        player.setAir(300);
                        ElectricItem.manager.discharge(headslot, breathEnergy, 5, true, false, false);
                        for (int i = 0; i < 4; i++)
                            ChainReaction.proxy.bubbleFX(event.player, (random.nextFloat() - 0.5), 0, (random.nextFloat() - 0.5));
                    }
                }
            }
        }
    }
}
