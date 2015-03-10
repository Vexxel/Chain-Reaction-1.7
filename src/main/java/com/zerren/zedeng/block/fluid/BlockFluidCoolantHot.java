package com.zerren.zedeng.block.fluid;

import com.zerren.zedeng.core.DamageSourceZE;
import com.zerren.zedeng.core.ModPotions;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Zerren on 3/8/2015.
 */
public class BlockFluidCoolantHot extends BlockFluidZE {
    public BlockFluidCoolantHot(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material, name, quanta, tickr, hardness, lightOpacity);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            PotionEffect effect = new PotionEffect(ModPotions.radSickness.getId(), 6000, 2);
            effect.getCurativeItems().clear();

            ((EntityLivingBase) entity).addPotionEffect(effect);
            entity.attackEntityFrom(DamageSourceZE.thermal, 4.0F);
        }
    }
}
