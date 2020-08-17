package com.github.yuitosaito.crazyweapon.network;

import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import com.github.yuitosaito.crazyweapon.entity.RenderEntityPanjandrum;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class CWMClientProxy extends CWMCommonProxy {
    @Override
    public void render() {
        RenderingRegistry.registerEntityRenderingHandler(EntityPanjandrum.class, new RenderEntityPanjandrum());
    }
}
