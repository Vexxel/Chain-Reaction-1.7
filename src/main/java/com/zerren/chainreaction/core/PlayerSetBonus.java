package com.zerren.chainreaction.core;

import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.reference.Names;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.util.Arrays;

/**
 * Created by Zerren on 8/26/2017.
 */
public class PlayerSetBonus implements IExtendedEntityProperties {

    public final static String EXT_PROP_NAME = "CRBaublesPlayer";
    public final static SetBonus[] BONUSES = SetBonus.values();

    private final EntityPlayer player;
    private boolean[] sets;

    public PlayerSetBonus(EntityPlayer player) {
        this.player = player;
        this.sets = new boolean[BONUSES.length];

        for (int i = 0; i < sets.length; i++) {
            this.sets[i] = false;
        }
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

        for (int i = 0; i < BONUSES.length; i++) {
            properties.setBoolean(BONUSES[i].getBonusName(), sets[i]);
        }
        compound.setTag(EXT_PROP_NAME, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);

        for (int i = 0; i < BONUSES.length; i++) {
            sets[i] = properties.getBoolean(BONUSES[i].getBonusName());

            if (ConfigHandler.devDebug) System.out.println(BONUSES[i].getBonusName() + " = " + sets[i]);
        }
    }

    public void toggleSetStatus(SetBonus set, boolean equipped) {
        if (ConfigHandler.devDebug) System.out.println(set + "=" + equipped);

        this.sets[set.ordinal()] = equipped;
    }

    public boolean getSetStatus(SetBonus set) {
        return this.sets[set.ordinal()];
    }

    @Override
    public void init(Entity entity, World world) {

    }
}
