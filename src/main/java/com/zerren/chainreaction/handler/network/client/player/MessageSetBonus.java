package com.zerren.chainreaction.handler.network.client.player;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.PlayerSetBonus;
import com.zerren.chainreaction.handler.network.AbstractMessage;
import com.zerren.chainreaction.item.baubles.SetBonus;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * Created by Zerren on 8/25/2017.
 */
public class MessageSetBonus extends AbstractMessage.AbstractClientMessage<MessageSetBonus> {

    private NBTTagCompound data;

    public MessageSetBonus() {

    }

    public MessageSetBonus(EntityPlayer player) {
        data = new NBTTagCompound();
        PlayerSetBonus.get(player).saveNBTData(data);
    }

    @Override
    protected void read(PacketBuffer buffer) throws IOException {
        data = buffer.readNBTTagCompoundFromBuffer();
    }

    @Override
    protected void write(PacketBuffer buffer) throws IOException {
        buffer.writeNBTTagCompoundToBuffer(data);
    }

    @Override
    public void process(EntityPlayer player, Side side) {
        //ChainReaction.log.info("Synchronizing extended properties data on CLIENT");
        PlayerSetBonus.get(player).loadNBTData(data);
    }
}