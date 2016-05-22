package com.zerren.chainreaction.client.render.block;

import chainreaction.api.block.CRBlocks;
import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.block.BlockPlumbing;
import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.tile.TileEntityCRBase;
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
public class ISBRHPlumbing extends ISBRHBase {

    public static int model = RenderingRegistry.getNextAvailableRenderId();

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
        if (modelId == model) {
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
                renderInventoryCube(s10, s16, s10, s3, s0, s3, block, metadata, renderer);
                renderInventoryCube(s12, s2, s12, s2, s0, s2, block, metadata, renderer);
                renderInventoryCube(s12, s2, s12, s2, s14, s2, block, metadata, renderer);
            }
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {

        if (modelId == model) {
            int meta = world.getBlockMetadata(x, y, z);
            TileEntityCRBase tile = CoreUtility.get(world, x, y, z, TileEntityCRBase.class);

            //distribution chamber
            if (meta == 1) {
                ForgeDirection dir = tile != null ? tile.getOrientation() : ForgeDirection.UNKNOWN;
                IIcon newicon = ClientProxy.tex_replacements[2];

                //core
                renderer.setRenderBounds(s1, s1, s1, s15, s15, s15);
                renderer.renderStandardBlock(block, x, y, z);
                //y
                doOverride(ForgeDirection.DOWN, dir, renderer, newicon);
                renderer.setRenderBounds(s3, s0, s3, s13, s1, s13);
                renderer.renderStandardBlock(block, x, y, z);

                doOverride(ForgeDirection.UP, dir, renderer, newicon);
                renderer.setRenderBounds(s3, s15, s3, s13, s16, s13);
                renderer.renderStandardBlock(block, x, y, z);
                //z
                doOverride(ForgeDirection.NORTH, dir, renderer, newicon);
                renderer.setRenderBounds(s3, s3, s0, s13, s13, s1);
                renderer.renderStandardBlock(block, x, y, z);

                doOverride(ForgeDirection.SOUTH, dir, renderer, newicon);
                renderer.setRenderBounds(s3, s3, s15, s13, s13, s16);
                renderer.renderStandardBlock(block, x, y, z);
                //x
                doOverride(ForgeDirection.WEST, dir, renderer, newicon);
                renderer.setRenderBounds(s0, s3, s3, s1, s13, s13);
                renderer.renderStandardBlock(block, x, y, z);

                doOverride(ForgeDirection.EAST, dir, renderer, newicon);
                renderer.setRenderBounds(s15, s3, s3, s16, s13, s13);
                renderer.renderStandardBlock(block, x, y, z);

                renderer.clearOverrideBlockTexture();
                return false;
            }

            //pipe
            else if (meta == 2) {
                renderer.setOverrideBlockTexture(ClientProxy.tex_replacements[1]);

                //core
                renderWorldCube(s10, s10, s10, s3, s3, s3, block, x, y, z, renderer);

                IIcon tex = ClientProxy.tex_replacements[0];

                //x axis
                if (world.getTileEntity(x - 1, y, z) instanceof IFluidHandler) {
                    renderWorldCube(s1, s10, s10, s2, s3, s3, block, x, y, z, renderer);
                    renderOverrideWorldCube(s2, s12, s12, s0, s2, s2, block, x, y, z, renderer, tex);
                }

                if (world.getTileEntity(x + 1, y, z) instanceof IFluidHandler) {
                    renderWorldCube(s1, s10, s10, s13, s3, s3, block, x, y, z, renderer);
                    renderOverrideWorldCube(s2, s12, s12, s14, s2, s2, block, x, y, z, renderer, tex);
                }

                //y axis
                if (world.getTileEntity(x, y - 1, z) instanceof IFluidHandler) {
                    renderWorldCube(s10, s1, s10, s3, s2, s3, block, x, y, z, renderer);
                    renderOverrideWorldCube(s12, s2, s12, s2, s0, s2, block, x, y, z, renderer, tex);
                }
                if (world.getTileEntity(x, y + 1, z) instanceof IFluidHandler) {
                    renderWorldCube(s10, s1, s10, s3, s13, s3, block, x, y, z, renderer);
                    renderOverrideWorldCube(s12, s2, s12, s2, s14, s2, block, x, y, z, renderer, tex);
                }

                //z axis
                if (world.getTileEntity(x, y, z - 1) instanceof IFluidHandler) {
                    renderWorldCube(s10, s10, s1, s3, s3, s2, block, x, y, z, renderer);
                    renderOverrideWorldCube(s12, s12, s2, s2, s2, s0, block, x, y, z, renderer, tex);
                }
                if (world.getTileEntity(x, y, z + 1) instanceof IFluidHandler) {
                    renderWorldCube(s10, s10, s1, s3, s3, s13, block, x, y, z, renderer);
                    renderOverrideWorldCube(s12, s12, s2, s2, s2, s14, block, x, y, z, renderer, tex);
                }
                renderer.clearOverrideBlockTexture();

                return false;
            }
        }
        return false;
    }

    private void doOverride(ForgeDirection dirCheck, ForgeDirection tileDir, RenderBlocks renderer, IIcon icon) {
        if (dirCheck == tileDir) {
            renderer.setOverrideBlockTexture(icon);
        }
        else if (renderer.hasOverrideBlockTexture()) renderer.clearOverrideBlockTexture();
    }

    @Override
    public int getRenderId() {
        return model;
    }
}
