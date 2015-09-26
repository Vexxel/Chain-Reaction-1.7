package com.zerren.chainreaction.handler.network.server.player;

import chainreaction.api.item.IKey;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.item.armor.ItemThrustPack;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 3/31/2015.
 */
public class MessageHotkey implements IMessage, IMessageHandler<MessageHotkey, IMessage> {

    private byte keyPressed;

    public MessageHotkey() { }

    public MessageHotkey(Key key) {
        if (key == Key.BOOST) {
            this.keyPressed = (byte)Key.BOOST.ordinal();
        }
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.keyPressed = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeByte(keyPressed);
    }

    @Override
    public IMessage onMessage(MessageHotkey message, MessageContext ctx) {

        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null) {
            //ChainReaction.log.info("recieved for " + player.getDisplayName());
            if (message.keyPressed == Key.BOOST.ordinal()) {
                //ChainReaction.log.info("Key pressed: " + message.keyPressed);
                ItemStack chest = player.inventory.armorInventory[2];
                if (chest != null && chest.getItem() instanceof ItemThrustPack) {
                    //ChainReaction.log.info("true");
                    ((ItemThrustPack) chest.getItem()).thrust(player);
                }
            }
        }
        return null;
    }

    public enum Key {
        BOOST
    }
}
