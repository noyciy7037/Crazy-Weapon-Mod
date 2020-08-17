package com.github.yuitosaito.crazyweapon.entity;

import com.github.yuitosaito.crazyweapon.CrazyWeaponMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;


@SideOnly(Side.CLIENT)
public class RenderEntityPanjandrum extends Render {
    ResourceLocation texture;
    ResourceLocation objModelLocation;
    IModelCustom model;

    public RenderEntityPanjandrum() {
        texture = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/panjandrum/panjandrum.png");
        objModelLocation = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/panjandrum/panjandrum.obj");
        model = AdvancedModelLoader.loadModel(objModelLocation);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float rx, float ry) {
        EntityPanjandrum entityPanjandrum = (EntityPanjandrum) entity;
        bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 1.5, z);
        GL11.glScalef(0.5F, 0.5F, 0.5F);
        GL11.glPushMatrix();
        GL11.glRotatef(540 - entityPanjandrum.getRotateY(), 0F, 1F, 0F);
        GL11.glRotatef(entityPanjandrum.getRotateZ(), 0F, 0F, 1F);
        GL11.glRotatef(entityPanjandrum.getRotateX(), 1F, 0F, 0F);
        model.renderAll();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return texture;
    }
}