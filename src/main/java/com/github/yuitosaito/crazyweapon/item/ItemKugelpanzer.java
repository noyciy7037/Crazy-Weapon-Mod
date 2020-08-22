package com.github.yuitosaito.crazyweapon.item;

import com.github.yuitosaito.crazyweapon.CrazyWeaponMod;
import com.github.yuitosaito.crazyweapon.entity.EntityKugelpanzer;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemKugelpanzer extends Item {

    public ItemKugelpanzer() {
        super();
        setCreativeTab(CrazyWeaponMod.tabCWM);
        setUnlocalizedName("itemKugelpanzer");
        setTextureName(CrazyWeaponMod.MOD_ID + ":itemKugelpanzer");
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int x, int y, int z, int side, float posX, float posY, float posZ) {
        if (!world.isRemote) {
            Block block = world.getBlock(x, y, z);
            x += Facing.offsetsXForSide[side];
            y += Facing.offsetsYForSide[side];
            z += Facing.offsetsZForSide[side];
            double height = 0.0D;

            if (side == 1 && block.getRenderType() == 11) {
                height = 0.5D;
            }

            Entity entity = spawnEntity(player, world, (double) x + 0.5D, (double) y + height, (double) z + 0.5D);

            if (entity != null) {
                if (!player.capabilities.isCreativeMode) {
                    --itemStack.stackSize;
                }
            }
        }
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            MovingObjectPosition movingobjectposition = this.getMovingObjectPositionFromPlayer(world, player, true);
            if (movingobjectposition != null) {
                if (movingobjectposition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
                    int x = movingobjectposition.blockX;
                    int y = movingobjectposition.blockY;
                    int z = movingobjectposition.blockZ;
                    if (!world.canMineBlock(player, x, y, z)) {
                        return itemStack;
                    }
                    if (!player.canPlayerEdit(x, y, z, movingobjectposition.sideHit, itemStack)) {
                        return itemStack;
                    }
                    if (world.getBlock(x, y, z) instanceof BlockLiquid) {
                        Entity entity = spawnEntity(player, world, x, y, z);


                        if (entity != null) {
                            if (!player.capabilities.isCreativeMode) {
                                --itemStack.stackSize;
                            }
                        }
                    }
                }
            }
        }
        return itemStack;
    }

    public Entity spawnEntity(EntityPlayer player, World world, double x, double y, double z) {
        EntityKugelpanzer entityKugelpanzer = new EntityKugelpanzer(world);
        int l = MathHelper.floor_double((double) (player.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        if (l == 0) {
            l = 180;
        } else if (l == 1) {
            l = 90;
        } else if (l == 2) {
            l = 0;
        } else {
            l = 270;
        }
        entityKugelpanzer.setLocationAndAngles(x, y, z, l, 0.0F);
        world.spawnEntityInWorld(entityKugelpanzer);
        return entityKugelpanzer;
    }
}