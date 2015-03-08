package com.zerren.zedeng.block;

import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
import com.zerren.zedeng.reference.RenderIDs;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Zerren on 3/6/2015.
 */
public class BlockExchanger extends BlockZE implements ITileEntityProvider {

    public BlockExchanger(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
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
        return RenderIDs.exchanger;
    }
}
