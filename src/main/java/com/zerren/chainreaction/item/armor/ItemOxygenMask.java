package com.zerren.chainreaction.item.armor;

import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.item.ItemCRArmor;
import com.zerren.chainreaction.reference.Reference;
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
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

import java.util.List;

/**
 * Created by Zerren on 9/1/2015.
 */
@Optional.InterfaceList({
        @Optional.Interface(iface = "ic2.api.item.IElectricItem", modid = "IC2")
})
public class ItemOxygenMask extends ItemCRArmor implements IElectricItem {

    public double maxEnergy;

    public static final ArmorMaterial material = EnumHelper.addArmorMaterial(
            "o2", 0, new int[]{0, 0, 0, 0}, 0);

    public ItemOxygenMask(String name, String folder, ArmorMaterial material, int renderslot, int slot, double maxEnergy) {
        super(name, folder, material, renderslot, slot);
        this.maxEnergy = maxEnergy;
        this.setMaxDamage(27);
        setNoRepair();
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
    public boolean canProvideEnergy(ItemStack itemStack) {
        return false;
    }

    @Override
    public Item getChargedItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public Item getEmptyItem(ItemStack itemStack) {
        return this;
    }

    @Override
    public double getMaxCharge(ItemStack itemStack) {
        return maxEnergy;
    }

    @Override
    public int getTier(ItemStack itemStack) {
        return 1;
    }

    @Override
    public double getTransferLimit(ItemStack itemStack) {
        return 128;
    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        ItemStack full = new ItemStack(this, 1);
        ElectricItem.manager.charge(full, Integer.MAX_VALUE, Integer.MAX_VALUE, true, false);
        list.add(full);
        list.add(new ItemStack(this, 1, this.getMaxDamage()));
    }
}
