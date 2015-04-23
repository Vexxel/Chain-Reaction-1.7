package com.zerren.zedeng;

import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.core.ModFluids;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.core.ModPotions;
import com.zerren.zedeng.core.registry.Recipes;
import com.zerren.zedeng.core.registry.TileEntities;
import com.zerren.zedeng.core.registry.ZEDictionary;
import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.handler.GuiHandler;
import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.core.proxy.CommonProxy;
import com.zerren.zedeng.reference.MultiblockCost;
import com.zerren.zedeng.reference.Reference;
import com.zerren.zedeng.utility.ItemRetriever;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;

/**
 * Created by Zerren on 2/19/2015.
 */

@Mod(modid= Reference.ModInfo.MOD_ID, name=Reference.ModInfo.MOD_NAME, version=Reference.ModInfo.VERSION, guiFactory = Reference.ModInfo.GUIFACTORY_CLASS)
public class ZederrianEngineering {

    @Mod.Instance(Reference.ModInfo.MOD_ID)
    public static ZederrianEngineering instance;

    @SidedProxy(modId = Reference.ModInfo.MOD_ID, clientSide = Reference.ModInfo.CLIENTPROXY_CLASS, serverSide = Reference.ModInfo.COMMONPROXY_CLASS)
    public static CommonProxy proxy;

    public static Logger log;

    public static CreativeTabs cTabZE = new CreativeTabs(Reference.ModInfo.MOD_ID) {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() { return null; }

        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack getIconItemStack() {
            return ItemRetriever.Blocks.plumbing(1, "liquidHeatExchanger");
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        log = event.getModLog();

        //ModIntegration.tryLoadingMods();

        ModFluids.init();
        ModBlocks.init();
        ModItems.init();
        ModPotions.init();

        ZEDictionary.init();
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        //GameRegistry.registerFuelHandler(new ZEDFuelHandler());
        TileEntities.init();

        proxy.initTESR();
        proxy.initItemRender();
        proxy.initISBRH();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Recipes.init();
        MultiblockCost.init();
    }
}
