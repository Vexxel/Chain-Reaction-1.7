package com.zerren.chainreaction.block;

import buildcraft.api.tools.IToolWrench;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.plumbing.TEDistroChamber;
import com.zerren.chainreaction.tile.plumbing.TEGasTank;
import com.zerren.chainreaction.tile.plumbing.TEHeatExchanger;
import com.zerren.chainreaction.tile.plumbing.TEPressurePipe;
import com.zerren.chainreaction.client.render.block.ISBRHPlumbing;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Zerren on 3/6/2015.
 */
public class BlockPlumbing extends BlockCR implements ITileEntityProvider {

    @SideOnly(Side.CLIENT)
    private final IIcon[] tubes = new IIcon[2];

    /**
     * pipeMouth 0, distroInput 1
     */
    @SideOnly(Side.CLIENT)
    public static final IIcon[] overrides = new IIcon[2];

    public BlockPlumbing(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        if (player.isSneaking()) return false;

        TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

        if (tile == null) return false;
        if (tile instanceof TEHeatExchanger) return activateExchanger(world, x, y, z, player, (TEHeatExchanger)tile);
        if (tile instanceof TEDistroChamber) return activateDistroChamber(world, x, y, z, player, (TEDistroChamber) tile);
        if (tile instanceof TEGasTank) return activateGasTank(world, x, y, z, player, (TEGasTank) tile);

        return false;
    }

    private boolean activateGasTank(World world, int x, int y, int z, EntityPlayer player, TEGasTank tile) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null) {
            if (held.getItem() == Items.ghast_tear){
                if (!world.isRemote)
                    CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                return true;
            }
            else if (held.getItem() == Items.arrow) {
                if (tile.tank.getFluid() != null)
                    CoreUtility.addChat("Tank: " + tile.tank.getFluid().getLocalizedName() + " " + tile.tank.getFluidAmount(), player);
            }
            else if (held.getItem() instanceof IToolWrench && ((IToolWrench) held.getItem()).canWrench(player, x, y, z)) {
                tile.setOrientation(CoreUtility.getLookingDirection(player, true));
                world.markBlockForUpdate(x, y, z);

                ((IToolWrench) held.getItem()).wrenchUsed(player, x, y, z);
                return true;
            }
        }
        return false;
    }

    private boolean activateExchanger(World world, int x, int y, int z, EntityPlayer player, TEHeatExchanger tile) {
        ItemStack held = player.inventory.getCurrentItem();
        if (tile != null  && held != null && held.getItem() instanceof IToolWrench) {

            if (((IToolWrench) held.getItem()).canWrench(player, x, y, z) && !tile.hasValidMaster()) {
                tile.initiateController(UUID.randomUUID(), player);
                tile.setOwnerUUID(player.getPersistentID());
                return true;
            }
        }

        if (tile != null && held != null && !world.isRemote) {
            if (held.getItem() == Items.ghast_tear){
                CoreUtility.addChat("Controlled by: " + tile.getMasterUUID(), player);
                CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                CoreUtility.addChat("Is Master: " + tile.isMaster(), player);
                CoreUtility.addChat("Slave Location: " + tile.getSlaveLocation(), player);
                return true;
            }
            if (held.getItem() == Items.arrow) {
                if (tile.coolantInletTank.getFluid() != null)
                    CoreUtility.addChat("Inlet tank: " + tile.coolantInletTank.getFluid().getLocalizedName() + " " + tile.coolantInletTank.getFluidAmount(), player);
                if (tile.waterTank.getFluid() != null)
                    CoreUtility.addChat("Water tank: " + tile.waterTank.getFluid().getLocalizedName() + " " + tile.waterTank.getFluidAmount(), player);
                if (tile.steamTank.getFluid() != null)
                    CoreUtility.addChat("Steam tank: " + tile.steamTank.getFluid().getLocalizedName() + " " + tile.steamTank.getFluidAmount(), player);
                if (tile.coolantOutputTank.getFluid() != null)
                    CoreUtility.addChat("Outlet tank: " + tile.coolantOutputTank.getFluid().getLocalizedName() + " " + tile.coolantOutputTank.getFluidAmount(), player);

                CoreUtility.addChat("Thermal Units: " + tile.getThermalUnits(), player);
            }
        }
        return false;
    }

    private boolean activateDistroChamber(World world, int x, int y, int z, EntityPlayer player, TEDistroChamber tile) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null) {
            if (held.getItem() == Items.ghast_tear){
                if (!world.isRemote)
                    CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                return true;
            }
            else if (held.getItem() == Items.arrow) {
                if (tile.tank.getFluid() != null)
                    CoreUtility.addChat("Tank: " + tile.tank.getFluid().getLocalizedName() + " " + tile.tank.getFluidAmount(), player);
            }
            else if (held.getItem() instanceof IToolWrench && ((IToolWrench) held.getItem()).canWrench(player, x, y, z)) {
                tile.setOrientation(CoreUtility.getLookingDirection(player, true));
                world.markBlockForUpdate(x, y, z);

                ((IToolWrench) held.getItem()).wrenchUsed(player, x, y, z);
                return true;
            }
        }
        return false;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister iconRegister) {
        super.registerBlockIcons(iconRegister);
        overrides[0] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "pipe_mouth");
        overrides[1] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "distributionChamber_input");

        tubes[0] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "tubes_front");
        tubes[1] = iconRegister.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + "tubes_side");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int metaData) {
        //LHE
        if (metaData == 0) {
            switch (side) {
                case 2:case 4: return tubes[0];
                default: return tubes[1];
            }
        }
        metaData = MathHelper.clamp_int(metaData, 0, subtypes.length - 1);
        return icon[metaData];
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TEHeatExchanger exchanger = CoreUtility.get(world, x, y, z, TEHeatExchanger.class);

        if (exchanger != null && (exchanger.hasValidMaster() || exchanger.isMaster())) {
            exchanger.invalidateMultiblock();
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile != null && tile instanceof TEHeatExchanger && entity instanceof EntityPlayer) {
            //((TEHeatExchanger) tile).initiateController(UUID.randomUUID(), (EntityPlayer)entity);
            ((TEHeatExchanger) tile).setOwnerUUID(entity.getPersistentID());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch(meta) {
            case 0:
                return new TEHeatExchanger();
            case 1:
                return new TEDistroChamber();
            case 2:
                return new TEPressurePipe();
            case 3:
                return new TEGasTank();
            default:
                return null;
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
        return ISBRHPlumbing.exchangerModel;
    }
}
