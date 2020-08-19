package com.github.yuitosaito.crazyweapon;

import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import com.github.yuitosaito.crazyweapon.network.CWMPacketHandler;
import com.github.yuitosaito.crazyweapon.network.MessagePanjandrum;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CWMDispenser {
    public static void register() {
        BlockDispenser.dispenseBehaviorRegistry.putObject(CrazyWeaponMod.itemPanjandrum, new IBehaviorDispenseItem() {
            @Override
            public ItemStack dispense(IBlockSource var1, ItemStack var2) {
                World world = var1.getWorld();//World
                IPosition iposition = BlockDispenser.func_149939_a(var1);//IPosition
                double x1 = iposition.getX();
                double y1 = iposition.getY();
                double z1 = iposition.getZ();
                int meta = var1.getBlockMetadata();
                float rotationYaw = 0;
                y1 = (int) y1;
                EntityPanjandrum entity;
                if (meta == 8) {//D
                    y1 = (int) y1 - 2;
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                } else if (meta == 9) {//U
                    y1 = (int) y1;
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                } else if (meta == 10) {//N
                    rotationYaw = 180;
                    z1 -= 1.5;
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                } else if (meta == 11) {//S
                    rotationYaw = 0;
                    z1 += 1.5;
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                } else if (meta == 12) {//W
                    rotationYaw = 90;
                    x1 -= 1.5;
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                } else if (meta == 13) {//E
                    rotationYaw = 270;
                    x1 += 1.5;
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                } else {
                    entity = spawnEntity(rotationYaw, world, x1, y1, z1);
                }
                entity.setStarted(true);
                if (!world.isRemote)
                    CWMPacketHandler.INSTANCE.sendToAll(new MessagePanjandrum(entity.getEntityId(), 1, 1));
                var2.stackSize--;//アイテムをひとつ減らす
                return var2;
            }

            public EntityPanjandrum spawnEntity(float rotationYaw, World world, double x, double y, double z) {
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
        });
    }
}
