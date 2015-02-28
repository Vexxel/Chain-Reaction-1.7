package com.zerren.zedeng.handler.network.packet;

import cpw.mods.fml.common.network.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Zerren on 2/21/2015.
 */
public class PacketKeyConfirm extends AbstractPacket {

    private String code;

    public PacketKeyConfirm(){}

    public PacketKeyConfirm(String code) {
        this.code = code;
    }

    @Override
    public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        ByteBufUtils.writeUTF8String(buffer, code);
    }

    @Override
    public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
        code = ByteBufUtils.readUTF8String(buffer);
    }

    @Override
    public void handleClientSide(EntityPlayer player) {
        player.inventory.getCurrentItem().stackTagCompound.setString("code", code);
    }

    @Override
    public void handleServerSide(EntityPlayer player) {
        player.inventory.getCurrentItem().stackTagCompound.setString("code", code);
    }

}