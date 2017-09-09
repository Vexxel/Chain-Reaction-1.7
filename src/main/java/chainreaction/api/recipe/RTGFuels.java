package chainreaction.api.recipe;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.utility.CoreUtility;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class RTGFuels {

    private static List<RTGFuels> rtgFuels = new ArrayList<RTGFuels>();

    public final ItemStack fuelItem;
    public final int rfPerTickBase;
    public final int halfLifeInDays;


    private RTGFuels(ItemStack input, int rfPerTickBase, int halfLifeInDays){
        this.fuelItem = input;
        this.rfPerTickBase = rfPerTickBase;
        this.halfLifeInDays = halfLifeInDays;
    }

    private ItemStack getInput(){
        return fuelItem;
    }

    public static int getRFPerTickBase(ItemStack input){
        for(RTGFuels fuels : rtgFuels) {
            if (CoreUtility.softCompareItem(input, fuels.getInput())) {
                return fuels.rfPerTickBase;
            }
        }
        return 0;
    }

    public static int getHalfLifeInDays(ItemStack input) {
        for(RTGFuels fuels : rtgFuels) {
            if (CoreUtility.softCompareItem(input, fuels.getInput())) {
                return fuels.halfLifeInDays;
            }
        }
        return 0;
    }

    public static boolean isValidRTGFuel(ItemStack input) {
        return getRFPerTickBase(input) > 0;
    }

    /**
     * Adds an ItemStack to the RTF fuel registry along with how much RF/t heat it generates baseline, and how long it's half life is.
     * The liquid heat plumbing can process up to 100mb/t of this liquid--no more.
     * @param input Input ItemStack
     * @param rfPerTickBase RF/t generated when the fuel is completely fresh
     * @param halfLifeInDays The time (in days) that it takes for half of the current material to decay into other elements
     */
    public static void addRTGFuel(ItemStack input, int rfPerTickBase, int halfLifeInDays) {
        rtgFuels.add(new RTGFuels(input, rfPerTickBase, halfLifeInDays));
        ChainReaction.log.info("Added " + input.getDisplayName() + " to RTG fuels with " + rfPerTickBase + " RF/t with a half life of " + halfLifeInDays + " days.");
    }
}
