package com.zerren.zedeng.block.fluid;

import com.zerren.zedeng.core.DamageSourceZE;
import com.zerren.zedeng.core.ModPotions;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Zerren on 3/12/2015.
 */
public class BlockFluidUF6 extends BlockFluidZE {

    public BlockFluidUF6(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material, name, quanta, tickr, hardness, lightOpacity, false);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            PotionEffect effect1 = new PotionEffect(ModPotions.getEffectID("radSickness"), 6000, 0);
            PotionEffect effect2 = new PotionEffect(Potion.poison.getId(), 400, 0);
            effect1.getCurativeItems().clear();

            ((EntityLivingBase) entity).addPotionEffect(effect1);
            ((EntityLivingBase) entity).addPotionEffect(effect2);
        }

        entity.motionX *= 0.8D;
        entity.motionZ *= 0.8D;
    }


    @Override
    public IIcon getIcon(int side, int meta) {
        return stillIcon;
    }

    @Override
    public void registerBlockIcons(IIconRegister icon) {
        super.registerBlockIcons(icon);
        flowingIcon = null;
    }
}