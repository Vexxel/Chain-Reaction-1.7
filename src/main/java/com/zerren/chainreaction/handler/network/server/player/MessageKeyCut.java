package com.zerren.chainreaction.handler.network.server.player;

import chainreaction.api.item.IKey;
import com.zerren.chainreaction.core.proxy.ClientProxy;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 3/31/2015.
 */
public class MessageKeyCut implements IMessage, IMessageHandler<MessageKeyCut, IMessage> {

    private String code;

    public MessageKeyCut() { }

    public MessageKeyCut(String code) {
        this.code = code;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        int codeLength = buf.readByte();
        this.code = new String(buf.readBytes(codeLength).array());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(code.length());
        buf.writeBytes(code.getBytes());
    }

    @Override
    public IMessage onMessage(MessageKeyCut message, MessageContext ctx) {

        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null && player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof IKey && message.code != null) {
            player.inventory.getCurrentItem().stackTagCompound.setString("code", message.code);
        }
        return null;
    }
}
