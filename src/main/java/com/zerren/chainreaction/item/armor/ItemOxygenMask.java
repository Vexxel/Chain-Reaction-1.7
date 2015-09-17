package com.zerren.chainreaction.item.armor;

import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.item.ItemCRArmor;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Zerren on 9/1/2015.
 */
public class ItemOxygenMask extends ItemCRArmor {


    public static final ArmorMaterial material = EnumHelper.addArmorMaterial(
            "o2,", 0, new int[]{1, 0, 0, 0}, 0);

    public ItemOxygenMask(String name, String folder, ArmorMaterial material, int renderslot, int slot) {
        super(name, folder, material, renderslot, slot);
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
}
