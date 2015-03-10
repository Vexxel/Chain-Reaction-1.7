package com.zerren.zedeng.block.fluid;

import com.zerren.zedeng.core.DamageSourceZE;
import com.zerren.zedeng.reference.Sounds;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import java.util.List;
import java.util.Random;

/**
 * Created by Zerren on 3/9/2015.
 */
public class BlockFluidSteam extends BlockFluidZE {

    private static float range = 5.0F;

    public BlockFluidSteam(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material, name, quanta, tickr, hardness, lightOpacity);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            entity.attackEntityFrom(DamageSourceZE.thermal, 4.0F);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {
        disperse(world, x, y, z, rand);
    }

    private void disperse(World world, int x, int y, int z, Random rand) {
        world.setBlockToAir(x, y, z);
        if (!world.isRemote) {
            world.playSoundEffect(x, y, z, Sounds.FIZZ, 1F, 1F);

            AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range);
            List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);

            for (EntityLivingBase entity : entities) {
                if (entity == null) return;

                entity.attackEntityFrom(DamageSourceZE.thermal, 4.0F);
            }
        }
    }
}