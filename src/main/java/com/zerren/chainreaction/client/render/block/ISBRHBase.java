package com.zerren.chainreaction.client.render.block;

import com.zerren.chainreaction.core.proxy.ClientProxy;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 4/9/2015.
 */
public class ISBRHBase implements ISimpleBlockRenderingHandler {

    protected final float s0 = 0f;
    protected final float s1 = 1f/16f;
    protected final float s2 = 2f/16f;
    protected final float s3 = 3f/16f;
    protected final float s4 = 4f/16f;
    protected final float s5 = 5f/16f;
    protected final float s6 = 6f/16f;
    protected final float s7 = 7f/16f;
    protected final float s8 = 8f/16f;
    protected final float s9 = 9f/16f;
    protected final float s10 = 10f/16f;
    protected final float s11 = 11f/16f;
    protected final float s12 = 12f/16f;
    protected final float s13 = 13f/16f;
    protected final float s14 = 14f/16f;
    protected final float s15 = 15f/16f;
    protected final float s16 = 1f;

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {

    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return false;
    }

    protected void renderInventoryCube(float sizeX, float sizeY, float sizeZ, float x, float y, float z, Block block, int meta, RenderBlocks renderer) {
        float x2, y2, z2;
        x2 = (x + sizeX) <= s16 ? x + sizeX : s16;
        y2 = (y + sizeY) <= s16 ? y + sizeY : s16;
        z2 = (z + sizeZ) <= s16 ? z + sizeZ : s16;

        renderer.setRenderBounds(x, y, z, x2, y2, z2);
        renderInvBlock(block, meta, renderer);
    }

    protected void renderWorldCube(float sizeX, float sizeY, float sizeZ, float x, float y, float z, Block block, int worldX, int worldY, int worldZ, RenderBlocks renderer) {
        float x2, y2, z2;
        x2 = (x + sizeX) <= s16 ? x + sizeX : s16;
        y2 = (y + sizeY) <= s16 ? y + sizeY : s16;
        z2 = (z + sizeZ) <= s16 ? z + sizeZ : s16;

        renderer.setRenderBounds(x, y, z, x2, y2, z2);
        renderer.renderStandardBlock(block, worldX, worldY, worldZ);
    }

    protected void renderOverrideWorldCube(float sizeX, float sizeY, float sizeZ, float x, float y, float z, Block block, int worldX, int worldY, int worldZ, RenderBlocks renderer, IIcon tex) {
        renderer.setOverrideBlockTexture(tex);
        renderWorldCube(sizeX, sizeY, sizeZ, x, y, z, block, worldX, worldY, worldZ, renderer);
        renderer.clearOverrideBlockTexture();
    }

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
    }

    @Override
    public int getRenderId() {
        return 0;
    }

    protected static void renderInvBlock(Block block, int m,  RenderBlocks renderer) {
        Tessellator var14 = Tessellator.instance;

        GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
        var14.startDrawingQuads();
        var14.setNormal(0.0F, -1.0F, 0.0F);
        renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, m));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, m));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
        var14.draw();
        GL11.glTranslatef(0.5F, 0.5F, 0.5F);
    }
}
