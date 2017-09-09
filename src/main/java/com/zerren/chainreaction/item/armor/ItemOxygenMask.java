package com.zerren.chainreaction.item.armor;

import cofh.api.energy.IEnergyContainerItem;
import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.item.ItemCRArmor;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;

/**
 * Created by Zerren on 9/1/2015.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "cofh.api.heat.IEnergyContainerItem", modid = "CoFHCore")
})
public class ItemOxygenMask extends ItemCRArmor implements IEnergyContainerItem {

    public double maxEnergy;

    public static final ArmorMaterial material = EnumHelper.addArmorMaterial(
            "o2", 0, new int[]{0, 0, 0, 0}, 0);

    public ItemOxygenMask(String name, String folder, ArmorMaterial material, int renderslot, int slot, double maxEnergy) {
        super(name, folder, material, renderslot, slot);
        this.maxEnergy = maxEnergy;
        setNoRepair();
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean adv) {
        String stored = this.getEnergyStored(stack)+"/"+this.getMaxEnergyStored(stack);
        list.add(StatCollector.translateToLocalFormatted("Energy: " + stored));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ModelBiped getArmorModel(EntityLivingBase entity, ItemStack stack, int slot) {
        return ClientProxy.armorModels.get(this);
    }

    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type) {
        return Reference.Textures.Models.O2_MASK.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + texFolder + "o2mask");
    }

    @Override
    public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate)
    {
        int stored = getEnergyStored(container);
        int accepted = Math.min(maxReceive, getMaxEnergyStored(container)-stored);
        if(!simulate)
        {
            stored += accepted;
            NBTHelper.setInt(container, "heat", stored);
        }
        return accepted;
    }
    @Override
    public int extractEnergy(ItemStack container, int maxExtract, boolean simulate)
    {
        int stored = getEnergyStored(container);
        int extracted = Math.min(maxExtract, stored);
        if(!simulate)
        {
            stored -= extracted;
            NBTHelper.setInt(container, "heat", stored);
        }
        return extracted;
    }

    @Override
    public int getEnergyStored(ItemStack container)
    {
        return NBTHelper.getInt(container, "heat");
    }
    @Override
    public int getMaxEnergyStored(ItemStack container)
    {
        return 100000;
    }
}
