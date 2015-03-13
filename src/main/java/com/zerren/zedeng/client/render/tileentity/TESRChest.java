package com.zerren.zedeng.client.render.tileentity;

import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.client.render.model.ModelVaultChest;
import com.zerren.zedeng.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

/**
 * Created by Zerren on 2/28/2015.
 */
@SideOnly(Side.CLIENT)
public class TESRChest extends TileEntitySpecialRenderer {

    private final ModelVaultChest modelChest = new ModelVaultChest();

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float tick) {
        renderZedChest(tileEntity, x, y, z, tick);
    }

    private void renderZedChest(TileEntity tile, double x, double y, double z, float tick) {
        if (tile instanceof TEChest) {
            TEChest chest = (TEChest) tile;
            ForgeDirection direction = null;
            boolean locked = chest.getChestLocked();

            if (chest.getWorldObj() != null) {
                direction = chest.getOrientation();
            }

            switch (chest.getState()) {
                case 0: this.bindTexture(Textures.Models.CHEST_BRICK); break;
                case 1: this.bindTexture(Textures.Models.CHEST_THAUMIUM); break;
                case 2: this.bindTexture(Textures.Models.CHEST_VOID); break;
            }

            GL11.glPushMatrix();
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glTranslatef((float) x, (float) y + 1.0F, (float) z + 1.0F);
            GL11.glScalef(1.0F, -1.0F, -1.0F);
            GL11.glTranslatef(0.5F, 0.5F, 0.5F);
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

            GL11.glRotatef(angle, 0.0F, 1.0F, 0.0F);
            GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
            float adjustedLidAngle = chest.prevLidAngle + (chest.lidAngle - chest.prevLidAngle) * tick;
            adjustedLidAngle = 1.0F - adjustedLidAngle;
            adjustedLidAngle = 1.0F - adjustedLidAngle * adjustedLidAngle * adjustedLidAngle;

            modelChest.chestLid.rotateAngleX = -(adjustedLidAngle * (float) Math.PI / 2.0F);
            modelChest.lidBolts.rotateAngleX = -(adjustedLidAngle * (float) Math.PI / 2.0F);

            if (!locked) {
                modelChest.renderUnlocked();
            }
            else {
                modelChest.renderLocked();
            }
            modelChest.renderBody();

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}