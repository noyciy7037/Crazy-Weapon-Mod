package com.github.yuitosaito.crazyweapon;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class CWMRecipe {
    public static void register() {
        GameRegistry.addRecipe(new ItemStack(CrazyWeaponMod.itemPanjandrum),
                "G#G",
                "#T#",
                "G#G",
                '#', Blocks.planks,
                'G', Items.fireworks,
                'T', Blocks.tnt
        );
        GameRegistry.addRecipe(new ItemStack(CrazyWeaponMod.blockFlamethrower),
                "###",
                "#M#",
                "#C#",
                '#', Blocks.iron_block,
                'M', Items.lava_bucket,
                'C', Items.coal
        );
    }
}
