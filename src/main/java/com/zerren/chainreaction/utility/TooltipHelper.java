package com.zerren.chainreaction.utility;

import baubles.api.BaubleType;
import com.zerren.chainreaction.item.baubles.SetBonus;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Created by Zerren on 4/2/2015.
 */
public final class TooltipHelper {

    /**
     * Adds tooltip information describing the required block for the multiblock to form on wrenching
     * @param list the list to add to--for use with item tooltips (addInformation)
     * @param reqMats the ItemStack array containing the required block
     */
    public static void addMaterialCostInfo(List<String> list, ItemStack[] reqMats) {
        String materials = CoreUtility.translate("gui.multiblock.requirement.name");

        if (showShiftInformation()) {
            list.add(EnumChatFormatting.AQUA + materials);

            for (ItemStack i : reqMats) {
                list.add("- " + EnumChatFormatting.GREEN + i.stackSize + "x " + EnumChatFormatting.GRAY + i.getDisplayName());
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
     * @param location the block that should be wrenched to complete the multiblock: check the en_US.lang file for examples
     */
    public static void addSizeInfo(List<String> list, String size, String location) {
        String sizeName = CoreUtility.translate("gui.multiblock.size.name");
        list.add(EnumChatFormatting.YELLOW + sizeName + " " + size);

        list.add(CoreUtility.translate(location));
    }

    public static void addTemperatureInfoKelvin(List<String> list, float temp) {
        String tempTranslate = CoreUtility.translate("gui.item.temperature.name");
        list.add(EnumChatFormatting.GOLD + tempTranslate + " " + Math.round(temp) + " K");
    }

    public static void addTemperatureInfoCelcius(List<String> list, float temp) {
        String tempTranslate = CoreUtility.translate("gui.item.temperature.name");
        list.add(EnumChatFormatting.GOLD + tempTranslate + " " + Math.round(CRMath.kelvinToCelsius(temp)) + " C");
    }

    public static void addTemperatureInfoFahrenheit(List<String> list, float temp) {
        String tempTranslate = CoreUtility.translate("gui.item.temperature.name");
        list.add(EnumChatFormatting.GOLD + tempTranslate + " " + Math.round(CRMath.kelvinToFahrenheit(temp)) + " F");
    }

    public static void addRadiationInfo(List<String> list, float radiation) {
        String rad = CoreUtility.translate("gui.item.radioactivity.name");
        String safety;
        if (CRMath.isWithin(radiation, 1F, 10.0F)) {
            safety = EnumChatFormatting.YELLOW + CoreUtility.translate("gui.item.radioactivity.moderate.name");
        }
        else if (CRMath.isWithin(radiation, 10.0F, 33.0F)) {
            safety = EnumChatFormatting.GOLD + CoreUtility.translate("gui.item.radioactivity.dangerous.name");
        }
        else if (CRMath.isWithin(radiation, 33.0F, 100F)) {
            safety = EnumChatFormatting.RED + CoreUtility.translate("gui.item.radioactivity.fatal.name");
        }
        else safety = EnumChatFormatting.GREEN + CoreUtility.translate("gui.item.radioactivity.minimal.name");

        list.add(EnumChatFormatting.AQUA + rad + " " + safety);
    }

    public static void addFuelLevelInfo(List<String> list, float fuelLevel) {
        String s1 = EnumChatFormatting.GREEN + CoreUtility.translate("gui.item.fuel.name");
        String s2 = " " + Math.round((fuelLevel * 100)) + "%";
        list.add(s1 + s2);
    }

    public static void addBaubleInfo(List<String> list, String bauble) {
        String s1 = EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.bauble." + bauble + ".name");
        list.add(s1);
    }

    public static void addSetBonusInfo(ItemStack stack, SetBonus set, EntityPlayer player, List<String> list, String bauble) {
        //shows the set bonus is either green or gray if it is activated or not
        EnumChatFormatting isActive = getSetActivity(BaubleHelper.hasSetBonusEquipped(player, set));
        list.add(isActive + CoreUtility.translate("gui.setInfo.name"));
        list.add(isActive + CoreUtility.translate("gui.item.bauble.setBonus." + set + ".name"));

        if (showShiftInformation()) {

        }
        else {
            list.add(EnumChatFormatting.DARK_PURPLE + CoreUtility.translate("gui.shift.name"));
        }
    }

    private static EnumChatFormatting getSetActivity(boolean active) {
        //System.out.println(active);
        return active ? EnumChatFormatting.GREEN : EnumChatFormatting.GRAY;
    }
}
