package com.zerren.chainreaction.client.render;

import org.lwjgl.opengl.GL11;

/**
 * Created by Zerren on 9/16/2015.
 */
public class CRRenderHelper {

    public static void blendOn() {
        GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public static void blendOff() {
        GL11.glPopAttrib();
    }
}
