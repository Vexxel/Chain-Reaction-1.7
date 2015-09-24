package com.zerren.chainreaction.block;

import com.zerren.chainreaction.client.render.block.ISBRHMechanism;
import com.zerren.chainreaction.tile.mechanism.TETeleporter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

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
        return ISBRHMechanism.mechModel;
    }

    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side) {
        return true;
    }
}
