package com.zerren.zedeng.block.tile.vault;

import com.zerren.zedeng.reference.Reference;
import com.zerren.zedeng.utility.CoreUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Zerren on 2/23/2015.
 */
public class TEVaultLock extends TEVaultBase {

    private String code = "";

    public TEVaultLock() {
        super();
    }

    public void setCode(String code, EntityPlayer player) {
        this.code = code;
        CoreUtility.addChat("Set lock code to: " + code, player);
        this.markDirty();

    }

    public boolean hasCode() {
        return code.length() > 0;
    }

    public String getCode() {
        return code;
    }

    public void tryCode(String keyCode, EntityPlayer player, boolean bedrock) {
        if (bedrock && !worldObj.isRemote) {
            unlockAdjacent(xCoord, yCoord, zCoord, player);
            CoreUtility.addColoredChat("gui.info.keyhole.creative.name", 'e', player);
            worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, Reference.MOD_ID.toLowerCase() + ":lock_success", 1F, 1F);
            return;
        }
        if (keyCode == null) return;
        if (getCommandingController() == null) return;

        //System.out.println("Used " + keyCode + " on lock " + code);
        if (!worldObj.isRemote) {
            if (keyCode.contains(code)) {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, Reference.MOD_ID.toLowerCase() + ":lock_success", 1F, 1F);
                unlockAdjacent(xCoord, yCoord, zCoord, player);
            }
            else {
                worldObj.playSoundEffect(xCoord + 0.5, yCoord + 0.5, zCoord + 0.5, Reference.MOD_ID.toLowerCase() + ":lock_failure", 1F, 1F);
                CoreUtility.addColoredChat("gui.info.keyhole.deny.name", 'e', player);
            }
        }
        this.markDirty();
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        tag.setString("code", code);
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        code = tag.getString("code");
    }
}
