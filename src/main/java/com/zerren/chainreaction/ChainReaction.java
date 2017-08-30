package com.zerren.chainreaction;

import com.zerren.chainreaction.core.ModBlocks;
import com.zerren.chainreaction.core.ModFluids;
import com.zerren.chainreaction.core.ModItems;
import com.zerren.chainreaction.core.ModPotions;
import com.zerren.chainreaction.core.proxy.ClientProxy;
import com.zerren.chainreaction.core.proxy.CommonProxy;
import com.zerren.chainreaction.core.registry.CRDictionary;
import com.zerren.chainreaction.core.registry.Recipes;
import com.zerren.chainreaction.core.registry.TileEntities;
import com.zerren.chainreaction.core.tick.CRTickHandler;
import com.zerren.chainreaction.core.tick.SetBonusHandler;
import com.zerren.chainreaction.handler.ConfigHandler;
import com.zerren.chainreaction.handler.GuiHandler;
import com.zerren.chainreaction.handler.network.PacketHandler;
import com.zerren.chainreaction.reference.MultiblockCost;
import com.zerren.chainreaction.reference.Reference;
import com.zerren.chainreaction.utility.CRHotkey;
import com.zerren.chainreaction.utility.ItemRetriever;
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
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;

/**
 * Created by Zerren on 2/19/2015.
 */

@Mod(modid= Reference.ModInfo.MOD_ID, name=Reference.ModInfo.MOD_NAME, version=Reference.ModInfo.VERSION, guiFactory = Reference.ModInfo.GUIFACTORY_CLASS, dependencies = "required-after:Baubles;")
public class ChainReaction {

    @Mod.Instance(Reference.ModInfo.MOD_ID)
    public static ChainReaction instance;

    @SidedProxy(modId = Reference.ModInfo.MOD_ID, clientSide = Reference.ModInfo.CLIENTPROXY_CLASS, serverSide = Reference.ModInfo.COMMONPROXY_CLASS)
    public static CommonProxy proxy;

    public static Logger log;

    public static CreativeTabs cTabCR = new CreativeTabs(Reference.ModInfo.MOD_ID) {
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

        ModFluids.init();
        ModBlocks.init();
        ModItems.init();
        ModPotions.init();

        CRDictionary.init();
        PacketHandler.init();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        TileEntities.init();

        proxy.initTESR();
        proxy.initItemRender();
        proxy.initISBRH();
        proxy.initArmorRender();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        Recipes.init();
        MultiblockCost.init();

        FMLCommonHandler.instance().bus().register(new CRTickHandler());

        MinecraftForge.EVENT_BUS.register(new CRTickHandler());
        MinecraftForge.EVENT_BUS.register(new SetBonusHandler());

        if(event.getSide() == Side.CLIENT) {
            CRHotkey.init();
            FMLCommonHandler.instance().bus().register(new ClientProxy());
        }
    }
}
