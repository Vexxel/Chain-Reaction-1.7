package com.zerren.chainreaction.core;

import com.zerren.chainreaction.reference.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

/**
 * Created by Zerren on 8/26/2017.
 */
public class PlayerSetBonus implements IExtendedEntityProperties {

    public final static String EXT_PROP_NAME = "CRBaublesPlayer";
    private final EntityPlayer player;
    private boolean skullfire;

    public PlayerSetBonus(EntityPlayer player) {
        this.player = player;
        this.skullfire = false;
    }

    public static PlayerSetBonus get(EntityPlayer player) {
        return (PlayerSetBonus) player.getExtendedProperties(EXT_PROP_NAME);
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(PlayerSetBonus.EXT_PROP_NAME, new PlayerSetBonus(player));
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();

        properties.setBoolean(Names.NBT.SET_SKULLFIRE, this.skullfire);
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
        this.skullfire = properties.getBoolean(Names.NBT.SET_SKULLFIRE);

        System.out.println("[CR] Skullfire set: " + skullfire);
    }

    public void setSkullfire(boolean equipped) {
        this.skullfire = equipped;
    }

    public boolean getSkullfire() {
        return skullfire;
    }

    @Override
    public void init(Entity entity, World world) {

    }
}
