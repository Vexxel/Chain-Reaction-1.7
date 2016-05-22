package com.zerren.chainreaction.client.render.tileentity;

import com.zerren.chainreaction.client.render.model.ModelBloomery;
import com.zerren.chainreaction.client.render.model.ModelPressurizedWaterReactor;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.mechanism.TEBloomery;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Zerren on 5/20/2016.
 */
@SideOnly(Side.CLIENT)
public class TESRBloomery extends TileEntitySpecialRenderer {

    private final ModelBloomery modelBloomery = new ModelBloomery();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        if (tile != null && tile instanceof TEBloomery)
            renderBloomery((TEBloomery)tile, x, y, z);
    }

    private void renderBloomery(TEBloomery tile, double x, double y, double z) {
        if (!tile.isMaster()) return;

        ForgeDirection direction = null;
        if (tile.getWorldObj() != null) {
            direction = tile.getOrientation();
        }

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

        GL11.glPushMatrix();
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float) x + 0.5F, (float) y + 1.5F, (float) z + 0.5F);
        GL11.glScalef(1.0F, -1.0F, -1.0F);
        GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);

        if (tile.isActive)
            this.bindTexture(Reference.Textures.Models.BLOOMERY_ON);
        else
            this.bindTexture(Reference.Textures.Models.BLOOMERY_OFF);

        modelBloomery.render();

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
    }
}
