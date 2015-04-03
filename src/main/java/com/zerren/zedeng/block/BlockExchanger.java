package com.zerren.zedeng.block;

import buildcraft.api.tools.IToolWrench;
import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.utility.CoreUtility;
import com.zerren.zedeng.utility.ItemRetriever;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Zerren on 3/6/2015.
 */
public class BlockExchanger extends BlockZE implements ITileEntityProvider {

    public static ItemStack[] exchangerRequirements = {
            //16 bolts
            ItemRetriever.Items.material(16, "boltStainlessSteel"),
            //4 Tubes
            ItemRetriever.Items.material(4, "tubeStainlessSteel"),
            //4 Plates
            ItemRetriever.Items.material(4, "plateStainlessSteel"),
    };

    public BlockExchanger(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }


    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TEHeatExchanger tile = CoreUtility.get(world, x, y, z, TEHeatExchanger.class);

        ItemStack held = player.inventory.getCurrentItem();
        if (tile != null && held != null && held.getItem() instanceof IToolWrench) {

            if (((IToolWrench) held.getItem()).canWrench(player, x, y, z) && !tile.hasValidMaster()) {
                tile.initiateController(UUID.randomUUID(), player);
                tile.setOwnerUUID(player.getPersistentID());
                return true;
            }
        }

        if (tile != null && held != null && held.getItem() == Items.ghast_tear && !world.isRemote) {
            CoreUtility.addChat("Controlled by: " + tile.getMasterUUID(), player);
            CoreUtility.addChat("Direction: " + tile.getOrientation(), player);
            CoreUtility.addChat("Is Master: " + tile.isMaster(), player);
            return true;
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        TEHeatExchanger exchanger = (TEHeatExchanger)world.getTileEntity(x, y, z);

        if (exchanger != null && (exchanger.hasValidMaster() || exchanger.isMaster())) {
            exchanger.invalidateMultiblock();
        }

        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TEHeatExchanger && entity instanceof EntityPlayer) {
            ((TEHeatExchanger) tile).initiateController(UUID.randomUUID(), (EntityPlayer)entity);
            ((TEHeatExchanger) tile).setOwnerUUID(entity.getPersistentID());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch(meta) {
            case 0:
                return new TEHeatExchanger();
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
        return -1;
    }
}
