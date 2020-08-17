package com.github.yuitosaito.crazyweapon.network;

import com.github.yuitosaito.crazyweapon.CrazyWeaponMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;


public class CWMPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(CrazyWeaponMod.MOD_ID);

    public static void init() {
        INSTANCE.registerMessage(MessagePanjandrumHandler.class, MessagePanjandrum.class, 0, Side.CLIENT);
        INSTANCE.registerMessage(MessagePanjandrumHandler.class, MessagePanjandrum.class, 1, Side.SERVER);
    }
}