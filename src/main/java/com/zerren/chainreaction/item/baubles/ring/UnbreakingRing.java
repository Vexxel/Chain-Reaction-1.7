package com.zerren.chainreaction.item.baubles.ring;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.client.player.MessageToolDamage;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;

import java.util.List;

/**
 * Created by Zerren on 8/25/2017.
 */
public class UnbreakingRing extends BaubleCore {

    public UnbreakingRing() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.RING;
        name = "unbreakingRing";
        extraTooltipValue = " +" + Math.round(ConfigHandler.unbreakingChance * 100) + "%";

        MinecraftForge.EVENT_BUS.register(this);
    }


    @SubscribeEvent
    public void onBreaking(BlockEvent.BreakEvent event) {
        if (event.getPlayer() != null) {
            EntityPlayer player = event.getPlayer();

            ItemStack held = player.getHeldItem();
            if (held != null && BaubleHelper.hasCorrectRing(player, ItemRetriever.Items.bauble("unbreakingRing")))
                if (held.getItem().isDamageable())
                    if (held.getItem() instanceof ItemTool)
                        negateDamageItemOnce(held, player);
        }
    }

    private void negateDamageItemOnce(ItemStack item, EntityPlayer player) {
        ItemStack ring1 = BaublesApi.getBaubles(player).getStackInSlot(1);
        ItemStack ring2 = BaublesApi.getBaubles(player).getStackInSlot(2);

        if (ring1 != null && ring1.isItemEqual(ItemRetriever.Items.bauble("unbreakingRing"))) {
            if (NBTHelper.getShort(ring1, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                int damage = item.getItemDamage();
                NBTHelper.setShort(ring1, Names.NBT.BAUBLE_COOLDOWN, (short)2);
                if (player.worldObj.rand.nextFloat() <= ConfigHandler.unbreakingChance) {
                    item.setItemDamage(damage - 1);
                    PacketHandler.INSTANCE.sendTo(new MessageToolDamage(item.getItemDamage()), (EntityPlayerMP)player);
                }
            }
        }
        if (ring2 != null && ring2.isItemEqual(ItemRetriever.Items.bauble("unbreakingRing"))) {
            if (NBTHelper.getShort(ring2, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                int damage = item.getItemDamage();
                NBTHelper.setShort(ring2, Names.NBT.BAUBLE_COOLDOWN, (short)2);
                if (player.worldObj.rand.nextFloat() <= ConfigHandler.unbreakingChance) {
                    item.setItemDamage(damage - 1);
                }
            }
        }
    }
}
