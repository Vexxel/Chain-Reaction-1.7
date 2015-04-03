package com.zerren.zedeng.utility;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Created by Zerren on 4/2/2015.
 */
@SideOnly(Side.CLIENT)
public final class TooltipHelper {

    public static void addMaterialCostInfo(List list, ItemStack[] reqMats) {
        String materials = CoreUtility.translate("gui.multiblock.requirement.name");

        if (showShiftInformation()) {
            list.add(EnumChatFormatting.AQUA + materials);

            for (ItemStack i : reqMats) {
                list.add("- " + i.stackSize + "x " + i.getDisplayName());
            }
        }
        else {
            String shift = CoreUtility.translate("gui.shift.name");
            list.add(EnumChatFormatting.AQUA + materials + " " + EnumChatFormatting.DARK_PURPLE + shift);
        }
    }

    public static boolean showShiftInformation() {
        return (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT));
    }

    /**
     * Adds tooltip information describing how large the multiblock needs to be and where to wrench it
     * @param list the list to add to--for use with item tooltips (addInformation)
     * @param size the size of this multiblock: format is 1x1x1
     */
    public static void addSizeInfo(List list, String size) {
        String sizeName = CoreUtility.translate("gui.multiblock.size.name");
        list.add(EnumChatFormatting.YELLOW + sizeName + " " + size);

        String center = CoreUtility.translate("gui.multiblock.wrench.center.name");
        list.add(center);
    }
}
