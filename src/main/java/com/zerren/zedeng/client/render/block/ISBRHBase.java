package com.zerren.zedeng.client.render.block;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 4/9/2015.
 */
public class ISBRHBase {

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

    public boolean shouldRender3DInInventory(int modelId) {
        return true;
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
