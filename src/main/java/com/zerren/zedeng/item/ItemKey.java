package com.zerren.zedeng.item;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.core.IKey;
import com.zerren.zedeng.reference.Reference;
import com.zerren.zedeng.utility.CoreUtility;
import com.zerren.zedeng.utility.NBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by Zerren on 2/20/2015.
 */
public class ItemKey extends ItemZE implements IKey {

    public ItemKey(String name, String[] subtypes, int stacksize, String folder, CreativeTabs tab) {
        super(name, subtypes, stacksize, folder, tab);
        //this.itemSubtypes = subtypes;
        //this.texFolder = folder;
    }

    @Override
    public ItemStack onItemRightClick (ItemStack stack, World world, EntityPlayer player) {
        if (stack.getItemDamage() == 0) {
            return stack;
        }
        if(stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        }
        if(!stack.stackTagCompound.hasKey("code")) {
            player.openGui(ZederrianEngineering.instance, 0, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
            return stack;
        }
        return stack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation (ItemStack stack, EntityPlayer player, List list, boolean par4) {
        if (stack.getItemDamage() == 0) {
            list.add(CoreUtility.translate("gui.item.key.bedrock.name"));
            return;
        }
        if (!stack.hasTagCompound()){
            return;
        }
        else if (stack.stackTagCompound.hasKey("code")){
            list.add(NBTHelper.getString(stack, "code"));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister) {
        icons = new IIcon[itemSubtypes.length];

        for (int i = 0; i < itemSubtypes.length; i++) {
            if (itemSubtypes[i].equals("bedrock")) {
                icons[i] = iconRegister.registerIcon(Reference.Textures.RESOURCE_PREFIX + texFolder + itemSubtypes[i]);
            }
            else
            for (int y = 0; y < 4; y++) {
                icons[i] = iconRegister.registerIcon(Reference.Textures.RESOURCE_PREFIX + texFolder + itemSubtypes[i] + "_" + y);
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack is, int pass) {
        return is.getItem() == this && is.getItemDamage() == 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int meta) {
        return icons[MathHelper.clamp_int(meta, 0, itemSubtypes.length - 1)];
    }

    @Override
    public boolean hasCode(ItemStack stack) {
        return NBTHelper.hasTag(stack, "code");
    }
}
