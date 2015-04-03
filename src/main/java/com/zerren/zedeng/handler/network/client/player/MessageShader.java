package com.zerren.zedeng.handler.network.client.player;

import com.zerren.zedeng.ZederrianEngineering;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.renderer.EntityRenderer;

/**
 * Created by Zerren on 3/9/2015.
 */
public class MessageShader implements IMessage, IMessageHandler<MessageShader, IMessage> {

    public byte shader;

    public MessageShader() {

    }

    /**
     *
     * @param shader Byte of the shader index--set to 0 to disable
     */
    public MessageShader(byte shader) {
        this.shader = shader;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        this.shader = buf.readByte();
    }

    @Override
    public void toBytes(ByteBuf buf)
    {
        buf.writeByte(shader);
    }

    @Override
    public IMessage onMessage(MessageShader message, MessageContext ctx)
    {

        if (!ctx.side.isClient()) return null;

        EntityRenderer playerX = FMLClientHandler.instance().getClient().entityRenderer;

        switch (message.shader) {
            case 0:  playerX.deactivateShader(); break;
            case 1: ZederrianEngineering.proxy.setShader("desaturate"); break;
        }

        return null;
    }
}