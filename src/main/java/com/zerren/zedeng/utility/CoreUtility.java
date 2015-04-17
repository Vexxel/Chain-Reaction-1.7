package com.zerren.zedeng.utility;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.handler.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 2/21/2015.
 */
public final class CoreUtility {

    public static String translate(String key) {
        String result = StatCollector.translateToLocal(key);
        int comment = result.indexOf('#');
        return (comment > 0) ? result.substring(0, comment).trim() : result;
    }

    public static int to32BitColor(int a, int r, int g, int b) {
        a = a << 24;
        r = r << 16;
        g = g << 8;

        return a | r | g | b;
    }

    public static void printCurrentStackTrace(String message) {
        if (message != null)
            ZederrianEngineering.log.info(message);

        for (StackTraceElement element : Thread.currentThread().getStackTrace())
            ZederrianEngineering.log.info(element);
    }

    public static MinecraftServer server() {
        return MinecraftServer.getServer();
    }

    //ONLY shows messages when the matching dev config is true
    public static boolean debug(String message) {
        if (ConfigHandler.devDebug){
            System.out.println(message);
            return true;
        }
        return false;
    }

    public static <T> T get(IBlockAccess world, int x, int y, int z, Class<T> tileClass) {
        TileEntity t = world.getTileEntity(x, y, z);
        return (tileClass.isInstance(t) ? (T)t : null);
    }

    public static TileEntity getTileEntity(World world, int x, int y, int z) {
        if (world.blockExists(x, y, z)) {
            return world.getChunkFromBlockCoords(x, z).getTileEntityUnsafe(x & 15, y, z & 15);
        }
        return null;
    }

    public static void addColoredChat(String message, EnumChatFormatting format, EntityPlayer player) {
        if (player == null) return;

        String translated = format + translate(message);
        ChatComponentText comp = new ChatComponentText(translated);
        player.addChatComponentMessage(comp);
    }
    public static void addChat(String message, EntityPlayer player) {
        if (player == null) return;

        String translated = translate(message);
        ChatComponentText comp = new ChatComponentText(translated);
        player.addChatComponentMessage(comp);
    }

    public static ForgeDirection getLookingDirection(EntityLivingBase entity, boolean shouldY) {
        int yawFacing = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;
        int pitchFacing = MathHelper.floor_double(entity.rotationPitch * 4.0F / 360.0F + 0.5D) & 3;

        ForgeDirection direction = ForgeDirection.UNKNOWN;
        switch (yawFacing) {
            case 0: direction = ForgeDirection.SOUTH; break;
            case 1: direction = ForgeDirection.WEST; break;
            case 2: direction = ForgeDirection.NORTH; break;
            case 3: direction = ForgeDirection.EAST; break;
        }
        if (shouldY)
            switch (pitchFacing) {
                case 1: direction = ForgeDirection.DOWN; break;
                case 3: direction = ForgeDirection.UP; break;
            }

        return direction;
    }
}
