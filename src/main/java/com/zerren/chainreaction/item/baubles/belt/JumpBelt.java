package com.zerren.chainreaction.item.baubles.belt;

import baubles.api.BaubleType;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.handler.network.client.player.MessageDoubleJump;
import com.zerren.chainreaction.handler.network.server.player.MessageHotkey;
import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.utility.*;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
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
        rarity = EnumRarity.rare;
        type = BaubleType.BELT;
        name = "jumpBelt";

        MinecraftForge.EVENT_BUS.register(this);

    }

    public void tick(ItemStack stack, EntityLivingBase entity) {
        super.tick(stack, entity);

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (player.onGround) {
                setCooldown(stack);
            }
        }
    }

    public static void doubleJump(EntityPlayer player) {
        ItemStack belt = BaubleHelper.getBelt(player);

        //player.jump();
        player.motionY = 0.41999998688697815D;
        setCooldown(belt);
        player.fallDistance = 0;

        double px = player.posX + player.worldObj.rand.nextFloat() / 5;
        double py = player.posY - 0.5F;
        double pz = player.posZ + player.worldObj.rand.nextFloat() / 5;

        for (int i = 0; i < 8; i++) {
            double vx = (player.worldObj.rand.nextFloat() - 0.5) * 5;
            double vy = (player.worldObj.rand.nextFloat() - 0.5) * 2;
            double vz = (player.worldObj.rand.nextFloat() - 0.5) * 5;

            ChainReaction.proxy.steamFX(player.getEntityWorld(), px, py, pz, vx, vy, vz, 4);
        }
    }

    @SubscribeEvent
    public void onPlayerJump(LivingEvent.LivingJumpEvent event) {
        if (event.entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.entity;
            ItemStack belt = BaubleHelper.getBelt(player);

            if (belt != null && belt.isItemEqual(getStack())) {

                if (getCooldown(belt) == 0) {
                    if (player.onGround)
                        setCooldown(belt, 500);
                }
            }
        }
    }
}
