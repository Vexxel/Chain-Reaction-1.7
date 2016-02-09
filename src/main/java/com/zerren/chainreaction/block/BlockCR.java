package com.zerren.chainreaction.block;

import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Zerren on 2/19/2015.
 */
public class BlockCR extends Block {

    @SideOnly(Side.CLIENT)
    protected IIcon[] icon;

    protected String[] subtypes;
    protected String folder;

    public BlockCR(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder) {
        super(material);
        this.setBlockName(name);
        this.subtypes = subtypes;
        this.setHardness(hardness);
        this.setResistance(resistance);
        this.setStepSound(sound);
        this.folder = folder;
    }

    public BlockCR(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        this(name, subtypes, material, hardness, resistance, sound, folder);
        this.setCreativeTab(tab);
    }

    @Override
    public Item getItemDropped(int meta, Random random, int p2) {

        switch (meta) {
            default: return Item.getItemFromBlock(this);
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public String getUnlocalizedName() {
        return String.format("tile.%s%s", Reference.ModInfo.CR_RESOURCE_PREFIX, unwrapName(super.getUnlocalizedName()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        this.icon = new IIcon[subtypes.length];

        for (int i = 0; i < subtypes.length; i++) {
            icon[i] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + subtypes[i]);
        }
    }

    protected String unwrapName(String unlocalizedName) {
        return unlocalizedName.substring(unlocalizedName.indexOf(".") + 1);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list) {
        for (int meta = 0; meta < subtypes.length; meta++) {
            list.add(new ItemStack(item, 1, meta));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData) {
        metaData = MathHelper.clamp_int(metaData, 0, subtypes.length - 1);
        return icon[metaData];
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

        if (tile != null && tile instanceof TEMultiBlockBase && (((TEMultiBlockBase)tile).hasValidMaster() || ((TEMultiBlockBase)tile).isMaster())) {
            ((TEMultiBlockBase)tile).getCommandingController().invalidateMultiblock();
        }

        dropInventory(world, x, y, z);
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack itemStack) {

        if (world.getTileEntity(x, y, z) instanceof TileEntityCRBase) {
            TileEntityCRBase tile = (TileEntityCRBase)world.getTileEntity(x, y, z);
            if (itemStack.hasDisplayName()) {
                ((TileEntityCRBase) world.getTileEntity(x, y, z)).setCustomName(itemStack.getDisplayName());
            }

            ((TileEntityCRBase) world.getTileEntity(x, y, z)).setOrientation(CoreUtility.getLookingDirection(entity, tile.canFaceUpDown()).getOpposite());
        }
    }

    protected void dropInventory(World world, int x, int y, int z)
    {
        TileEntity tileEntity = world.getTileEntity(x, y, z);

        if (!(tileEntity instanceof IInventory))
        {
            return;
        }

        IInventory inventory = (IInventory) tileEntity;

        for (int i = 0; i < inventory.getSizeInventory(); i++)
        {
            ItemStack itemStack = inventory.getStackInSlot(i);

            if (itemStack != null && itemStack.stackSize > 0)
            {
                Random rand = new Random();

                float dX = rand.nextFloat() * 0.8F + 0.1F;
                float dY = rand.nextFloat() * 0.8F + 0.1F;
                float dZ = rand.nextFloat() * 0.8F + 0.1F;

                EntityItem entityItem = new EntityItem(world, x + dX, y + dY, z + dZ, itemStack.copy());

                if (itemStack.hasTagCompound())
                {
                    entityItem.getEntityItem().setTagCompound((NBTTagCompound) itemStack.getTagCompound().copy());
                }

                float factor = 0.05F;
                entityItem.motionX = rand.nextGaussian() * factor;
                entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                entityItem.motionZ = rand.nextGaussian() * factor;
                world.spawnEntityInWorld(entityItem);
                itemStack.stackSize = 0;
            }
        }
    }
}