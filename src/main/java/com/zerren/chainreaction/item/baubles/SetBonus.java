package com.zerren.chainreaction.item.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.item.tool.ItemBaubleCR;
import com.zerren.chainreaction.utility.CoreUtility;
import com.zerren.chainreaction.utility.ItemRetriever;
import net.minecraft.item.ItemStack;

/**
 * Created by Zerren on 8/26/2017.
 */
public enum SetBonus {
    SKULLFIRE("skullfire", "witherAmulet", "powerRing"),
    GUARDIAN("guardian", "deflectionAmulet", "knockbackBelt");

    private final String bonusName;
    private final String piece1;
    private final String piece2;

    SetBonus(String bonusName, String p1, String p2) {
        this.bonusName = bonusName;
        this.piece1 = p1;
        this.piece2 = p2;
    }

    public String getBonusName() {
        return this.bonusName;
    }

    public String getBonusNameTranslated() {
        return CoreUtility.translate("gui.setBonus." + this.bonusName + ".name");
    }

    public ItemStack getBauble1() {
        return ItemRetriever.Items.bauble(piece1);
    }

    public ItemStack getBauble2() {
        return ItemRetriever.Items.bauble(piece2);
    }

    public int getBauble1Slot() {
        ItemStack bauble = getBauble1();
        if (bauble.getItem() instanceof ItemBaubleCR) {
            switch (ItemBaubleCR.getBauble(bauble).getType()) {
                case AMULET: return 0;
                case RING: return 1;
                case BELT: return 2;
            }
        }
        return -1;
    }

    public int getBauble2Slot() {
        ItemStack bauble = getBauble2();
        if (bauble.getItem() instanceof ItemBaubleCR) {
            switch (ItemBaubleCR.getBauble(bauble).getType()) {
                case AMULET: return 0;
                case RING: return 1;
                case BELT: return 2;
            }
        }
        return -1;
    }

    public ItemStack getOtherSetPiece(String currentItemName) {
        return currentItemName.equals(piece1) ? getBauble2() : getBauble1();
    }

    public int getOtherSetPieceSlot(String currentItemName) {
        return currentItemName.equals(piece1) ? getBauble2Slot() : getBauble1Slot();
    }

    public String getExtraValue() {
        switch (this.ordinal()) {
            case 0: return " +" + Math.round(ConfigHandler.skullfireChance * 100) + "%";
            case 1: return " +" + Math.round(ConfigHandler.guardianKnockbackResistChance * 100) + "%";
        }
        return null;
    }
}
