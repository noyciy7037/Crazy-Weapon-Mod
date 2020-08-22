package com.github.yuitosaito.crazyweapon;

import com.github.yuitosaito.crazyweapon.block.BlockFlamethrower;
import com.github.yuitosaito.crazyweapon.entity.EntityKugelpanzer;
import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import com.github.yuitosaito.crazyweapon.item.ItemKugelpanzer;
import com.github.yuitosaito.crazyweapon.item.ItemPanjandrum;
import com.github.yuitosaito.crazyweapon.network.CWMCommonProxy;
import com.github.yuitosaito.crazyweapon.network.CWMPacketHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


@Mod(modid = CrazyWeaponMod.MOD_ID, name = CrazyWeaponMod.MOD_NAME, version = CrazyWeaponMod.MOD_VERSION)
public class CrazyWeaponMod {

    public static final String MOD_ID = "crazyweapon";
    public static final String MOD_NAME = "CrazyWeaponMod";
    public static final String MOD_VERSION = Constants.version;

    @Mod.Instance("crazyweapon")
    public static CrazyWeaponMod INSTANCE;

    @SidedProxy(clientSide = "com.github.yuitosaito.crazyweapon.network.CWMClientProxy", serverSide = "com.github.yuitosaito.crazyweapon.network.CWMCommonProxy")
    public static CWMCommonProxy proxy;

    public static Item itemPanjandrum;
    public static Item itemKugelpanzer;

    public static Block blockFlamethrower;

    public static CreativeTabs tabCWM = new CWMTab("CrazyWeaponModTab");

    @EventHandler
    public void perInit(FMLPreInitializationEvent event) {
        itemPanjandrum = new ItemPanjandrum();
        GameRegistry.registerItem(itemPanjandrum, "panjandrum");

        itemKugelpanzer = new ItemKugelpanzer();
        GameRegistry.registerItem(itemKugelpanzer, "kugelpanzer");

        blockFlamethrower = new BlockFlamethrower();
        GameRegistry.registerBlock(blockFlamethrower, "flamethrower");

        CWMPacketHandler.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        EntityRegistry.registerModEntity(EntityPanjandrum.class, "Panjandrum", 0, this, 250, 1, true);
        EntityRegistry.registerModEntity(EntityKugelpanzer.class, "Kugelpanzer", 1, this, 250, 1, true);
        proxy.render();
        proxy.registerTileEntity();
        CWMDispenser.register();
        CWMRecipe.register();
    }
}