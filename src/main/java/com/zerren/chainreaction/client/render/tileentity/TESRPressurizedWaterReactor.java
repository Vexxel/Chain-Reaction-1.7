package com.zerren.chainreaction.client.render.tileentity;

import com.zerren.chainreaction.client.render.model.ModelPressurizedWaterReactor;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.tile.reactor.TEPressurizedWaterReactor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Zerren on 4/24/2015.
 */
@SideOnly(Side.CLIENT)
public class TESRPressurizedWaterReactor extends TileEntitySpecialRenderer {

    private final ModelPressurizedWaterReactor modelReactor = new ModelPressurizedWaterReactor();

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float tick) {
        renderReactor(tile, x, y, z);
    }

    private void renderReactor(TileEntity tile, double x, double y, double z) {
        if (tile instanceof TEPressurizedWaterReactor) {

            if (!((TEPressurizedWaterReactor) tile).isMaster() && ((TEPressurizedWaterReactor) tile).hasMaster) {
                return;
            }

            TEPressurizedWaterReactor reactor = (TEPressurizedWaterReactor) tile;

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x + 0.5F, (float) y + (reactor.isMaster() ? 0.5F : 1.5F), (float) z + 0.5F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);

            if (reactor.isMaster()) {
                this.bindTexture(Reference.Textures.Models.PRESSURIZED_WATER_REACTOR);
                modelReactor.render();
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
        }
    }
}
