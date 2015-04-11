package com.zerren.zedeng.client.render.block;

import com.zerren.zedeng.block.BlockPlumbing;
import com.zerren.zedeng.block.tile.TileEntityZE;
import com.zerren.zedeng.block.tile.plumbing.TEDistroChamber;
import com.zerren.zedeng.utility.CoreUtility;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Zerren on 4/9/2015.
 */
public class ISBRHPlumbing extends ISBRHBase implements ISimpleBlockRenderingHandler {

    public static int exchangerModel = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (modelId == exchangerModel) {
            //tubes
            if (metadata == 0) {
                renderer.setRenderBounds(s1, s0, s0, s2, s16, s16);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s14, s0, s0, s15, s16, s16);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s0, s3, s3, s16, s13, s13);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s0, s1, s1, s16, s2, s2);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s0, s1, s14, s16, s2, s15);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s0, s14, s1, s16, s15, s2);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s0, s14, s14, s16, s15, s15);
                renderInvBlock(block, metadata, renderer);
            }
            //distribution chamber
            else if (metadata == 1) {
                renderer.setRenderBounds(s1, s1, s1, s15, s15, s15);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s3, s3, s0, s13, s13, s16);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s0, s3, s3, s16, s13, s13);
                renderInvBlock(block, metadata, renderer);

                renderer.setRenderBounds(s3, s0, s3, s13, s16, s13);
                renderInvBlock(block, metadata, renderer);
            }
            //pipe
            else if (metadata == 2) {

                renderer.setRenderBounds(s4, s0, s4, s12, s16, s12);
                renderInvBlock(block, metadata, renderer);
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        if (modelId == exchangerModel) {
            int meta = world.getBlockMetadata(x, y, z);
            TileEntityZE tile = CoreUtility.get(world, x, y, z, TileEntityZE.class);

            //distribution chamber
            if (meta == 1) {
                renderer.setRenderBounds(s1, s1, s1, s15, s15, s15);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setRenderBounds(s3, s3, s0, s13, s13, s16);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setRenderBounds(s0, s3, s3, s16, s13, s13);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.setRenderBounds(s3, s0, s3, s13, s16, s13);
                renderer.renderStandardBlock(block, x, y, z);

                return false;
            }

            //pipe
            else if (meta == 2) {
                //if the small 6x6x6 core should render (a standalone pipe)
                byte renderCore = 0;

                renderer.setOverrideBlockTexture(BlockPlumbing.pipeMouth);

                //x axis
                if (world.getTileEntity(x - 1, y, z) instanceof IFluidHandler) {
                    renderer.setRenderBounds(s0, s4, s4, s4, s12, s12);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderCore++;
                }
                if (world.getTileEntity(x + 1, y, z) instanceof IFluidHandler) {
                    renderer.setRenderBounds(s12, s4, s4, s16, s12, s12);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderCore++;
                }

                //y axis
                if (world.getTileEntity(x, y - 1, z) instanceof IFluidHandler) {
                    renderer.setRenderBounds(s4, s0, s4, s12, s4, s12);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderCore++;
                }
                if (world.getTileEntity(x, y + 1, z) instanceof IFluidHandler) {
                    renderer.setRenderBounds(s4, s12, s4, s12, s16, s12);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderCore++;
                }

                //z axis
                if (world.getTileEntity(x, y, z - 1) instanceof IFluidHandler) {
                    renderer.setRenderBounds(s4, s4, s0, s12, s12, s4);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderCore++;
                }
                if (world.getTileEntity(x, y, z + 1) instanceof IFluidHandler) {
                    renderer.setRenderBounds(s4, s4, s12, s12, s12, s16);
                    renderer.renderStandardBlock(block, x, y, z);
                    renderCore++;
                }

                renderer.clearOverrideBlockTexture();

                //if there aren't connectables on all 6 sides
                if (renderCore < 6) {
                    renderer.setRenderBounds(s4, s4, s4, s12, s12, s12);
                    renderer.renderStandardBlock(block, x, y, z);
                }

                return false;
            }
        }
        return false;
    }

    @Override
    public int getRenderId() {
        return exchangerModel;
    }
}
