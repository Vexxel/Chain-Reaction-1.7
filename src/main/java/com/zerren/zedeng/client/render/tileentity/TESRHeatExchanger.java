package com.zerren.zedeng.client.render.tileentity;

import com.zerren.zedeng.block.tile.reactor.TEHeatExchanger;
import com.zerren.zedeng.client.render.model.ModelHeatExchanger;
import com.zerren.zedeng.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Zerren on 3/7/2015.
 */
@SideOnly(Side.CLIENT)
public class TESRHeatExchanger extends TileEntitySpecialRenderer {

    private final ModelHeatExchanger modelHeatExchanger = new ModelHeatExchanger();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        if (tile instanceof TEHeatExchanger) {
            TEHeatExchanger exchanger = (TEHeatExchanger) tile;
            ForgeDirection direction = null;

            if (exchanger.getWorldObj() != null) {
                direction = exchanger.getOrientation();
            }

            switch (exchanger.getState()) {
                case 0: this.bindTexture(Textures.model.HEAT_EXCHANGER); break;
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            //GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, -0.5F, 0.5F);
            short angle = 0;

            if (direction != null)
            {
                if (direction == ForgeDirection.NORTH)
                {
                    angle = 180;
                }
                else if (direction == ForgeDirection.SOUTH)
                {
                    angle = 0;
                }
                else if (direction == ForgeDirection.WEST)
                {
                    angle = 90;
                }
                else if (direction == ForgeDirection.EAST)
                {
                    angle = -90;
                }
            }
            //Rotate to the direction placed
            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);

            modelHeatExchanger.renderAll();

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            //GL11.glDisable(GL11.GL_BLEND);
        }
    }
}