package com.github.yuitosaito.crazyweapon.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntityKugelpanzer extends Entity {

    private float rotateX = 0;

    public EntityKugelpanzer(World world) {
        super(world);
        setSize(1.7F, 1.7F);
    }

    @Override
    public void entityInit() {
    }

    @Override
    public void onUpdate() {
        if (riddenByEntity != null) {
            if (riddenByEntity.isDead) {
                riddenByEntity = null;
                return;
            }
        } else {
            motionX = 0;
            motionZ = 0;
        }
        if (!onGround) {
            motionY -= 0.1D;
        }
        int direction = 1;
        if (this.riddenByEntity instanceof EntityPlayer) {
            EntityPlayer player = ((EntityPlayer) this.riddenByEntity);
            double move = player.moveForward > 0 ? 0.11111111111 : 0 + player.moveForward < 0 ? -0.11111111111 : 0;
            direction = player.moveForward > 0 ? 1 : 0 + player.moveForward < 0 ? -1 : 0;
            double rotationYaw = this.rotationYaw;
            if (rotationYaw < 0) {
                rotationYaw = 360F + rotationYaw;
            }
            double[] moveXZ = getXZSpeed(move, rotationYaw);
            motionX = moveXZ[0];
            motionZ = moveXZ[1];
            this.setRotation(this.rotationYaw + (player.moveStrafing % 360), this.rotationPitch);
        }
        double move = Math.sqrt(motionX * motionX + motionZ * motionZ);
        rotateX = (float) (((move * 360 / (1.72 * Math.PI)) * direction + rotateX) % 360F);
        this.moveEntity(motionX, motionY, motionZ);
        super.onUpdate();
    }

    @Override
    public void updateRiderPosition() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.setPosition(this.posX, this.posY + this.riddenByEntity.getYOffset() + 0.2D, this.posZ);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (riddenByEntity != null)
            riddenByEntity = null;
        this.setDead();
        return true;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {

    }

    @Override
    public float getShadowSize() {
        return 3.0F;
    }

    @Override
    public boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public boolean interactFirst(EntityPlayer player) {
        if (this.riddenByEntity == null) {
            player.mountEntity(this);
            if (worldObj.isRemote) {
                Minecraft minecraft = Minecraft.getMinecraft();
                minecraft.ingameGUI.func_110326_a(I18n.format("mount.onboard", GameSettings.getKeyDisplayString(minecraft.gameSettings.keyBindSneak.getKeyCode())), false);
            }
        }
        return false;
    }

    public float getRotateX() {
        return rotateX;
    }

    public double[] getXZSpeed(double speed, double angle) {
        double[] xzSpeed = new double[2];
        if (angle == 0) {
            xzSpeed[0] = speed;
            xzSpeed[1] = 0;
        } else if (angle > 0 && angle < 90) {
            xzSpeed[0] = Math.cos(Math.toRadians(angle)) * speed;
            xzSpeed[1] = -Math.sin(Math.toRadians(angle)) * speed;
        } else if (angle == 90) {
            xzSpeed[0] = 0;
            xzSpeed[1] = -speed;
        } else if (angle > 90 && angle < 180) {
            xzSpeed[0] = -Math.sin(Math.toRadians(angle - 90)) * speed;
            xzSpeed[1] = -Math.cos(Math.toRadians(angle - 90)) * speed;
        } else if (angle == 180) {
            xzSpeed[0] = -speed;
            xzSpeed[1] = 0;
        } else if (angle > 270 && angle < 360) {
            xzSpeed[0] = Math.sin(Math.toRadians(angle - 270)) * speed;
            xzSpeed[1] = Math.cos(Math.toRadians(angle - 270)) * speed;
        } else if (angle == 270) {
            xzSpeed[0] = 0;
            xzSpeed[1] = speed;
        } else if (angle > 180 && angle < 270) {
            xzSpeed[0] = -Math.cos(Math.toRadians(angle - 180)) * speed;
            xzSpeed[1] = Math.sin(Math.toRadians(angle - 180)) * speed;
        }
        return xzSpeed;
    }
}