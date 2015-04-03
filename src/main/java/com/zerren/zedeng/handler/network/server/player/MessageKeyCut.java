package com.zerren.zedeng.handler.network.server.player;

import com.zerren.zedeng.proxy.ClientProxy;
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
    protected transient EntityPlayer player;
    protected transient ItemStack item;

    public MessageKeyCut() { }

    public MessageKeyCut(String code, EntityPlayer player, ItemStack item) {
        this.code = code;
        this.player = player;
        this.item = item;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        int codeLength = buf.readInt();
        this.code = new String(buf.readBytes(codeLength).array());
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(code.length());
        buf.writeBytes(code.getBytes());
    }

    @Override
    public IMessage onMessage(MessageKeyCut message, MessageContext ctx) {

        if (ctx.side.isClient())
            message.player = ClientProxy.getPlayer();
        else {
            message.player = ctx.getServerHandler().playerEntity;
        }

        if (message.code != null) {
            message.player.inventory.getCurrentItem().stackTagCompound.setString("code", message.code);
        }
        return null;
    }
}
