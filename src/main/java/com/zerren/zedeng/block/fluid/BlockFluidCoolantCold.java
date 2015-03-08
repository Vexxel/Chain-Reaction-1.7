package com.zerren.zedeng.block.fluid;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Zerren on 3/7/2015.
 */
public class BlockFluidCoolantCold extends BlockFluidZE {
    public BlockFluidCoolantCold(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material, name, quanta, tickr, hardness, lightOpacity);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {

    }
}
