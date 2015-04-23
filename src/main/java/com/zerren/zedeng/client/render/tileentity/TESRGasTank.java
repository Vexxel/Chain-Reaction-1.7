package com.zerren.zedeng.client.render.tileentity;

import com.zerren.zedeng.tile.plumbing.TEGasTank;
import com.zerren.zedeng.client.render.model.ModelGasTank;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Zerren on 4/13/2015.
 */
@SideOnly(Side.CLIENT)
public class TESRGasTank extends TileEntitySpecialRenderer {

    private final ModelGasTank modelTank = new ModelGasTank();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        renderTank(tile, x, y, z);
    }

    private void renderTank(TileEntity tile, double x, double y, double z) {
        if (tile instanceof TEGasTank) {
            TEGasTank tank = (TEGasTank) tile;
            int amount = tank.fluidAmount;

            ForgeDirection direction = null;

            if (tank.getWorldObj() != null) {
                direction = tank.getOrientation();
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            short angle = 0;

            if (direction != null) {
                if (direction == ForgeDirection.NORTH) {
                    angle = 180;
                }
                else if (direction == ForgeDirection.SOUTH) {
                    angle = 0;
                }
                else if (direction == ForgeDirection.WEST) {
                    angle = 90;
                }
                else if (direction == ForgeDirection.EAST) {
                    angle = -90;
                }
            }
            //System.out.println("" + tick);
            //Rotate to the direction placed
            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);

            this.bindTexture(Reference.Textures.Models.GAS_TANK);
            modelTank.render();

            modelTank.Needle.rotateAngleZ = 0.65F;

            if (amount > 0) {
                float filled = (float)amount / tank.tank.getCapacity();
                modelTank.Needle.rotateAngleZ = 0.65F + (filled * (float) Math.PI / 0.65F);
            }
            modelTank.renderNeedle();


            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }
}
