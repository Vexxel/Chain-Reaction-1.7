package com.zerren.chainreaction.item.baubles.belt;

import baubles.api.BaubleType;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.utility.BaubleHelper;
import com.zerren.chainreaction.utility.CRMath;
import com.zerren.chainreaction.utility.ItemRetriever;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Keyboard;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

/**
 * Created by Zerren on 8/24/2017.
 */
public class JumpBelt extends BaubleCore {


    public JumpBelt() {
        rarity = EnumRarity.uncommon;
        type = BaubleType.BELT;
        name = "jumpBelt";
        extraTooltipValue = " +" + ConfigHandler.jumpModifier;

        MinecraftForge.EVENT_BUS.register(this);

    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;

            if (CRMath.isWithin(NBTHelper.getShort(stack, Names.NBT.BAUBLE_COOLDOWN), 1, 490)) {

                if (player.isAirBorne && Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                    System.out.println("Double Jump");
                    player.jump();
                    player.fallDistance = 0;
                    NBTHelper.setShort(stack, Names.NBT.BAUBLE_COOLDOWN, (short)0);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack belt = BaubleHelper.getBelt(player);

            if (belt != null && belt.isItemEqual(ItemRetriever.Items.bauble(name)) && player.onGround) {
                if (NBTHelper.getShort(belt, Names.NBT.BAUBLE_COOLDOWN) == 0) {
                    NBTHelper.setShort(belt, Names.NBT.BAUBLE_COOLDOWN, (short)500);
                }
            }
        }
    }
}
