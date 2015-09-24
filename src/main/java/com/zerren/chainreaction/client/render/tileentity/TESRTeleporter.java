package com.zerren.chainreaction.client.render.tileentity;

import com.zerren.chainreaction.client.render.model.ModelTeleporter;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.mechanism.TETeleporter;
import com.zerren.chainreaction.tile.plumbing.TEHeatExchanger;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Zerren on 9/22/2015.
 */
public class TESRTeleporter extends TileEntitySpecialRenderer {

    private final ModelTeleporter modelTeleporter = new ModelTeleporter();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        renderTeleporter(tile, x, y, z);
    }

    private void renderTeleporter(TileEntity tile, double x, double y, double z) {
        if (tile instanceof TETeleporter) {
            //TEPressurizedWaterReactor reactor = (TEPressurizedWaterReactor) tile;

            if (!((TETeleporter) tile).isMaster() && ((TETeleporter) tile).hasMaster) {
                return;
            }

            TETeleporter teleporter = (TETeleporter) tile;
            ForgeDirection direction = null;

            if (teleporter.getWorldObj() != null) {
                direction = teleporter.getOrientation();
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

            this.bindTexture(Reference.Textures.Models.TELEPORTER);
            modelTeleporter.render();

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }
}
