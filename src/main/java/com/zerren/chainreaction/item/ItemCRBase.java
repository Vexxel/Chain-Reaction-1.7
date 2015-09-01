package com.zerren.chainreaction.item;

import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;

import java.util.List;

/**
 * Created by Zerren on 2/20/2015.
 */
public class ItemCRBase extends Item {

    @SideOnly(Side.CLIENT)
    protected IIcon[] icons;

    protected String itemName;
    protected String[] itemSubtypes;
    protected String texFolder;

    public ItemCRBase(String name, String[] subtypes, int stacksize, String folder, CreativeTabs tab) {
        super();

        this.setHasSubtypes(true);
        this.maxStackSize = stacksize;
        this.setCreativeTab(tab);
        this.setNoRepair();
        this.setUnlocalizedName(name);

        this.itemName = name;
        this.itemSubtypes = subtypes;
        this.texFolder = folder;
    }

    public ItemCRBase(String name, String[] subtypes, String folder, CreativeTabs tab) {
        this(name, subtypes, 64, folder, tab);
    }

    public ItemCRBase(String name, String[] subtypes, CreativeTabs tab) {
        this(name, subtypes, 64, null, tab);
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("item.%s%s", Reference.ModInfo.CR_RESOURCE_PREFIX, itemName);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemStack) {
        return String.format("item.%s%s.%s", Reference.ModInfo.CR_RESOURCE_PREFIX, itemName, itemSubtypes[MathHelper.clamp_int(itemStack.getItemDamage(), 0, itemSubtypes.length - 1)]);
    }

    protected String unwrapName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return icons[MathHelper.clamp_int(meta, 0, itemSubtypes.length - 1)];
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item item, CreativeTabs creativeTab, List list) {
        for (int meta = 0; meta < itemSubtypes.length; ++meta) {
            list.add(new ItemStack(this, 1, meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[itemSubtypes.length];

        for (int i = 0; i < itemSubtypes.length; i++) {
            if (texFolder != null) {
                icons[i] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + texFolder + itemSubtypes[i]);
            }
            else {
                icons[i] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + itemSubtypes[i]);
            }
        }
    }
}
