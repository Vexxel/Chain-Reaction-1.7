package com.zerren.zedeng.block.fluid;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.client.fx.EntityRadiationFX;
import com.zerren.zedeng.core.DamageSourceZE;
import com.zerren.zedeng.core.ModPotions;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

/**
 * Created by Zerren on 3/8/2015.
 */
public class BlockFluidCoolantHot extends BlockFluidZE {
    public BlockFluidCoolantHot(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material, name, quanta, tickr, hardness, lightOpacity, true);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            PotionEffect effect = new PotionEffect(ModPotions.getEffectID("radSickness"), 6000, 2);
            effect.getCurativeItems().clear();

            ((EntityLivingBase) entity).addPotionEffect(effect);
            entity.attackEntityFrom(DamageSourceZE.thermal, 4.0F);
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {

        super.randomDisplayTick(world, x, y, z, rand);

        double px = x + rand.nextFloat();
        double py = y + rand.nextFloat();
        double pz = z + rand.nextFloat();

        double vx = (rand.nextFloat() - 0.5) * 3;
        double vy = (rand.nextFloat() - 0.5) * 3;
        double vz = (rand.nextFloat() - 0.5) * 3;

        ZederrianEngineering.proxy.radiationFX(world, px, py, pz, vx, vy, vz, 5);
    }
}
