package com.zerren.zedeng.block;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.block.tile.chest.TEChest;
import com.zerren.zedeng.block.tile.chest.TEChestBrick;
import com.zerren.zedeng.block.tile.chest.TEChestThaumium;
import com.zerren.zedeng.block.tile.chest.TEChestVoid;
import com.zerren.zedeng.block.tile.vault.TEVaultBase;
import com.zerren.zedeng.block.tile.vault.TEVaultController;
import com.zerren.zedeng.core.IKey;
import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.proxy.ClientProxy;
import com.zerren.zedeng.reference.GUIs;
import com.zerren.zedeng.reference.RenderIDs;
import com.zerren.zedeng.reference.Sounds;
import com.zerren.zedeng.utility.CoreUtility;
import com.zerren.zedeng.utility.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Zerren on 2/28/2015.
 */
public class BlockZedChest extends BlockZE implements ITileEntityProvider {

    public BlockZedChest(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
        this.setBlockBounds(0.0625f, 0.0f, 0.0625f, 0.9375f, 0.875f, 0.9375f);
    }

    @Override
    public int getRenderType() {
        return RenderIDs.vaultChest;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch (meta) {
            case 0: return new TEChestBrick();
            case 1: return new TEChestThaumium();
            case 2: return new TEChestVoid();
        }

        return null;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {

        TEChest chest = CoreUtility.get(world, x, y, z, TEChest.class);
        ItemStack held = player.inventory.getCurrentItem();

        if (held != null && held.getItem() == Items.ghast_tear && !world.isRemote) {

            CoreUtility.addChat("Owned by: " + chest.getOwnerUUID(), player);
            CoreUtility.addChat("Locked: " + chest.getChestLocked(), player);
            return true;
        }

        //player must have a key in hand and must be sneaking
        if (held != null && held.getItem() instanceof IKey) {
            if (!world.isRemote) {
                //bedrock key always wins
                if (held.getItemDamage() == 0) {
                    chest.tryCode(NBTHelper.getString(player.inventory.getCurrentItem(), "code"), player, true);
                    return true;
                }

                //has key in hand and it does have a code
                if (held.stackTagCompound != null && held.stackTagCompound.hasKey("code")) {
                    //chest doesn't have a code yet
                    if (!chest.hasChestCode()) {
                        chest.setChestCode(NBTHelper.getString(player.inventory.getCurrentItem(), "code"), player);
                        chest.setOwnerUUID(player.getPersistentID());
                    }
                    //chest does have a code
                    else {
                        chest.tryCode(NBTHelper.getString(player.inventory.getCurrentItem(), "code"), player, false);
                    }
                    return true;
                }
                //has key in hand but it doesn't have a code
                else {
                    //chest doesn't have a code
                    if (!chest.hasChestCode()) {
                        CoreUtility.addColoredChat("gui.info.keyhole.null.name", EnumChatFormatting.YELLOW, player);
                        return true;
                    }
                    //lock does have a code
                    CoreUtility.addColoredChat("gui.item.key.nocode.name", EnumChatFormatting.YELLOW, player);
                    //lock's ownerName matches the key's holder
                    if (chest.getOwnerUUID().equals(player.getPersistentID())) {
                        System.out.println("Lock ownerName matches player");
                        CoreUtility.addColoredChat("gui.item.key.remember.name", EnumChatFormatting.YELLOW, player);

                        ChatComponentText comp = new ChatComponentText(EnumChatFormatting.GOLD + ("..." + chest.getChestCode() + "!"));
                        player.addChatComponentMessage(comp);
                    }
                    //lock's ownerName doesn't match the key's holder
                    else {
                        CoreUtility.addColoredChat("gui.item.key.trespass.name", EnumChatFormatting.YELLOW, player);
                    }
                }
            }

            return true;
        }

        if ((player.isSneaking() && player.getCurrentEquippedItem() != null) || world.isSideSolid(x, y + 1, z, ForgeDirection.DOWN)) {
            return true;
        }
        else {
            if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TEChest) {
                if (!chest.getChestLocked())
                    player.openGui(ZederrianEngineering.instance, GUIs.CHEST.ordinal(), world, x, y, z);
                else
                    world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, Sounds.LOCK_RATTLE, 0.7F, 1F);
            }

            return true;
        }
    }

    @Override
    public boolean onBlockEventReceived(World world, int x, int y, int z, int eventId, int eventData) {
        super.onBlockEventReceived(world, x, y, z, eventId, eventData);
        TileEntity tileentity = world.getTileEntity(x, y, z);
        return tileentity != null && tileentity.receiveClientEvent(eventId, eventData);
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        TEChest chest = CoreUtility.get(world, x, y, z, TEChest.class);
        if ((chest != null) && chest.getChestLocked())
            return -1F;
        else
            return super.getBlockHardness(world, x, y, z);
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        TEChest chest = CoreUtility.get(world, x, y, z, TEChest.class);

        if ((chest != null) && chest.getChestLocked()) {
            if (ConfigHandler.bedrockVault) {
                return 6000000.0F;
            }
            return ConfigHandler.vaultResistance;
        }
        else
            return super.getExplosionResistance(entity);
    }
}
