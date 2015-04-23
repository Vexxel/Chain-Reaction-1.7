package com.zerren.zedeng.block;

import com.zerren.zedeng.tile.reactor.TEPressurizedWaterReactor;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Zerren on 4/23/2015.
 */
public class BlockReactor extends BlockZE implements ITileEntityProvider {
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
}
