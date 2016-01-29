package com.zerren.chainreaction.item;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 9/1/2015.
 */
public class ItemCRArmor extends ItemArmor {


    protected String itemName;
    protected String texFolder;

    public ItemCRArmor(String name, String folder, ArmorMaterial material, int renderslot, int slot) {
        super(material, renderslot, slot);

        setCreativeTab(ChainReaction.cTabCR);
        this.setUnlocalizedName(name);
        this.itemName = name;
        this.texFolder = folder;
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.ModInfo.CR_RESOURCE_PREFIX, itemName);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return getUnlocalizedName();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) { }

    public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
    {
        return null;
    }
}
