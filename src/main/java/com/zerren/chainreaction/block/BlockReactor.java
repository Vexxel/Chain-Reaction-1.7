package com.zerren.chainreaction.block;

import buildcraft.api.tools.IToolWrench;
import chainreaction.api.item.IScanner;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.client.render.block.ISBRHReactor;
import com.zerren.chainreaction.handler.GuiHandler;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Zerren on 4/23/2015.
 */
public class BlockReactor extends BlockCR implements ITileEntityProvider {
    @SideOnly(Side.CLIENT)
    private IIcon[] assembly;

    public BlockReactor(String name, String[] subtypes, Material material, float hardness, float resistance, SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta) {
            //PWR assembly
            case 0: return new TEPressurizedWaterReactor();
            //PWR casing
            case 1: return new TEMultiBlockBase();
        }
        return null;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float sideX, float sideY, float sideZ) {
        TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

        if (tile == null) return false;
        if (tile instanceof TEPressurizedWaterReactor) return activatePWR(world, x, y, z, player, (TEPressurizedWaterReactor) tile, sideX, sideY, sideZ);
        if (tile instanceof TEMultiBlockBase) return activateHull(world, x, y, z, player, (TEMultiBlockBase) tile);

        return false;
    }

    private boolean activateHull(World world, int x, int y, int z, EntityPlayer player, TEMultiBlockBase tile) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null) {

            if (world.isRemote && tile.isFormed()) return true;

            if (held != null && held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z) && !world.isRemote) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);
                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Controlled by: " + tile.getMasterUUID(), player);
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        CoreUtility.addChat("Is Master: " + tile.isMaster(), player);
                        CoreUtility.addChat("Has Master: " + tile.hasValidMaster(), player);
                        CoreUtility.addChat("Slave Location: " + tile.getMultiblockPartNumber(), player);
                        return true;
                    }
                }
            }
            if (tile.hasValidMaster()) {
                int[] mPos = tile.getMasterPos();
                player.openGui(ChainReaction.instance, Reference.GUIs.PWR.ordinal(), world, mPos[0], mPos[1], mPos[2]);
                return true;
            }
        }

        return false;
    }

    private boolean activatePWR(World world, int x, int y, int z, EntityPlayer player, TEPressurizedWaterReactor tile, float sideX, float sideY, float sideZ) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile == null) return false;

        if (world.isRemote && tile.isFormed()) return true;

        if (held != null && held.getItem() instanceof IToolWrench) {
            TileEntity coretile = CoreUtility.getTileOnOppositeFace(world, x, y, z, CoreUtility.getClickedFaceDirection(sideX, sideY, sideZ));

            if (coretile != null && coretile instanceof TEPressurizedWaterReactor && ((IToolWrench) held.getItem()).canWrench(player, x, y, z)) {
                if (!((TEPressurizedWaterReactor) coretile).hasValidMaster()) {
                    ((TEPressurizedWaterReactor) coretile).initiateController(UUID.randomUUID(), player);
                    return true;
                }
            }

        }

        if (held != null) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);
                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Controlled by: " + tile.getMasterUUID(), player);
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        CoreUtility.addChat("Is Master: " + tile.isMaster(), player);
                        CoreUtility.addChat("Has Master: " + tile.hasValidMaster(), player);
                        CoreUtility.addChat("Slave Location: " + tile.getMultiblockPartNumber(), player);
                        return true;
                    }
                    case 1: {
                        CoreUtility.addChat("Thermal Units: " + tile.getThermalUnits(), player);
                        return true;
                    }
                    case 2: {
                        if (tile.coolantInletTank.getFluid() != null)
                            CoreUtility.addChat("Inlet tank: " + tile.coolantInletTank.getFluid().getLocalizedName() + " " + tile.coolantInletTank.getFluidAmount(), player);
                        if (tile.coolantOutputTank.getFluid() != null)
                            CoreUtility.addChat("Outlet tank: " + tile.coolantOutputTank.getFluid().getLocalizedName() + " " + tile.coolantOutputTank.getFluidAmount(), player);
                        return true;
                    }
                }
            }
        }
        if (tile.hasValidMaster()) {
            int[] mPos = tile.getMasterPos();
            GuiHandler.openGui(player, Reference.GUIs.PWR, world, mPos);
            return true;
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof TEPressurizedWaterReactor && entity instanceof EntityPlayer) {
            ((TEPressurizedWaterReactor) tile).setOwnerUUID(entity.getPersistentID());
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return ISBRHReactor.model;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);

        assembly = new IIcon[2];

        assembly[0] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "pwr_a_top");
        assembly[1] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "pwr_a_sides");
    }

    /*@Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TEMultiBlockBase tile = CoreUtility.get(world, x, y, z, TEMultiBlockBase.class);

        if (tile != null && (tile.hasValidMaster() || tile.isMaster())) {
            tile.getCommandingController().invalidateMultiblock();
        }

        super.breakBlock(world, x, y, z, block, meta);
    }*/

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData) {
        //PWR
        if (metaData == 0) {
            switch (side) {
                case 0:case 1: return assembly[0];
                default: return assembly[1];
            }
        }
        metaData = MathHelper.clamp_int(metaData, 0, subtypes.length - 1);
        return icon[metaData];
    }
}
