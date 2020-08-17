package com.github.yuitosaito.crazyweapon.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;

public class MessagePanjandrum implements IMessage {

    public int entityId;
    public int type;
    public double data;

    @SuppressWarnings("unused")
    public MessagePanjandrum() {
    }

    public MessagePanjandrum(int entityId, int type, double data) {
        this.entityId = entityId;
        this.type = type;
        this.data = data;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.entityId = buf.readInt();
        this.type = buf.readInt();
        this.data = buf.readDouble();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(this.entityId);
        buf.writeInt(this.type);
        buf.writeDouble(this.data);
    }
}