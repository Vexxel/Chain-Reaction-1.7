package chainreaction.api.recipe;

import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zerren on 4/3/2015.
 */
public class HeatingFluid {

    private static List<HeatingFluid> heatingFluid = new ArrayList<HeatingFluid>();

    public final Fluid input;
    public final Fluid output;
    public final float heat;

    private HeatingFluid(Fluid input, Fluid output, float heat){
        this.input = input;
        this.output = output;
        this.heat = heat;
    }

    private Fluid getInput(){
        return input;
    }

    @Nullable
    public static Fluid getOutput(Fluid input){
        for(HeatingFluid fluid : heatingFluid) {
            if (input == fluid.getInput()) {
                return fluid.output;
            }
        }
        return null;
    }

    public static float getHeat(Fluid input) {
        for(HeatingFluid fluid : heatingFluid) {
            if (input == fluid.getInput()) {
                return fluid.heat;
            }
        }
        return 0F;
    }

    public static boolean validHeatingFluid(Fluid input) {
        return getOutput(input) != null;
    }

    /**
     * Adds a fluid to the heating registry and its output, as well as how many TUs each 100mb will generate (IC2 Hot->Cold coolant is 62).
     * The liquid heat plumbing can process up to 100mb/t of this liquid--no more.
     * @param input Input fluid
     * @param output Output fluid
     * @param heat TUs per 100mb input fluid (1/10 of a bucket)
     */
    public static void addHeatingFluid(Fluid input, Fluid output, float heat) {
        heatingFluid.add(new HeatingFluid(input, output, heat));
    }
}
