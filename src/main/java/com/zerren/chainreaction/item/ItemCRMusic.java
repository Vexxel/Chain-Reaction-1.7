package com.zerren.chainreaction.item;

import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemRecord;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Created by Zerren on 2/12/2016.
 */
public class ItemCRMusic extends ItemRecord {
    protected String itemName;
    protected String texFolder;

    public ItemCRMusic(String name, String folder, String music) {
        super(music);
        this.setUnlocalizedName(name);
        this.itemName = name;
        this.texFolder = folder;
    }

    @Override
    public ResourceLocation getRecordResource(String name) {
        return new ResourceLocation(Reference.Sounds.RECORDS + name);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.ModInfo.CR_RESOURCE_PREFIX, itemName);
    }

    /*@Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s.%s", Reference.ModInfo.CR_RESOURCE_PREFIX, itemName, itemSubtypes[MathHelper.clamp_int(itemStack.getItemDamage(), 0, itemSubtypes.length - 1)]);
    }*/

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return this.itemIcon;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {

        this.itemIcon = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + texFolder + itemName);
    }
}
