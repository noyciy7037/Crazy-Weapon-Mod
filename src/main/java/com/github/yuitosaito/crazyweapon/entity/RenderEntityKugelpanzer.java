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
public class RenderEntityKugelpanzer extends Render {
    ResourceLocation texture;
    ResourceLocation objModelLocation;
    IModelCustom model;
    ResourceLocation objModelLocation2;
    IModelCustom model2;

    public RenderEntityKugelpanzer() {
        texture = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/kugelpanzer/kugelpanzer.png");
        objModelLocation = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/kugelpanzer/kugelpanzer1.obj");
        model = AdvancedModelLoader.loadModel(objModelLocation);
        objModelLocation2 = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/kugelpanzer/kugelpanzer2.obj");
        model2 = AdvancedModelLoader.loadModel(objModelLocation2);
    }

    @Override
    public void doRender(Entity entity, double x, double y, double z, float rx, float ry) {
        EntityKugelpanzer entityKugelpanzer = (EntityKugelpanzer) entity;
        bindTexture(texture);
        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 0.85, z);
        GL11.glRotatef(entityKugelpanzer.rotationYaw, 0, 1, 0);
        GL11.glRotatef(90, 0, 1, 0);
        GL11.glPushMatrix();
        GL11.glTranslated(-0.22, 0.05, 0);
        GL11.glRotatef(180, 0, 1, 0);
        GL11.glRotatef(-5, 0, 0, 1);
        GL11.glRotatef(-entityKugelpanzer.getRotateX(), 1, 0, 0);
        model2.renderAll();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslated(0.22, 0.05, 0);
        GL11.glRotatef(-5, 0, 0, 1);
        GL11.glRotatef(entityKugelpanzer.getRotateX(), 1, 0, 0);
        model2.renderAll();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        model.renderAll();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity p_110775_1_) {
        return texture;
    }
}