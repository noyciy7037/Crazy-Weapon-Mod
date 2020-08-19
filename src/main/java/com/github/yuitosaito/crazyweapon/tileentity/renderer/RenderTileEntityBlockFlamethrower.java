package com.github.yuitosaito.crazyweapon.tileentity.renderer;

import com.github.yuitosaito.crazyweapon.CrazyWeaponMod;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.AdvancedModelLoader;
import net.minecraftforge.client.model.IModelCustom;
import org.lwjgl.opengl.GL11;

public class RenderTileEntityBlockFlamethrower extends TileEntitySpecialRenderer {
    ResourceLocation texture;
    ResourceLocation objModelLocation;
    IModelCustom model;

    public RenderTileEntityBlockFlamethrower() {
        texture = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/flamethrower/flamethrower.png");
        objModelLocation = new ResourceLocation(CrazyWeaponMod.MOD_ID, "models/flamethrower/flamethrower.obj");
        model = AdvancedModelLoader.loadModel(objModelLocation);
    }

    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double posX, double posY, double posZ, float timeSinceLastTick) {
        bindTexture(texture);

        GL11.glPushMatrix();
        GL11.glTranslated(posX + 0.5, posY + 0.5, posZ + 0.5);
        GL11.glScalef(1, 1, 1);
        GL11.glPushMatrix();
        model.renderAll();
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }
}
