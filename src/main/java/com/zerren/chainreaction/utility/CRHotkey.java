package com.zerren.chainreaction.utility;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;

/**
 * Created by Zerren on 9/25/2015.
 */
@SideOnly(Side.CLIENT)
public final class CRHotkey {

    public static KeyBinding boost = new KeyBinding("key.boost", Keyboard.KEY_X, "key.categories.chainreaction");

    public static void init() {
        ClientRegistry.registerKeyBinding(boost);
    }
}
