package com.zerren.chainreaction.handler.network.client.player;

import com.zerren.chainreaction.item.baubles.belt.JumpBelt;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Zerren on 3/31/2015.
 */
public class MessageDoubleJump implements IMessage, IMessageHandler<MessageDoubleJump, IMessage> {

    private float distance;

    public MessageDoubleJump() {
        this.distance = 0;
    }

    public MessageDoubleJump(float distance) {
        this.distance = distance;
    }
    @Override
    public void fromBytes(ByteBuf buf) {
        this.distance = buf.readFloat();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeFloat(distance);
    }

    @Override
    public IMessage onMessage(MessageDoubleJump message, MessageContext ctx) {

        EntityPlayer player = ctx.getServerHandler().playerEntity;

        if (player != null) {
            //ChainReaction.log.info("recieved for " + player.getDisplayName());
            JumpBelt.doubleJump(player);
            player.fallDistance = 0;
        }
        return null;
    }
}
