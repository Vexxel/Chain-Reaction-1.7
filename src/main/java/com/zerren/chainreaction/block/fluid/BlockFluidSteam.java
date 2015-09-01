package com.zerren.chainreaction.block.fluid;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.core.DamageSourceCR;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import java.util.List;
import java.util.Random;

/**
 * Created by Zerren on 3/9/2015.
 */
public class BlockFluidSteam extends BlockFluidCR {

    private static float range = 3.0F;

    public BlockFluidSteam(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material, name, quanta, tickr, hardness, lightOpacity, true);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity) {
        if (entity instanceof EntityLivingBase) {
            entity.attackEntityFrom(DamageSourceCR.THERMAL_LOW, 4.0F);
        }
    }

    @Override
    public int getRenderType() {
        return 0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {

        super.randomDisplayTick(world, x, y, z, rand);

        double px = x + rand.nextFloat();
        double py = y + rand.nextFloat();
        double pz = z + rand.nextFloat();

        for (int i = 0; i < 10; i++) {
            double vx = (rand.nextFloat() - 0.5) * 10;
            double vy = (rand.nextFloat() - 0.5) * 10;
            double vz = (rand.nextFloat() - 0.5) * 10;

            ChainReaction.proxy.steamFX(world, px, py, pz, vx, vy, vz, 4);
        }
    }

    @Override
    public void updateTick(World world, int x, int y, int z, Random rand) {

        damage(world, x, y, z, rand);
        disperse(world, x, y, z);

        if (rand.nextInt(2) == 0)
            world.setBlockToAir(x, y, z);
        if (rand.nextInt(80) == 0)
            ChainReaction.proxy.playSound(world, x, y, z, Reference.Sounds.FIZZ, 1F, 1.3F);
    }

    private void disperse(World world, int x, int y, int z) {
        //world.setBlockToAir(x, y, z);
        if (!world.isRemote) {
            int meta = world.getBlockMetadata(x, y, z);

            if (meta < quantaPerBlock) {
                if (isReplaceable(world.getBlock(x - 1, y, z))) world.setBlock(x - 1, y, z, this, meta + 1, 3);
                if (isReplaceable(world.getBlock(x + 1, y, z))) world.setBlock(x + 1, y, z, this, meta + 1, 3);

                if (isReplaceable(world.getBlock(x, y - 1, z))) world.setBlock(x, y - 1, z, this, meta + 1, 3);
                if (isReplaceable(world.getBlock(x, y + 1, z))) world.setBlock(x, y + 1, z, this, meta + 1, 3);

                if (isReplaceable(world.getBlock(x, y, z - 1))) world.setBlock(x, y, z - 1, this, meta + 1, 3);
                if (isReplaceable(world.getBlock(x, y, z + 1))) world.setBlock(x, y, z + 1, this, meta + 1, 3);
            }
            else
                world.setBlockToAir(x, y, z);
        }





        if (world.isRemote) {

        }
    }

    private boolean isReplaceable(Block block) {
        Material b = block.getMaterial();
        return b == Material.air || b == Material.circuits || b == Material.fire || b == Material.web || b == Material.plants;
    }

    private void damage(World world, int x, int y, int z, Random rand) {
        AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(x - range, y - range, z - range, x + range, y + range, z + range);
        List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, boundingBox);

        for (EntityLivingBase entity : entities) {
            if (entity == null) return;

            entity.attackEntityFrom(DamageSourceCR.THERMAL_LOW, 2.0F);
        }
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