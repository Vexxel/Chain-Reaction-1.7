package com.zerren.chainreaction.utility;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.ConfigHandler;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;

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
            ChainReaction.log.info(message);

        for (StackTraceElement element : Thread.currentThread().getStackTrace())
            ChainReaction.log.info(element);
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
        TileEntity tile = world.getTileEntity(x, y, z);
        return (tileClass.isInstance(tile) ? (T)tile : null);
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

    public static ForgeDirection getClickedFaceDirection(float x, float y, float z) {

        if (x == 0) return ForgeDirection.WEST;
        if (x == 1) return ForgeDirection.EAST;

        if (y == 0) return ForgeDirection.DOWN;
        if (y == 1) return ForgeDirection.UP;

        if (z == 0) return ForgeDirection.NORTH;
        if (z == 1) return ForgeDirection.SOUTH;

        return ForgeDirection.UNKNOWN;
    }

    public static TileEntity getTileOnOppositeFace(World world, int x, int y, int z, ForgeDirection faceClicked) {

        switch (faceClicked) {
            case EAST: return world.getTileEntity(x + ForgeDirection.WEST.offsetX, y + ForgeDirection.WEST.offsetY, z + ForgeDirection.WEST.offsetZ);
            case WEST: return world.getTileEntity(x + ForgeDirection.EAST.offsetX, y + ForgeDirection.EAST.offsetY, z + ForgeDirection.EAST.offsetZ);
            case UP: return world.getTileEntity(x + ForgeDirection.DOWN.offsetX, y + ForgeDirection.DOWN.offsetY, z + ForgeDirection.DOWN.offsetZ);
            case DOWN: return world.getTileEntity(x + ForgeDirection.UP.offsetX, y + ForgeDirection.UP.offsetY, z + ForgeDirection.UP.offsetZ);
            case SOUTH: return world.getTileEntity(x + ForgeDirection.NORTH.offsetX, y + ForgeDirection.NORTH.offsetY, z + ForgeDirection.NORTH.offsetZ);
            case NORTH: return world.getTileEntity(x + ForgeDirection.SOUTH.offsetX, y + ForgeDirection.SOUTH.offsetY, z + ForgeDirection.SOUTH.offsetZ);
        }
        return null;
    }

    public static boolean hasDictionaryMatch(ItemStack stack, String dictionary) {
        ArrayList<ItemStack> ores = OreDictionary.getOres(dictionary);
        for (ItemStack ore : ores) {
            if (stack != null && stack.getItem() == ore.getItem() && stack.getItemDamage() == ore.getItemDamage()) return true;
        }
        return false;
    }
}
