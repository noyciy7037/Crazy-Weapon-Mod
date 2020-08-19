package com.github.yuitosaito.crazyweapon.entity;

import com.github.yuitosaito.crazyweapon.network.CWMPacketHandler;
import com.github.yuitosaito.crazyweapon.network.MessagePanjandrum;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

import java.util.Random;


public class EntityPanjandrum extends Entity {
    public final double G = 0.31481481;
    private float damage = 0;
    private double fallSpeed = 0;
    private final Random rnd = new Random();
    private float rotateX;
    private float rotateY;
    private float rotateZ;
    private boolean started = false;
    private boolean rollover = false;
    private boolean explode = false;
    public boolean loaded = false;
    private boolean preFall = false;

    public EntityPanjandrum(World world) {
        super(world);
        setSize(3, 3);
    }

    @Override
    public void entityInit() {
    }

    @Override
    public void onUpdate() {
        if (!loaded && worldObj.isRemote) {
            CWMPacketHandler.INSTANCE.sendToServer(new MessagePanjandrum(this.getEntityId(), 1, started ? 1 : 0));
            CWMPacketHandler.INSTANCE.sendToServer(new MessagePanjandrum(this.getEntityId(), 0, rollover ? 1 : 0));
            CWMPacketHandler.INSTANCE.sendToServer(new MessagePanjandrum(this.getEntityId(), 2, rotateY));
            return;
        }
        if (explode) explode();
        motionY = 0;
        int x = (int) this.posX;
        int y = (int) this.posY;
        int z = (int) this.posZ;
        if (started) {
            if (this.isCollided) {
                if (preFall) {
                    if (!worldObj.isRemote)
                        explode();
                    started = false;
                } else {
                    preFall = true;
                }
            }
            if (worldObj.getBlock(x, y - 1, z).getMaterial() == Material.air) {
                if (worldObj.getBlock(x + 1, y - 1, z + 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x + 1, y - 1, z).getMaterial() == Material.air &&
                        worldObj.getBlock(x + 1, y - 1, z - 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x, y - 1, z + 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x, y - 1, z - 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x - 1, y - 1, z + 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x - 1, y - 1, z).getMaterial() == Material.air &&
                        worldObj.getBlock(x - 1, y - 1, z - 1).getMaterial() == Material.air) {
                    fallSpeed += G;
                    motionY = -fallSpeed;
                    preFall = false;
                } else if (fallSpeed != 0) {
                    fallSpeed = 0;
                }
            } else if (fallSpeed != 0) {
                fallSpeed = 0;
            }
            if (rotateZ + 360 * 0.41667F / (3 * 3.14F) >= 360) {
                rotateZ = (rotateZ + 360 * 0.41667F / (3 * 3.14F)) - 360;
            } else {
                rotateZ += 360 * 0.41667F / (3 * 3.14F);
            }
            double[] xzSpeed = getXZSpeed(0.41667, rotateY);
            if (worldObj.isRemote) spawnSmoke();
            this.moveEntity(xzSpeed[0], motionY, xzSpeed[1]);
            if (!worldObj.isRemote) {
                int rand = rnd.nextInt(1000);
                if (rand == 0 && !rollover) {
                    CWMPacketHandler.INSTANCE.sendToAll(new MessagePanjandrum(this.getEntityId(), 0, 1));
                    rollover = true;
                }
            }
            if (rollover) {
                if (rotateX < 90) {
                    rotateX += 2;
                } else {
                    if (!worldObj.isRemote)
                        explode();
                }
            }
        } else {
            if (this.isCollided) {
                fallSpeed = 0;
            }
            if (worldObj.getBlock(x, y - 1, z).getMaterial() == Material.air) {
                if (worldObj.getBlock(x + 1, y - 1, z + 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x + 1, y - 1, z).getMaterial() == Material.air &&
                        worldObj.getBlock(x + 1, y - 1, z - 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x, y - 1, z + 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x, y - 1, z - 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x - 1, y - 1, z + 1).getMaterial() == Material.air &&
                        worldObj.getBlock(x - 1, y - 1, z).getMaterial() == Material.air &&
                        worldObj.getBlock(x - 1, y - 1, z - 1).getMaterial() == Material.air) {
                    fallSpeed += G;
                    motionY = -fallSpeed;
                } else if (fallSpeed != 0) {
                    fallSpeed = 0;
                }
            } else if (fallSpeed != 0) {
                fallSpeed = 0;
            }
            this.moveEntity(0, motionY, 0);
        }
        super.onUpdate();
    }

