package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.tileentity.TileEntityBowlCatFood;

// TODO: Create dog food bowl equivalent
public class BlockBowlCatFood extends BlockBowl implements ITileEntityProvider
{
    public BlockBowlCatFood(Material material, String id)
    {
        super(material, id);
        setTickRandomly(true);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityBowlCatFood();
    }

    @Override
    public boolean hasTileEntity(IBlockState state)
    {
        return true;
    }
}
