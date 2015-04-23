package com.zerren.zedeng.reference;

import com.zerren.zedeng.utility.ItemRetriever;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Zerren on 4/8/2015.
 */
public final class MultiblockCost {

    public static ItemStack[] LIQUID_HEAT_EXCHANGER;
    public static ItemStack[] PRESSURIZED_WATER_REACTOR;

    public static void init() {
        LIQUID_HEAT_EXCHANGER = new ItemStack[]{
                ItemRetriever.Items.material(16, "boltStainlessSteel"),
                ItemRetriever.Items.material(4, "plateStainlessSteel"),
                ItemRetriever.Items.material(4, "tubeInconel")
        };

        PRESSURIZED_WATER_REACTOR = new ItemStack[]{
                ItemRetriever.Items.material(64, "boltStainlessSteel"),
                ItemRetriever.Items.material(24, "plateStainlessSteel"),
                ItemRetriever.Items.material(8, "tubeStainlessSteel"),
                ItemRetriever.Items.material(8, "tubeInconel")
        };
    }

    /**
     * Gets the passed ItemStack array containing the materials required to complete the multiblock and checks if the player has enough
     * @param player The {@link EntityPlayer} that should have their inventory checked
     * @param materials The {@link ItemStack} array containing the required materials to check
     * @param consume If this should simulate consumption and not actually take items away from the player
     * @return If the player has the correct amount of the required items
     */
    public static boolean takeMaterials(EntityPlayer player, ItemStack[] materials, boolean consume) {

        //creative doesn't need materials
        if (player.capabilities.isCreativeMode) return true;

        byte matCount = 0;
        int[] invSlotContaining = new int[materials.length];

        //iterates through the requried materials
        for (ItemStack mat : materials) {
            int countRequired = mat.stackSize;
            //iterates through the player's inventory and adds to the matCounter if it finds a matching stack and enough items in said stack
            for (int i = 0; i < player.inventory.mainInventory.length; ++i) {
                //found a matching itemstack
                if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].isItemEqual(mat)) {
                    //and a matching or more amount of said items
                    if (player.inventory.mainInventory[i].stackSize >= countRequired) {
                        //sets the slot number containing this material
                        invSlotContaining[matCount] = i;
                        matCount++;
                        break;
                    }
                }
            }
        }
        //enough materials were found, so consume them
        if (matCount >= materials.length) {
            int i = 0;
            for (ItemStack mat : materials) {
                if (consume)
                    player.inventory.decrStackSize(invSlotContaining[i], mat.stackSize);
                i++;
            }
            return true;
        }

        return false;
    }

    public static void refundMaterials(ItemStack[] materials, World world, int x, int y, int z) {
        for (ItemStack mat : materials) {
            Random rand = new Random();

            float dX = rand.nextFloat() * 0.8F + 0.1F;
            float dY = rand.nextFloat() * 0.8F + 0.1F;
            float dZ = rand.nextFloat() * 0.8F + 0.1F;

            EntityItem eItem = new EntityItem(world, x + dX, y + dY, z + dZ, mat.copy());

            float factor = 0.05F;
            eItem.motionX = rand.nextGaussian() * factor;
            eItem.motionY = rand.nextGaussian() * factor + 0.2F;
            eItem.motionZ = rand.nextGaussian() * factor;
            world.spawnEntityInWorld(eItem);
        }
    }
}
