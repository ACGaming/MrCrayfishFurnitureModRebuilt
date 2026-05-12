package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureBlocks;

public class BlockOldLampOn extends BlockOldLamp
{
	public BlockOldLampOn(Material material, String id)
	{
		super(material, true, id);
		this.setLightLevel(1.0F);
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		world.setBlockState(pos, FurnitureBlocks.LAMP_OFF_OLD.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		return true;
	}
}
