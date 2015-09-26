package com.zerren.chainreaction.item.armor;

import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.item.ItemCRArmor;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.util.EnumHelper;

/**
 * Created by Zerren on 9/1/2015.
 */
public class ItemThrustPack extends ItemCRArmor {

    public double maxEnergy;

    public static final ArmorMaterial material = EnumHelper.addArmorMaterial(
            "thrustpack", 0, new int[]{0, 0, 0, 0}, 0);

    public ItemThrustPack(String name, String folder, ArmorMaterial material, int renderslot, int slot, double maxEnergy) {
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
        return Reference.Textures.Models.THRUST_PACK.toString();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + texFolder + "thrustpack");
    }

    public void thrust(EntityPlayer player) {
        if (player.isSneaking()) {
            player.motionY += 1D;
            player.fallDistance = 0;
        }
        if (player.isSprinting()) {

        }
    }
}
