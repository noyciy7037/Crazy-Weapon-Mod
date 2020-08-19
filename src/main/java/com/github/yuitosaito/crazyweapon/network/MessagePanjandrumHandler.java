package com.github.yuitosaito.crazyweapon.network;

import com.github.yuitosaito.crazyweapon.entity.EntityPanjandrum;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.client.Minecraft;

public class MessagePanjandrumHandler implements IMessageHandler<MessagePanjandrum, IMessage> {
    @Override
    public IMessage onMessage(MessagePanjandrum message, MessageContext ctx) {
        if (ctx.side == Side.CLIENT) {
            EntityPanjandrum entity = (EntityPanjandrum) Minecraft.getMinecraft().theWorld.getEntityByID(message.entityId);
            if (message.type == 0) {
                if (entity == null) return null;
                entity.setRollover(message.data == 1);
                entity.loaded = true;
            } else if (message.type == 1) {
                entity.setStarted(message.data == 1);
                entity.loaded = true;
            } else if (message.type == 2) {
                entity.setRotateY((float) message.data);
                entity.loaded = true;
            }
        }
        if (ctx.side == Side.SERVER) {
            EntityPanjandrum entity = (EntityPanjandrum) ctx.getServerHandler().playerEntity.worldObj.getEntityByID(message.entityId);
            if (message.type == 0) {
                return new MessagePanjandrum(entity.getEntityId(), 0, entity.getRollover() ? 1 : 0);
            } else if (message.type == 1) {
                return new MessagePanjandrum(entity.getEntityId(), 1, entity.getStarted() ? 1 : 0);
            } else if (message.type == 2) {
                return new MessagePanjandrum(entity.getEntityId(), 2, entity.getRotateY());
            }
        }
        return null;
    }
}