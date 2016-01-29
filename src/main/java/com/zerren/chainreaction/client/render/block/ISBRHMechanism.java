package com.zerren.chainreaction.client.render.block;

import com.zerren.chainreaction.tile.TEMultiBlockBase;
import com.zerren.chainreaction.tile.TileEntityCRBase;
import com.zerren.chainreaction.utility.CoreUtility;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;

/**
 * Created by Zerren on 9/22/2015.
 */
public class ISBRHMechanism extends ISBRHBase {

    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int meta, int modelId, RenderBlocks renderer) {
        //teleporter
        if (modelId == model) {
            if (meta == 0) {
                renderer.setRenderBounds(s0, s0, s0, s16, s16, s16);
                renderInvBlock(block, meta, renderer);
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        if (modelId == model) {
            int meta = world.getBlockMetadata(x, y, z);
            TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

            if (meta == 0) {
                if (tile != null && (((TEMultiBlockBase)tile).hasValidMaster() || ((TEMultiBlockBase)tile).isMaster())) return false;

                renderer.setRenderBounds(s0, s0, s0, s16, s16, s16);
                renderer.renderStandardBlock(block, x, y, z);

            }
        }

        return false;
    }


}
