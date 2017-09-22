package com.zerren.chainreaction.block;

import buildcraft.api.tools.IToolWrench;
import chainreaction.api.item.IScanner;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.client.render.block.ISBRHMechanism;
import com.zerren.chainreaction.handler.GuiHandler;
import com.zerren.chainreaction.reference.GUIs;
import com.zerren.chainreaction.reference.Names;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.mechanism.*;
import com.zerren.chainreaction.tile.reactor.TERTG;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.NBTHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;
import java.util.UUID;

/**
 * Created by Zerren on 9/22/2015.
 */
public class BlockMechanism extends BlockCR implements ITileEntityProvider {

    public BlockMechanism(String name, String[] subtypes, Material material, float hardness, float resistance, SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch(meta) {
            case 0:
                return new TETeleporter();
            case 1:
                return new TEBloomery();
            case 2:
                return new TERTG();
            case 3:
                return new TEStirlingEngine();
            case 4:
                return new TEElectricHeater();
            case 5:
                return new TEElectrolyzer();
            case 6:
                return new TELiquifier();
            default:
                return null;
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float sideX, float sideY, float sideZ) {
        super.onBlockActivated(world, x, y, z, player, meta, sideX, sideY, sideZ);
        if (super.onBlockActivated(world, x, y, z, player, meta, sideX, sideY, sideZ)) return true;

        TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

        if (tile == null) return false;
        if (player.isSneaking()) return false;

        if (tile instanceof TEBloomery) return activateBloomery(world, x, y, z, player, (TEBloomery) tile, sideX, sideY, sideZ);
        if (tile instanceof TERTG) return activateRTG(world, x, y, z, player, (TERTG) tile, sideX, sideY, sideZ);
        if (tile instanceof TEElectrolyzer) return activateElectrolyzer(world, x, y, z, player, (TEElectrolyzer) tile, sideX, sideY, sideZ);
        if (tile instanceof TELiquifier) return activateLiquifier(world, x, y, z, player, (TELiquifier) tile, sideX, sideY, sideZ);

        return false;
    }

    private boolean activateRTG(World world, int x, int y, int z, EntityPlayer player, TERTG tile, float sideX, float sideY, float sideZ) {

        if (tile == null) return false;

        if (!world.isRemote) {
            player.openGui(ChainReaction.instance, GUIs.RTG.ordinal(), world, x, y, z);
            return true;
        }
        return true;
    }

    private boolean activateElectrolyzer(World world, int x, int y, int z, EntityPlayer player, TEElectrolyzer tile, float sideX, float sideY, float sideZ) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null && !world.isRemote) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);

                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        return true;
                    }
                    case 1: {
                        //CoreUtility.addChat("Thermal Units: " + tile.getHeatStored(ForgeDirection.UNKNOWN), player);
                        return true;
                    }
                    case 2: {
                        if (tile.inputTank.getFluid() != null)
                            CoreUtility.addChat("Input Fluid Tank: " + tile.inputTank.getFluid().getLocalizedName() + " " + tile.inputTank.getFluidAmount(), player);
                        if (tile.outputTank1.getFluid() != null)
                            CoreUtility.addChat("Output Fluid Tank 1: " + tile.outputTank1.getFluid().getLocalizedName() + " " + tile.outputTank1.getFluidAmount(), player);
                        if (tile.outputTank2.getFluid() != null)
                            CoreUtility.addChat("Output Fluid Tank 2: " + tile.outputTank2.getFluid().getLocalizedName() + " " + tile.outputTank2.getFluidAmount(), player);
                        return true;
                    }
                    default:
                        break;
                }
            }
        }

        if (tile != null && !world.isRemote && !player.isSneaking()) {
            GuiHandler.openGui(player, GUIs.ELECTROLYZER, world, tile);
            return true;
        }
        return true;
    }

    private boolean activateLiquifier(World world, int x, int y, int z, EntityPlayer player, TELiquifier tile, float sideX, float sideY, float sideZ) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile != null && held != null && !world.isRemote) {
            if (held.getItem() instanceof IScanner && ((IScanner) held.getItem()).canScan(player, x, y, z)) {
                byte mode = NBTHelper.getByte(held, Names.NBT.SCANNER_MODE);

                switch (mode) {
                    case 0: {
                        CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
                        return true;
                    }
                    case 1: {
                        //CoreUtility.addChat("Thermal Units: " + tile.getHeatStored(ForgeDirection.UNKNOWN), player);
                        return true;
                    }
                    case 2: {
                        if (tile.inputTank.getFluid() != null)
                            CoreUtility.addChat("Input Fluid Tank: " + tile.inputTank.getFluid().getLocalizedName() + " " + tile.inputTank.getFluidAmount(), player);
                        if (tile.outputTank1.getFluid() != null)
                            CoreUtility.addChat("Output Fluid Tank 1: " + tile.outputTank1.getFluid().getLocalizedName() + " " + tile.outputTank1.getFluidAmount(), player);
                        return true;
                    }
                    default:
                        break;
                }
            }
        }

        if (tile != null && !world.isRemote && !player.isSneaking()) {
            GuiHandler.openGui(player, GUIs.LIQUIFIER, world, tile);
            return true;
        }
        return true;
    }

    private boolean activateBloomery(World world, int x, int y, int z, EntityPlayer player, TEBloomery tile, float sideX, float sideY, float sideZ) {
        ItemStack held = player.inventory.getCurrentItem();

        if (tile == null) return false;

        if (world.isRemote && tile.isFormed()) return true;

        if (held != null) {
            if (held.getItem() instanceof IToolWrench && ((IToolWrench) held.getItem()).canWrench(player, x, y, z)) {
                TileEntity coretile = CoreUtility.getTileOnOppositeFace(world, x, y, z, CoreUtility.getClickedFaceDirection(sideX, sideY, sideZ));

                if (coretile != null && coretile instanceof TEBloomery) {
                    if (!((TEBloomery) coretile).hasValidMaster()) {
                        ((TEBloomery) coretile).setOrientation(CoreUtility.getClickedFaceDirection(sideX, sideY, sideZ));
                        ((TEBloomery) coretile).initiateController(UUID.randomUUID(), player);
                        return true;
                    }
                }
            }
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
                }
            }
        }
        if (tile.hasValidMaster() && !world.isRemote) {
            int[] mPos = tile.getMasterPos();
            player.openGui(ChainReaction.instance, GUIs.BLOOMERY.ordinal(), world, mPos[0], mPos[1], mPos[2]);
            return true;
        }
        return false;
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
        return ISBRHMechanism.model;
    }

    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        TileEntityCRBase tile = (TileEntityCRBase)world.getTileEntity(x, y, z);
        if (tile != null && tile instanceof TEBloomery && ((TEBloomery) tile).isBurning()) {
            for (int i = 0; i < 3; i++) {
                float f = (x - 0.2F) + 1.5F*rand.nextFloat();
                float f1 = (float)y + 4;
                float f2 = (z - 0.2F) + 1.5F*rand.nextFloat();
                world.spawnParticle("largesmoke", (double)f, (double)f1, (double)f2, 0.0D, 0.0D, 0.0D);
                world.spawnParticle("flame", (double)f, (double)f1 + 0.1, (double)f2, 0.0D, 0.0D, 0.0D);
            }
        }
    }
}
