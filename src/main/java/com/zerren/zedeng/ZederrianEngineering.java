package com.zerren.zedeng;

import com.zerren.zedeng.core.ModBlocks;
import com.zerren.zedeng.core.ModItems;
import com.zerren.zedeng.core.registry.Recipes;
import com.zerren.zedeng.core.registry.TileEntities;
import com.zerren.zedeng.core.registry.ZEDictionary;
import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.handler.GuiHandler;
import com.zerren.zedeng.handler.PacketHandler;
import com.zerren.zedeng.handler.network.packet.PacketPipeline;
import com.zerren.zedeng.proxy.IProxy;
import com.zerren.zedeng.reference.Reference;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import org.apache.logging.log4j.Logger;

/**
 * Created by Zerren on 2/19/2015.
 */

@Mod(modid= Reference.MOD_ID, name=Reference.MOD_NAME, version=Reference.VERSION, guiFactory = Reference.GUIFACTORY_CLASS)
public class ZederrianEngineering {

    @Mod.Instance(Reference.MOD_ID)
    public static ZederrianEngineering instance;

    @SidedProxy(clientSide = Reference.CLIENTPROXY_CLASS, serverSide = Reference.SERVERPROXY_CLASS)

    public static IProxy proxy;
    public static final PacketPipeline packetPipeline = new PacketPipeline();

    public static Logger log;

    public static CreativeTabs cTabZE = new CreativeTabs("zedeng") {
        @Override
        @SideOnly(Side.CLIENT)
        public Item getTabIconItem() {
            return Item.getItemFromBlock(ModBlocks.glass);
        }
    };

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigHandler.init(event.getSuggestedConfigurationFile());
        FMLCommonHandler.instance().bus().register(new ConfigHandler());
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        log = event.getModLog();

        //ModIntegration.tryLoadingMods();

        ModBlocks.init();
        ModItems.init();
        ZEDictionary.init();
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        packetPipeline.initalise();
        //GameRegistry.registerFuelHandler(new PPFuelHandler());
        TileEntities.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Recipes.init();
    }
}
