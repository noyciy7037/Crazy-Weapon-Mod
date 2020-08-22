package com.github.yuitosaito.crazyweapon.network;

import com.github.yuitosaito.crazyweapon.entity.EntityKugelpanzer;
import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import com.github.yuitosaito.crazyweapon.entity.RenderEntityKugelpanzer;
import com.github.yuitosaito.crazyweapon.entity.RenderEntityPanjandrum;
import com.github.yuitosaito.crazyweapon.tileentity.TileEntityBlockFlamethrower;
import com.github.yuitosaito.crazyweapon.tileentity.renderer.RenderTileEntityBlockFlamethrower;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CWMClientProxy extends CWMCommonProxy {
    @Override
    public void render() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPanjandrum.class, new RenderEntityPanjandrum());
        RenderingRegistry.registerEntityRenderingHandler(EntityKugelpanzer.class, new RenderEntityKugelpanzer());
    }

    @Override
    public void registerTileEntity() {
        GameRegistry.registerTileEntity(TileEntityBlockFlamethrower.class, "tileFlamethrower");
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityBlockFlamethrower.class, new RenderTileEntityBlockFlamethrower());
    }
}
