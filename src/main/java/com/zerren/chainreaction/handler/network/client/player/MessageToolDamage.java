package com.zerren.chainreaction.handler.network.client.player;

import com.zerren.chainreaction.ChainReaction;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by Zerren on 8/25/2017.
 */
public class MessageToolDamage implements IMessage, IMessageHandler<MessageToolDamage, IMessage> {

    public int damage;
    public MessageToolDamage() {

    }

    /**
     *
     * @param damage Byte of the shader index--set to 0 to disable
     */
    public MessageToolDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.damage = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(damage);
    }

    @Override
    public IMessage onMessage(MessageToolDamage message, MessageContext ctx) {

        if (!ctx.side.isClient()) return null;

        EntityClientPlayerMP player = FMLClientHandler.instance().getClientPlayerEntity();
        if (player.getHeldItem() != null)
            player.getHeldItem().setItemDamage(message.damage + 1);
        System.out.println("Item Update from Ring--new damage :" + message.damage);

        return null;
    }
}