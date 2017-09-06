package com.zerren.chainreaction.core.tick;

import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;

/**
 * Created by Zerren on 8/26/2017.
 */
public class SetBonusHandler {

    private static final AttributeModifier druidSpeedBonus = (new AttributeModifier(Names.UUIDs.DRUID_SET_SPEED_BONUS_UUID, Names.UUIDs.DRUID_SET_SPEED_BONUS_NAME, ConfigHandler.druidSpeedBonus, 2)).setSaved(false);

    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event) {
        if(event.recentlyHit && event.entityLiving instanceof EntitySkeleton && event.source.getEntity() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.source.getEntity();
            if(PlayerSetBonus.get(player).getSetStatus(SetBonus.SKULLFIRE)){
                // ok, we need to drop a skull then.
                if (player.worldObj.rand.nextFloat() <= ConfigHandler.skullfireChance) {
                    if (event.drops.isEmpty()) {
                        addDrop(event, new ItemStack(Items.skull, 1, 1));
                    }
                    else {
                        int skulls = 0;

                        for (int i=0; i<event.drops.size(); i++) {
                            EntityItem drop = event.drops.get(i);
                            ItemStack stack = drop.getEntityItem();
                            if (stack.getItem() == Items.skull) {
                                if (stack.getItemDamage() == 1) {
                                    skulls++;
                                } else if (stack.getItemDamage() == 0) {
                                    skulls++;
                                    stack.setItemDamage(1);
                                }
                            }
                        }

                        if (skulls == 0) {
                            addDrop(event, new ItemStack(Items.skull, 1, 1));
                        }
                    }
                }
            }
        }
    }

    private void addDrop(LivingDropsEvent event, ItemStack drop) {
        EntityItem entityitem = new EntityItem(event.entityLiving.worldObj, event.entityLiving.posX, event.entityLiving.posY, event.entityLiving.posZ, drop);
        entityitem.delayBeforeCanPickup = 10;
        event.drops.add(entityitem);
    }

    public static void setDruidSpeed(EntityPlayer player) {
        IAttributeInstance speed = player.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

        if(PlayerSetBonus.get(player).getSetStatus(SetBonus.DRUID) && player.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(Names.UUIDs.DRUID_SET_SPEED_BONUS_UUID) == null){
            speed.applyModifier(druidSpeedBonus);
        }
        else if (player.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getModifier(Names.UUIDs.DRUID_SET_SPEED_BONUS_UUID) != null){
            speed.removeModifier(druidSpeedBonus);
        }

    }
}
