package com.zerren.chainreaction.tile.reactor;

import chainreaction.api.item.IRTGFuel;
import chainreaction.api.recipe.RTGFuels;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.tile.TEEnergyProviderBase;
import com.zerren.chainreaction.utility.CRMath;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.TransferUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.BiomeDictionary;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TERTG extends TEEnergyProviderBase implements IInventory {

    private static final String DECAY_TIME = "decayTime";
    private float tempMod;
    private boolean hasCheckedTemperature;
    private ItemStack[] inventory;
    private int checkTime;
    private int decayTime;

    public TERTG() {
        super(0, 12800, TransferUtility.getAllElevationDirections());
        hasCheckedTemperature = false;
        inventory = new ItemStack[1];
        checkTime = 0;
    }

    @Override
    public void updateEntity() {
        if (!worldObj.isRemote) {
            if (!hasCheckedTemperature) {
                setTemp(worldObj.getWorldChunkManager().getBiomeGenAt(xCoord, zCoord));
                hasCheckedTemperature = true;
                //ChainReaction.log.info("RTG temperature modifier @ X:" + xCoord + ", Z:" + zCoord + " = " + tempMod);
            }

            if (checkTime >= 40) {
                rfGenPerTick = getEnergyValue();
                energyStorage.setMaxExtract(rfGenPerTick * 2);
                if (ConfigHandler.devDebug) {
                    ChainReaction.log.info("RTG Power Generation @ X:" + xCoord + ", Z:" + zCoord + " = " + rfGenPerTick + "RF/t");
                    ChainReaction.log.info("RTG Max Output Rate @ X:" + xCoord + ", Z:" + zCoord + " = " + energyStorage.getMaxExtract() + "RF/t");
                }
                checkTime = 0;
            }
            checkTime++;

            energyStorage.modifyEnergyStored(Math.round(rfGenPerTick * tempMod));
            transferEnergyToConnectingSides();
            decayTime++;
            //a full minecraft day
            if (decayTime >= 24000) {
                decayTime = 0;

                decayFuelByOneDay();
            }
        }
    }

    public int getEnergyValue() {
        ItemStack stack = inventory[0];
        if (stack == null) return 0;

        if (RTGFuels.isValidRTGFuel(stack)) {
            if (stack.getItem() instanceof IRTGFuel) {
                IRTGFuel fuel = (IRTGFuel) stack.getItem();

                return (int)Math.round(fuel.getRTGFuelRemaining(stack) * RTGFuels.getRFPerTickBase(stack));
            }
            //for the IC2 rtg pebbles
            else {
                return RTGFuels.getRFPerTickBase(stack);
            }
        }
        return 0;
    }

    private void decayFuelByOneDay() {
        ItemStack stack = inventory[0];
        if (stack == null) return;

        if (stack.getItem() instanceof IRTGFuel) {
            IRTGFuel fuel = (IRTGFuel) stack.getItem();

            if (RTGFuels.getHalfLifeInDays(stack) > 0) {
                double decayed = CRMath.getFuelLevelAfterOneDayDecay(fuel.getRTGFuelRemaining(stack), RTGFuels.getHalfLifeInDays(stack));
                //ChainReaction.log.info(decayed);

                //essentially gone but it keeps it from going literally forever
                if (decayed > 0.0049D)
                    fuel.setRTGFuelRemaining(stack, decayed);
                else
                    fuel.setRTGFuelRemaining(stack, 0F);
            }
        }
    }

    //sets a modifier on power generated based on the ambient temperature of the current biome. Colder biomes get a bonus, hell biome is basically unusable
    private void setTemp(BiomeGenBase biome) {
        //gets the biometypes from the biome this block is loaded in
        BiomeDictionary.Type[] biomeTypes = BiomeDictionary.getTypesForBiome(biome);
        for (BiomeDictionary.Type type : biomeTypes) {
            if (biome.equals(BiomeGenBase.hell)) {
                this.tempMod = 0.1F;
                return;
            }
            else if (type.equals(BiomeDictionary.Type.COLD)) {
                this.tempMod = 1.3F;
                return;
            }
        }
        this.tempMod = 1.0F;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return inventory[slotIndex];
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int decrementAmount) {
        ItemStack itemStack = getStackInSlot(slotIndex);
        if (itemStack != null) {
            if (itemStack.stackSize <= decrementAmount) {
                setInventorySlotContents(slotIndex, null);
            }
            else {
                itemStack = itemStack.splitStack(decrementAmount);
                if (itemStack.stackSize == 0) {
                    setInventorySlotContents(slotIndex, null);
                }
            }
        }

        return itemStack;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slotIndex) {
        if (inventory[slotIndex] != null) {
            ItemStack itemStack = inventory[slotIndex];
            inventory[slotIndex] = null;
            return itemStack;
        }
        else {
            return null;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        inventory[slotIndex] = itemStack;

        if (itemStack != null && itemStack.stackSize > this.getInventoryStackLimit()) {
            itemStack.stackSize = this.getInventoryStackLimit();
        }

        this.markDirty();
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);

        this.inventory = readNBTItems(tag, this);
        this.decayTime = tag.getInteger(DECAY_TIME);
    }

    @Override
    public void writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);

        writeNBTItems(tag, inventory);
        tag.setInteger(DECAY_TIME, decayTime);
    }

    @Override
    public String getInventoryName() {
        return this.hasCustomName() ? this.getCustomName() : CoreUtility.translate("container.rtg.name");
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.hasCustomName();
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    /**
     * Do not make give this method the name canInteractWith because it clashes with Container
     *
     * @param entityplayer The player we are checking to see if they can use this chest
     */
    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        return false;
    }
}
