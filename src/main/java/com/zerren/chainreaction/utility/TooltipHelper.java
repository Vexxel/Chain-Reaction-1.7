package com.zerren.chainreaction.utility;

import com.zerren.chainreaction.item.baubles.BaubleCore;
import com.zerren.chainreaction.item.baubles.SetBonus;
import com.zerren.chainreaction.item.tool.ItemBaubleCR;
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

    public static void addFuelLevelInfo(List<String> list, double fuelLevel) {
        String s1 = EnumChatFormatting.GREEN + CoreUtility.translate("gui.item.fuel.name");
        String s2 = " " + Math.round((fuelLevel * 100)) + "%";
        list.add(s1 + s2);
    }

    public static void addRTGPowerInfo(List<String> list, int power) {
        String s1 = EnumChatFormatting.RED + CoreUtility.translate("gui.item.rtgPower.name");
        String s2 = " " + power + " RF/t";
        list.add(s1 + s2);
    }

    public static void addHalfLifeInfo(List<String> list, int halflife) {
        String s1 = EnumChatFormatting.GOLD + CoreUtility.translate("gui.item.halflife.name");
        String s2 = " " + Math.round(halflife > 1460 ? halflife / 365.24 : halflife) + (halflife > 1460 ? " Years" : " Days");
        list.add(s1 + s2);
    }

    public static void addBaubleInfo(List<String> list, String bauble, String extraValue, String cooldownValue) {
        String s1 = EnumChatFormatting.BLUE + CoreUtility.translate("gui.item.bauble." + bauble + ".name");
        if (extraValue != null) {
            s1 = s1.concat(extraValue);
        }
        list.add(s1);

        if (cooldownValue != null) {
            String s2 = EnumChatFormatting.DARK_AQUA + CoreUtility.translate("gui.item.bauble.cooldown.name").concat(cooldownValue);
            list.add(s2);
        }
    }

    public static void addSetBonusInfo(SetBonus set, EntityPlayer player, List<String> list) {
        //shows the set bonus is either green or gray if it is activated or not
        boolean hasSet = CoreUtility.isSetActivated(player, set);
        EnumChatFormatting isActive = getSetActivity(hasSet);
        EnumChatFormatting isFirstEquipped = getSetActivity(hasFirstSetItemEquipped(set, player));
        EnumChatFormatting isSecondEquipped = getSetActivity(hasSecondSetItemEquipped(set, player));

        list.add(isActive + CoreUtility.translate("gui.setInfo.name"));
        String s1 = isActive + CoreUtility.translate("gui.item.bauble.setBonus." + set.getBonusName() + ".name");
        if (set.getExtraValue() != null) {
            s1 = s1.concat(set.getExtraValue());
        }
        list.add(s1);


        if (showShiftInformation()) {
            //if set is active, set bonus name is green
            list.add(" ");
            list.add(isActive + set.getBonusNameTranslated() + ":");
            //first item name
            list.add(isFirstEquipped + "- " + set.getBauble1().getDisplayName());
            list.add(isSecondEquipped + "- " + set.getBauble2().getDisplayName());
            //flavor text
            if (hasSet) {
                list.add(" ");
                list.add(EnumChatFormatting.GOLD + "" + EnumChatFormatting.ITALIC + "'" + CoreUtility.translate("gui.setBonusFlavorText." + set.getBonusName() + ".name") + "'");
            }
        }
        else {
            list.add(EnumChatFormatting.DARK_PURPLE + CoreUtility.translate("gui.shift.name"));
        }
    }

    public static EnumChatFormatting getSetActivity(boolean active) {
        //System.out.println(active);
        return active ? EnumChatFormatting.GREEN : EnumChatFormatting.GRAY;
    }

    public static boolean hasFirstSetItemEquipped(SetBonus set, EntityPlayer player) {
        return BaubleHelper.hasCorrectBauble(player, set.getBauble1(), set.getBauble1Slot());
    }
    public static boolean hasSecondSetItemEquipped(SetBonus set, EntityPlayer player) {
        return BaubleHelper.hasCorrectBauble(player, set.getBauble2(), set.getBauble2Slot());
    }

    public static String getSecondsTranslated() {
        return CoreUtility.translate("gui.item.bauble.seconds.name");
    }
}
