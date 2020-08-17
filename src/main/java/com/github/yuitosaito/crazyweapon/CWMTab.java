package com.github.yuitosaito.crazyweapon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;


public class CWMTab extends CreativeTabs {


    public CWMTab(String label) {
        super(label);
    }


    @Override
    public Item getTabIconItem() {
        return CrazyWeaponMod.itemPanjandrum;
    }


}