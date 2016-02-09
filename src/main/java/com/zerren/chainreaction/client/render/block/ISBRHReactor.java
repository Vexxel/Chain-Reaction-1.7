package com.zerren.chainreaction.client.render.block;

import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.tile.plumbing.TEHeatExchanger;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.IFluidHandler;

import java.util.Arrays;

/**
 * Created by Zerren on 4/9/2015.
 */
public class ISBRHReactor extends ISBRHBase {

    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (modelId == model) {
            //PWR assembly
            if (metadata == 0) {
                renderInventoryCube(s10, s16, s10, s3, s0, s3, block, metadata, renderer);
                renderInventoryCube(s16, s10, s10, s0, s3, s3, block, metadata, renderer);
                renderInventoryCube(s10, s10, s16, s3, s3, s0, block, metadata, renderer);

                renderInventoryCube(s16, s1, s16, s0, s1, s0, block, metadata, renderer);
                renderInventoryCube(s16, s1, s16, s0, s14, s0, block, metadata, renderer);

                renderInventoryCube(s2, s16, s2, s1, s0, s1, block, metadata, renderer);
                renderInventoryCube(s2, s16, s2, s1, s0, s13, block, metadata, renderer);
                renderInventoryCube(s2, s16, s2, s13, s0, s1, block, metadata, renderer);
                renderInventoryCube(s2, s16, s2, s13, s0, s13, block, metadata, renderer);
            }
            //PWR Casing
            if (metadata == 1) {
                renderInventoryCube(s16, s16, s16, s0, s0, s0, block, metadata, renderer);
            }

        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        if (modelId == model) {
            int meta = world.getBlockMetadata(x, y, z);
            TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

            //casing
            if (meta == 0 && tile instanceof TEPressurizedWaterReactor) {
                if (!((TEPressurizedWaterReactor) tile).isMaster() && ((TEPressurizedWaterReactor) tile).hasMaster) {
                    return false;
                }

                renderWorldCube(s10, s16, s10, s3, s0, s3, block, x, y, z, renderer);
                renderWorldCube(s16, s10, s10, s0, s3, s3, block, x, y, z, renderer);
                renderWorldCube(s10, s10, s16, s3, s3, s0, block, x, y, z, renderer);

                renderWorldCube(s16, s1, s16, s0, s1, s0, block, x, y, z, renderer);
                renderWorldCube(s16, s1, s16, s0, s14, s0, block, x, y, z, renderer);

                renderWorldCube(s2, s16, s2, s1, s0, s1, block, x, y, z, renderer);
                renderWorldCube(s2, s16, s2, s1, s0, s13, block, x, y, z, renderer);
                renderWorldCube(s2, s16, s2, s13, s0, s1, block, x, y, z, renderer);
                renderWorldCube(s2, s16, s2, s13, s0, s13, block, x, y, z, renderer);
                
            }
            if (meta == 1 && tile instanceof TEMultiBlockBase) {
                if (!((TEMultiBlockBase) tile).isMaster() && ((TEMultiBlockBase) tile).hasMaster) {
                    return false;
                }
                renderWorldCube(s16, s16, s16, s0, s0, s0, block, x, y, z, renderer);
                return false;
            }
        }
        return false;
    }

    @Override
    public int getRenderId() {
        return model;
    }
}