    private void spawnSmoke() {
        double[] xzSpeed = getXZSpeed(0.41667, rotateY);
        worldObj.spawnParticle("explode", posX, posY, posZ, -xzSpeed[0], 0, -xzSpeed[1]);
        worldObj.spawnParticle("explode", posX, posY + 0.5, posZ, -xzSpeed[0], 0, -xzSpeed[1]);
        worldObj.spawnParticle("explode", posX, posY + 1, posZ, -xzSpeed[0], 0, -xzSpeed[1]);
        worldObj.spawnParticle("explode", posX, posY + 1.5, posZ, -xzSpeed[0], 0, -xzSpeed[1]);
        worldObj.spawnParticle("explode", posX, posY + 2, posZ, -xzSpeed[0], 0, -xzSpeed[1]);
        worldObj.spawnParticle("explode", posX, posY + 3, posZ, -xzSpeed[0], 0, -xzSpeed[1]);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float damage) {
        if (source.isExplosion()) {
            explode = true;
            return false;
        }
        if (worldObj.isRemote) return false;
        if (source.getSourceOfDamage() != null) {
            if (source.getSourceOfDamage() instanceof EntityPlayer) {
                this.damage += damage;
                final float MAX_DAMAGE = 1;
                if (this.damage >= MAX_DAMAGE) this.setDead();
                return true;
            }
        }
        if (!worldObj.isRemote)
            explode();
        return false;
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        rotateX = nbt.getFloat("rotateX");
        rotateY = nbt.getFloat("rotateY");
        rotateZ = nbt.getFloat("rotateZ");
        started = nbt.getBoolean("started");
        rollover = nbt.getBoolean("rollover");
        fallSpeed = nbt.getDouble("fallSpeed");
        damage = nbt.getFloat("damage");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        nbt.setFloat("rotateX", rotateX);
        nbt.setFloat("rotateY", rotateY);
        nbt.setFloat("rotateZ", rotateZ);
        nbt.setBoolean("started", started);
        nbt.setBoolean("rollover", rollover);
        nbt.setDouble("fallSpeed", fallSpeed);
        nbt.setFloat("damage", damage);
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
        if (player.isSneaking()) {
            started = true;
            return true;
        }
        return false;
    }

    public void explode() {
        worldObj.createExplosion(this, posX, posY, posZ, 10, true);
        this.setDead();
    }

    public float getRotateX() {
        return rotateX;
    }

    public float getRotateY() {
        return rotateY;
    }

    public float getRotateZ() {
        return rotateZ;
    }

    public void setRotateY(float rotateY) {
        this.rotateY = rotateY;
    }

    public void setRollover(boolean bool) {
        rollover = bool;
    }

    public void setStarted(boolean bool) {
        started = bool;
    }

    public boolean getRollover() {
        return rollover;
    }

    public boolean getStarted() {
        return started;
    }

    public double[] getXZSpeed(double speed, double angle) {
        double[] xzSpeed = new double[2];
        if (angle == 0) {
            xzSpeed[0] = speed;
            xzSpeed[1] = 0;
        } else if (angle > 0 && angle < 90) {
            xzSpeed[0] = Math.cos(Math.toRadians(angle)) * speed;
            xzSpeed[1] = Math.sin(Math.toRadians(angle)) * speed;
        } else if (angle == 90) {
            xzSpeed[0] = 0;
            xzSpeed[1] = speed;
        } else if (angle > 90 && angle < 180) {
            xzSpeed[0] = -Math.sin(Math.toRadians(angle - 90)) * speed;
            xzSpeed[1] = Math.cos(Math.toRadians(angle - 90)) * speed;
        } else if (angle == 180) {
            xzSpeed[0] = -speed;
            xzSpeed[1] = 0;
        } else if (angle > 270 && angle < 360) {
            xzSpeed[0] = Math.sin(Math.toRadians(angle - 270)) * speed;
            xzSpeed[1] = -Math.cos(Math.toRadians(angle - 270)) * speed;
        } else if (angle == 270) {
            xzSpeed[0] = 0;
            xzSpeed[1] = -speed;
        } else if (angle > 180 && angle < 270) {
            xzSpeed[0] = -Math.cos(Math.toRadians(angle - 180)) * speed;
            xzSpeed[1] = -Math.sin(Math.toRadians(angle - 180)) * speed;
        }
        return xzSpeed;
    }
}