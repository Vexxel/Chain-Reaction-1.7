package com.zerren.zedeng.block;

import com.zerren.zedeng.ZederrianEngineering;
import com.zerren.zedeng.block.tile.vault.TEVaultBase;
import com.zerren.zedeng.block.tile.vault.TEVaultController;
import com.zerren.zedeng.block.tile.vault.TEVaultLock;
import com.zerren.zedeng.core.IKey;
import com.zerren.zedeng.handler.ConfigHandler;
import com.zerren.zedeng.reference.GUIs;
import com.zerren.zedeng.utility.CoreUtility;
import com.zerren.zedeng.utility.NBTHelper;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import java.util.UUID;

/**
 * Created by Zerren on 2/20/2015.
 */
public class BlockVault extends BlockZE implements ITileEntityProvider {

    public BlockVault(String name, String[] subtypes, Material material, float hardness, float resistance, Block.SoundType sound, String folder, CreativeTabs tab) {
        super(name, subtypes, material, hardness, resistance, sound, folder, tab);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
        TEVaultBase vault = CoreUtility.get(world, x, y, z, TEVaultBase.class);
        ItemStack held = player.inventory.getCurrentItem();

        //ghast tear debug
        if (held != null && held.getItem() == Items.ghast_tear && !world.isRemote) {
            if (vault instanceof TEVaultController) {
                TEVaultController vc = CoreUtility.get(world, x, y, z, TEVaultController.class);
                CoreUtility.addChat("Page: " + vc.page, player);
                CoreUtility.addChat("Owner UUID: " + vc.getOwner(), player);
                CoreUtility.addChat("Controller UUID: " + vc.getControllerUUID().toString(), player);
                CoreUtility.addChat("Breakable: " + vault.isBreakable(), player);
                return true;
            }
            CoreUtility.addChat("Owned by: " + vault.getOwner(), player);
            CoreUtility.addChat("Controlled by: " + vault.getControllerID(), player);
            CoreUtility.addChat("Breakable: " + vault.isBreakable(), player);
            return true;
        }

        //controller
        if (world.getBlockMetadata(x, y, z) == 1 && player.isSneaking()) {
            if (vault != null){
                //vault is a controller tile
                if (vault instanceof TEVaultController && !world.isRemote) {
                    //vault isn't active (has no commanding blocks)
                    if (!((TEVaultController) vault).isActive()) {
                        ((TEVaultController) vault).initiateController(UUID.randomUUID(), player);
                        return true;
                    }
                }
            }
            return true;
        }

        //door (open)
        if (world.getBlockMetadata(x, y, z) == 4) {
            if (vault != null && vault.hasMaster()) {
                if (player.isSneaking()) {
                    //Manually close an open door
                    world.setBlockMetadataWithNotify(x, y, z, 3, 2);
                    return true;
                }

                //gets an array of this vault's master position
                if (!world.isRemote) {
                    int[] mPos = vault.getMasterPos();
                    player.openGui(ZederrianEngineering.instance, GUIs.VAULT.ordinal(), world, mPos[0], mPos[1], mPos[2]);
                }
                return true;
            }
            return true;
        }

        if (held != null) {
            if (held.getItem() instanceof IKey) {
                if (vault instanceof TEVaultLock && !world.isRemote) {
                    TEVaultLock lock = CoreUtility.get(world, x, y, z, TEVaultLock.class);

                    //bedrock key always wins
                    if (held.getItemDamage() == 0) {
                        lock.tryCode(NBTHelper.getString(player.inventory.getCurrentItem(), "code"), player, true);
                        return true;
                    }
                    //has key in hand and it does have a code
                    if (held.stackTagCompound != null && held.stackTagCompound.hasKey("code")) {
                        //if the lock doesn't have a code
                        if (!lock.hasCode()) {
                            //lock has no owner because it isn't a part of a multiblock
                            if (!lock.hasOwner()) return false;
                            //if the lock's owner equals the key holder
                            if (lock.getOwner().equals(player.getUniqueID().toString())) {
                                lock.setCode(NBTHelper.getString(player.inventory.getCurrentItem(), "code"), player);
                            }
                            //lock's owner isn't the key holder
                            else {
                                CoreUtility.addColoredChat("gui.info.keyhole.trespass.name", EnumChatFormatting.YELLOW, player);
                            }
                        }
                        //try the key's code on the lock
                        else {
                            lock.tryCode(NBTHelper.getString(player.inventory.getCurrentItem(), "code"), player, false);
                            return true;
                        }
                    }
                    //has key in hand but it doesn't have a code
                    else {
                        //lock doesn't have a code
                        if (!lock.hasCode()) {
                            CoreUtility.addColoredChat("gui.info.keyhole.null.name", EnumChatFormatting.YELLOW, player);
                            return true;
                        }
                        //lock does have a code
                        CoreUtility.addColoredChat("gui.item.key.nocode.name", EnumChatFormatting.YELLOW, player);
                        //lock's owner matches the key's holder
                        if (lock.getOwner().equals(player.getUniqueID().toString())) {
                            System.out.println("Lock owner matches player");
                            CoreUtility.addColoredChat("gui.item.key.remember.name", EnumChatFormatting.YELLOW, player);

                            ChatComponentText comp = new ChatComponentText("§6..." + lock.getCode() + "!");
                            player.addChatComponentMessage(comp);
                        }
                        //lock's owner doesn't match the key's holder
                        else {
                            CoreUtility.addColoredChat("gui.item.key.trespass.name", EnumChatFormatting.YELLOW, player);
                        }
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public void breakBlock(World world, int x, int y, int z, Block block, int meta) {
        if (meta == 1) {
            TEVaultController controller = (TEVaultController)world.getTileEntity(x, y, z);

            if (controller != null) {
                controller.breakMultiblock();
            }
        }
        super.breakBlock(world, x, y, z, block, meta);
    }

    @Override
    public int damageDropped(int meta) {
        if (meta == 4) return 3;

        return meta;
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        super.onBlockPlacedBy(world, x, y, z, entity, stack);

        TileEntity tile = world.getTileEntity(x, y, z);

        if (tile instanceof TEVaultController && entity instanceof EntityPlayer) {
            ((TEVaultController) tile).initiateController(UUID.randomUUID(), (EntityPlayer)entity);
            ((TEVaultController) tile).setOwner(entity.getUniqueID().toString());
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        switch(meta) {
            case 0:case 3:case 4:case 5:
                return new TEVaultBase();
            case 1:
                return new TEVaultController();
            case 2:
                return new TEVaultLock();
            default:
                return null;
        }
    }

    @Override
    public float getBlockHardness(World world, int x, int y, int z) {
        TEVaultBase vault = CoreUtility.get(world, x, y, z, TEVaultBase.class);
        if ((vault != null) && !vault.isBreakable())
            return -1F;
        else
            return super.getBlockHardness(world, x, y, z);
    }

    @Override
    public float getExplosionResistance(Entity entity, World world, int x, int y, int z, double explosionX, double explosionY, double explosionZ) {
        TEVaultBase vault = CoreUtility.get(world, x, y, z, TEVaultBase.class);

        if ((vault != null) && !vault.isBreakable()) {
            if (ConfigHandler.bedrockVault) {
                return 6000000.0F;
            }
            return ConfigHandler.vaultResistance;
        }
        else
            return super.getExplosionResistance(entity);
    }
}
