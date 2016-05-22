package com.zerren.chainreaction.core.tick;

import cofh.api.energy.IEnergyContainerItem;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.PacketHandler;
import com.zerren.chainreaction.handler.network.server.player.MessageHotkey;
import com.zerren.chainreaction.handler.network.server.player.MessageKeyCut;
import com.zerren.chainreaction.item.armor.ItemOxygenMask;
import com.zerren.chainreaction.item.armor.ItemThrustPack;
import com.zerren.chainreaction.utility.CRHotkey;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;

import java.util.Random;

/**
 * Created by Zerren on 9/16/2015.
 */
public class CRTickHandler {

    Random random = new Random();

    @SubscribeEvent
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
                if (Loader.isModLoaded("CoFHCore")) {
                    int charge = ((IEnergyContainerItem)headslot.getItem()).getEnergyStored(headslot);
                    int air = player.getAir();

                    if (air <= 150 && charge >= breathEnergy) {
                        player.setAir(300);
                        ((IEnergyContainerItem) headslot.getItem()).extractEnergy(headslot, breathEnergy, false);
                        for (int i = 0; i < 4; i++)
                            ChainReaction.proxy.bubbleFX(event.player, (random.nextFloat() - 0.5), 0, (random.nextFloat() - 0.5));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyInput(InputEvent.KeyInputEvent event) {

        if (CRHotkey.boost.isPressed()) {
            //ChainReaction.log.info("pressed");
            EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
            ItemStack chestslot = player.inventory.armorInventory[2];

            if (chestslot != null && chestslot.getItem() instanceof ItemThrustPack) {
                ((ItemThrustPack) chestslot.getItem()).thrust(player);
                PacketHandler.INSTANCE.sendToServer(new MessageHotkey(MessageHotkey.Key.BOOST));
            }
        }
    }
}
