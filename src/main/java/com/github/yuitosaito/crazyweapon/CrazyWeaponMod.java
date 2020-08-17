package com.github.yuitosaito.crazyweapon;

import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import com.github.yuitosaito.crazyweapon.item.ItemPanjandrum;
import com.github.yuitosaito.crazyweapon.network.CWMCommonProxy;
import com.github.yuitosaito.crazyweapon.network.CWMPacketHandler;
import com.github.yuitosaito.crazyweapon.network.MessagePanjandrum;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.BlockDispenser;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.dispenser.IBehaviorDispenseItem;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.dispenser.IPosition;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;


@Mod(modid = CrazyWeaponMod.MOD_ID, name = CrazyWeaponMod.MOD_NAME, version = CrazyWeaponMod.MOD_VERSION)
public class CrazyWeaponMod {

    public static final String MOD_ID = "crazyweapon";
    public static final String MOD_NAME = "CrazyWeaponMod";
    public static final String MOD_VERSION = Constants.version;

    @Mod.Instance("crazyweapon")
    public static CrazyWeaponMod INSTANCE;

    @SidedProxy(clientSide = "com.github.yuitosaito.crazyweapon.network.CWMClientProxy", serverSide = "com.github.yuitosaito.crazyweapon.network.CWMServerProxy")
    public static CWMCommonProxy proxy;

    public static Item itemPanjandrum;

    public static CreativeTabs tabCWM = new CWMTab("CrazyWeaponModTab");

    @EventHandler
    public void perInit(FMLPreInitializationEvent event) {
        itemPanjandrum = new ItemPanjandrum();
        GameRegistry.registerItem(itemPanjandrum, "panjandrum");
        CWMPacketHandler.init();
    }


    @EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityPanjandrum.class, "Panjandrum", 0, this, 250, 1, true);
        proxy.render();
        BlockDispenser.dispenseBehaviorRegistry.putObject(itemPanjandrum, new IBehaviorDispenseItem() {
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

        GameRegistry.addRecipe(new ItemStack(itemPanjandrum),
                "G#G",
                "#T#",
                "G#G",
                '#', Blocks.planks,
                'G', Items.fireworks,
                'T', Blocks.tnt
        );
    }
}