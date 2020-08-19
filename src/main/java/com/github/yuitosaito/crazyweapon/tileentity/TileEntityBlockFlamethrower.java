package com.github.yuitosaito.crazyweapon.tileentity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityExplodeFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFlameFX;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.biome.BiomeGenBase;

import java.util.List;
import java.util.Random;

public class TileEntityBlockFlamethrower extends TileEntity {
    private boolean isFire = false;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return AxisAlignedBB.getBoundingBox(xCoord - 1, yCoord, zCoord - 1, xCoord + 2, yCoord + 7, zCoord + 2);
    }

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (!isFire) return;
        if (!this.canFire(xCoord, yCoord + 7, zCoord + 1)) return;
        if (worldObj.isRemote) {
            worldObj.playAuxSFX(1009, xCoord, yCoord, zCoord, 0);
            Random random = new Random();
            for (int j = 0; j < 5; ++j) {
                double x = xCoord + (j == 0 ? -0.7 : 0) + (j == 4 ? 0.7 : 0);
                double z = zCoord + (j == 1 ? -0.7 : 0) + (j == 3 ? 0.7 : 0);
                for (int i = 0; i < 100 / 6; ++i) {
                    if (random.nextInt(3) == 0)
                        this.spawnParticle("flame", x + 0.5, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    if (random.nextInt(3) == 0)
                        this.spawnParticle("flame", x + 0.5, yCoord + 7 + (6 * i), z + 1.1, 0, 1, 0);
                    if (random.nextInt(3) == 0)
                        this.spawnParticle("flame", x + 0.5, yCoord + 7 + (6 * i), z + 1.3, 0, 1, 0);
                    if (random.nextInt(3) == 0)
                        this.spawnParticle("flame", x + 0.6, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    if (random.nextInt(3) == 0)
                        this.spawnParticle("flame", x + 0.6, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    this.spawnParticle("explode", x + 0.5, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    this.spawnParticle("explode", x + 0.5, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    this.spawnParticle("explode", x + 0.5, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    this.spawnParticle("explode", x + 0.5, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                    this.spawnParticle("explode", x + 0.5, yCoord + 7 + (6 * i), z + 1.2, 0, 1, 0);
                }
            }
        } else {
            @SuppressWarnings("rawtypes")
            List entityList = worldObj.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord + 7, zCoord + 0.7, xCoord + 1, yCoord + 107, zCoord + 1.7));
            for (Object o : entityList) {
                ((Entity) o).setFire(8);
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1NBTTagCompound) {
        super.readFromNBT(par1NBTTagCompound);
        this.isFire = par1NBTTagCompound.getBoolean("isFire");
    }

    @Override
    public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
        super.writeToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setBoolean("isFire", this.isFire);
    }

    @Override
    public Packet getDescriptionPacket() {
        NBTTagCompound nbtTagCompound = new NBTTagCompound();
        this.writeToNBT(nbtTagCompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, nbtTagCompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
        this.readFromNBT(pkt.func_148857_g());
    }

    public void setFire(boolean isFire) {
        this.isFire = isFire;
    }

    public boolean getFire() {
        return this.isFire;
    }

    public boolean canFire(int x, int y, int z) {
        if (!worldObj.canBlockSeeTheSky(x, y, z)) {
            return false;
        } else if (worldObj.getPrecipitationHeight(x, z) > y) {
            return false;
        } else {
            BiomeGenBase biomegenbase = worldObj.getBiomeGenForCoords(x, z);
            return !biomegenbase.getEnableSnow() && (!worldObj.func_147478_e(x, y, z, false) && biomegenbase.canSpawnLightningBolt());
        }
    }

    @SideOnly(Side.CLIENT)
    public void spawnParticle(String particle, double x, double y, double z, double vecX, double vecY, double vecZ) {
        EntityFX e;
        if ("flame".equals(particle)) {
            e = new EntityFlameFX(worldObj, x, y, z, vecX, vecY, vecZ);
        } else {
            e = new EntityExplodeFX(worldObj, x, y, z, vecX, vecY, vecZ);
        }
        e.renderDistanceWeight = Float.POSITIVE_INFINITY;
        Minecraft.getMinecraft().effectRenderer.addEffect(e);
    }
}