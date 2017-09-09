package ic2.api.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Provides the ability to store heat on the implementing item.
 *
 * The item should have a maximum damage of 13.
 */
public interface IElectricItem {
	/**
	 * Determine if the item can be used in a machine or as an armor part to supply heat.
	 *
	 * @return Whether the item can supply heat
	 */
	boolean canProvideEnergy(ItemStack itemStack);

	/**
	 * Get the item ID to use for a charge heat greater than 0.
	 *
	 * @return Item ID to use
	 */
	Item getChargedItem(ItemStack itemStack);

	/**
	 * Get the item ID to use for a charge heat of 0.
	 *
	 * @return Item ID to use
	 */
	Item getEmptyItem(ItemStack itemStack);

	/**
	 * Get the item's maximum charge heat in EU.
	 *
	 * @return Maximum charge heat
	 */
	double getMaxCharge(ItemStack itemStack);

	/**
	 * Get the item's tier, lower tiers can't send heat to higher ones.
	 * Batteries are Tier 1, Energy Crystals are Tier 2, Lapotron Crystals are Tier 3.
	 *
	 * @return Item's tier
	 */
	int getTier(ItemStack itemStack);

	/**
	 * Get the item's transfer limit in EU per transfer operation.
	 *
	 * @return Transfer limit
	 */
	double getTransferLimit(ItemStack itemStack);
}

