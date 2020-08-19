package com.github.yuitosaito.crazyweapon.item;

import com.github.yuitosaito.crazyweapon.CrazyWeaponMod;
import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import com.github.yuitosaito.crazyweapon.network.CWMPacketHandler;
import com.github.yuitosaito.crazyweapon.network.MessagePanjandrum;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemPanjandrum extends Item {

    public ItemPanjandrum() {
        super();
        setCreativeTab(CrazyWeaponMod.tabCWM);
        setUnlocalizedName("itemPanjandrum");
        setTextureName(CrazyWeaponMod.MOD_ID + ":itemPanjandrum");
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
        float rotationYaw = player.rotationYaw;
        if (rotationYaw < 0) {
            rotationYaw = 360F + rotationYaw;
        }
        EntityPanjandrum entityPanjandrum = new EntityPanjandrum(world);
        entityPanjandrum.setLocationAndAngles(x, y, z, 0.0F, 0.0F);
        entityPanjandrum.setRotateY(rotationYaw);
        world.spawnEntityInWorld(entityPanjandrum);
        CWMPacketHandler.INSTANCE.sendToAll(new MessagePanjandrum(entityPanjandrum.getEntityId(), 2, rotationYaw));
        return entityPanjandrum;
    }
}