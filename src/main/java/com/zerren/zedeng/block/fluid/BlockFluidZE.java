package com.zerren.zedeng.block.fluid;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.reference.Reference;
import com.zerren.zedeng.reference.Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.Fluid;

/**
 * Created by Zerren on 3/7/2015.
 */
public class BlockFluidZE extends BlockFluidClassic {

    @SideOnly(Side.CLIENT)
    protected IIcon stillIcon, flowingIcon;

    private String folder, name;
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
     *
     */
    public BlockFluidZE(Fluid fluid, Material material, String name, int quanta, int tickr, float hardness, int lightOpacity) {
        super(fluid, material);
        this.setCreativeTab(ZederrianEngineering.cTabZE);
        this.setRenderPass(1);
        this.folder = Textures.folders.FLUID_FOLDER;
        this.setBlockName(Reference.MOD_ID.toLowerCase() + ".fluid." + name);
        this.name = name;
        this.fluid = fluid;

        this.setQuantaPerBlock(quanta);
        this.setTickRate(tickr);
        this.setHardness(hardness);
        this.setLightOpacity(lightOpacity);
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
        stillIcon = icon.registerIcon(Textures.RESOURCE_PREFIX + folder + name + "_still");
        flowingIcon = icon.registerIcon(Textures.RESOURCE_PREFIX + folder + name + "_flow");

        fluid.setIcons(stillIcon, flowingIcon);
    }
}
