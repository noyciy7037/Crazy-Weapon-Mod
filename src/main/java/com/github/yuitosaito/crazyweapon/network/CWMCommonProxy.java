package com.github.yuitosaito.crazyweapon.network;

import com.github.yuitosaito.crazyweapon.tileentity.TileEntityBlockFlamethrower;
import cpw.mods.fml.common.registry.GameRegistry;

public class CWMCommonProxy {
    public void render() {
    }

    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityBlockFlamethrower.class, "tileFlamethrower");
    }
}
