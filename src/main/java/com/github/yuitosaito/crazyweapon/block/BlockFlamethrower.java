package com.github.yuitosaito.crazyweapon.block;

import com.github.yuitosaito.crazyweapon.CrazyWeaponMod;
import com.github.yuitosaito.crazyweapon.tileentity.TileEntityBlockFlamethrower;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import java.util.Random;

public class BlockFlamethrower extends BlockContainer {

    public BlockFlamethrower() {
        super(Material.rock);
        setCreativeTab(CrazyWeaponMod.tabCWM);
        setBlockName("blockFlamethrower");
        setStepSound(Block.soundTypeMetal);
        setBlockTextureName(CrazyWeaponMod.MOD_ID + ":flamethrower");
        setHardness(5.0F);
        setResistance(10.0F);
        setHarvestLevel("pickaxe", 1);
        setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 2.0F, 1.0F);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float posX, float posY, float posZ) {
        if (!world.isRemote) {
            world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
            TileEntityBlockFlamethrower tile = (TileEntityBlockFlamethrower) world.getTileEntity(x, y, z);
            tile.setFire(!tile.getFire());
            world.markBlockForUpdate(x, y, z);
        }
        return true;
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block neighborBlock) {
        if (!world.isRemote) {
            boolean flag = world.isBlockIndirectlyGettingPowered(x, y, z);
            TileEntityBlockFlamethrower tile = (TileEntityBlockFlamethrower) world.getTileEntity(x, y, z);
            boolean flag1 = tile.getFire();

            if (flag && !flag1) {
                tile.setFire(true);
            } else if (!flag && flag1) {
                tile.setFire(false);
            }
            world.markBlockForUpdate(x, y, z);
        }
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public int getRenderType() {
        return -1;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random) {
        //ドロップするアイテムを返す
        return quantityDroppedWithBonus(fortune, random);
    }

    @Override
    public int quantityDropped(Random random) {
        //ドロップさせる量を返す
        return 1;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int par2) {
        return new TileEntityBlockFlamethrower();
    }
}