package com.zerren.chainreaction.core.tick;

import cofh.api.energy.IEnergyContainerItem;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.client.player.MessageDoubleJump;
import com.zerren.chainreaction.handler.network.client.player.MessageSetBonus;
import com.zerren.chainreaction.handler.network.server.player.MessageHotkey;
import com.zerren.chainreaction.item.armor.ItemOxygenMask;
import com.zerren.chainreaction.item.armor.ItemThrustPack;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.item.baubles.belt.JumpBelt;
import com.zerren.chainreaction.item.tool.ItemBaubleCR;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CRHotkey;
import com.zerren.chainreaction.utility.CRMath;
import com.zerren.chainreaction.utility.ItemRetriever;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import org.lwjgl.input.Keyboard;

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

        if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
            //ChainReaction.log.info("pressed");
            EntityPlayer player = FMLClientHandler.instance().getClient().thePlayer;
            if (BaubleHelper.hasCorrectBelt(player, ItemRetriever.Items.bauble("jumpBelt"))) {
                ItemStack belt = BaubleHelper.getBelt(player);

                if (CRMath.isWithin(BaubleCore.getCooldown(belt), 1, 495)) {
                    if (player.isAirBorne) {
                        PacketHandler.INSTANCE.sendToServer(new MessageDoubleJump());
                        JumpBelt.doubleJump(player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onEntityConstructing(EntityEvent.EntityConstructing event) {
        if (event.entity instanceof EntityPlayer && PlayerSetBonus.get((EntityPlayer)event.entity) == null) {
            PlayerSetBonus.register((EntityPlayer) event.entity);
        }
    }

    @SubscribeEvent
    public void joinWorld(EntityJoinWorldEvent event) {
        if (event.entity instanceof EntityPlayerMP) {
            PacketHandler.INSTANCE.sendTo(new MessageSetBonus((EntityPlayer)event.entity), (EntityPlayerMP)event.entity);
        }
    }

    @SubscribeEvent
    public void onPlayerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event) {
        if (event.player != null) {

            if (!event.player.worldObj.isRemote) {
                EntityPlayer player = event.player;
                ItemStack[] baubles = BaubleHelper.getBaubles(player);

                for (ItemStack bauble : baubles) {
                    if (bauble != null && bauble.getItem() instanceof ItemBaubleCR) {
                        ItemBaubleCR.getBauble(bauble).onEquipped(bauble, player);
                    }
                }
            }
            for (SetBonus set : SetBonus.values()) {
                if (PlayerSetBonus.get(event.player).getSetStatus(set)) {
                    SetBonus.activateEffect(event.player, set);
                    //System.out.println("Active now: " + set);
                }
            }
        }
    }

    /*@SubscribeEvent
    public void onPostRender(RenderPlayerEvent.Specials.Post event) {
        AbstractClientPlayer player = (AbstractClientPlayer) event.entityPlayer;
        boolean flag = (event.entityPlayer.getUniqueID().equals(Reference.ModInfo.VEXXEL_UUID));
        //System.out.println(flag);
    }*/
}
