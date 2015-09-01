package com.zerren.chainreaction.block.fluid;

import com.zerren.chainreaction.ChainReaction;
import com.zerren.chainreaction.reference.Reference;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityDropParticleFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

import java.util.Random;

/**
 * Created by Zerren on 3/7/2015.
 */
public class BlockFluidCR extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon, flowingIcon;

    private String folder, name;
    private boolean hasFlowingIcon;
    protected Fluid fluid;

    /**
     *
     * @param fluid Fluid to register
     * @param material Material of the liquid, (use MaterialLiquid(MapColor.*))
     * @param name Name of fluid
     * @param quanta How far the liquid will spread
     * @param tickr Tickrate
     * @param hardness Hardness
     * @param lightOpacity Light decays by how much per block travelled through liquid
     * @param hasFlowingIcon If this fluid has a flowing icon (mostly used for gases). If false, still icon will be used
     *
     */
    public BlockFluidCR(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity, boolean hasFlowingIcon) {
        super(fluid, material);
        this.setCreativeTab(ChainReaction.cTabZE);
        this.setRenderPass(1);
        this.folder = Reference.Textures.Folders.FLUID_FOLDER;
        this.name = name;
        this.setBlockName(Reference.ModInfo.MOD_ID.toLowerCase() + ".fluid." + name);
        this.fluid = fluid;
        this.hasFlowingIcon = hasFlowingIcon;

        this.setQuantaPerBlock(quanta);
        this.setTickRate(tickr);
        this.setHardness(hardness);
        this.setLightOpacity(lightOpacity);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {

        super.randomDisplayTick(world, x, y, z, rand);

        double px = x + rand.nextFloat();
        double py = y - 1.05D;
        double pz = z + rand.nextFloat();

        if (density < 0) {
            py = y + 2.10D;
        }
        if (rand.nextInt(20) == 0 && world.isSideSolid(x, y + densityDir, z, densityDir == -1 ? ForgeDirection.UP : ForgeDirection.DOWN)
                && !world.getBlock(x, y + 2 * densityDir, z).getMaterial().blocksMovement()) {
            EntityFX fx = new EntityDropParticleFX(world, px, py, pz, this.getMaterial());
            FMLClientHandler.instance().getClient().effectRenderer.addEffect(fx);
        }
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        return (side == 0 || side == 1)? stillIcon : flowingIcon;
    }

    @Override
    public boolean canCreatureSpawn(EnumCreatureType type, IBlockAccess world, int x, int y, int z) {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister icon) {

        stillIcon = icon.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + name + "_still");
        if (hasFlowingIcon)
            flowingIcon = icon.registerIcon(Reference.ModInfo.CR_RESOURCE_PREFIX + folder + name + "_flow");
        else
            flowingIcon = stillIcon;

        fluid.setIcons(stillIcon, flowingIcon);
    }
}
