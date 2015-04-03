package com.zerren.zedeng.handler.network.server.tile;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.block.tile.vault.TEVaultController;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

/**
 * Created by Zerren on 2/25/2015.
 */
public class PacketVaultCycle extends PacketTileZE<TEVaultController> implements IMessageHandler<PacketVaultCycle, IMessage> {

    int page;
    public PacketVaultCycle() {
        super();
    }

    public PacketVaultCycle(TEVaultController tile, int pg, EntityPlayer player, boolean accept) {
        super(tile, player);
        this.page = pg;
        //if this packet was tagged with false, it will shift the value down 100. this prevents the client from changing the page, and the player from opening the new gui
        if (!accept) {
            this.page -= 100;
        }
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        super.toBytes(byteBuf);
        byteBuf.writeInt(page);
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        super.fromBytes(byteBuf);
        page = byteBuf.readInt();
    }

    @Override
    public IMessage onMessage(PacketVaultCycle message, MessageContext ctx) {
        super.onMessage(message, ctx);
        if (!ctx.side.isServer()) {
            throw new IllegalStateException("received PacketVault " + message + "on client side!");
        }

        //recieves int packet 'page'. If the packet is >=0, (ie not shifted down 100 from previous), then the client will set its page to the packet int and open a new GUI
        if (message.page >= 0 && message.page != message.tile.page) {
            message.tile.setPage(message.page);
            message.player.openGui(ZederrianEngineering.instance, Reference.GUIs.VAULT.ordinal(), message.tile.getWorldObj(), message.tile.xCoord, message.tile.yCoord, message.tile.zCoord);
            message.tile.playSFXatCore(Reference.Sounds.PISTON_OUT, 0.5F, message.tile.getWorldObj().rand.nextFloat() * 0.25F + 0.6F);
        }
        //this re-shifts the packet up 100 if it is below 0
        int msgp = message.page;
        if (message.page < 0) {
            msgp += 100;
        }
        //this sets the client's selector to the packet int
        message.tile.selection = msgp;
        return null;
    }
}