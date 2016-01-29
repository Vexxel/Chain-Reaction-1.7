package com.zerren.chainreaction.block;

import com.zerren.chainreaction.client.render.block.ISBRHReactor;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Zerren on 4/23/2015.
 */
public class BlockReactor extends BlockCR implements ITileEntityProvider {
    public BlockReactor(String name, String[] subtypes, Material material, float hardness, float resistance, SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta) {
            case 0: return new TEPressurizedWaterReactor();
        }
        return null;
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
}
