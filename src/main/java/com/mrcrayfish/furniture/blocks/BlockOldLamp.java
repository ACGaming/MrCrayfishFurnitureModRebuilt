package com.mrcrayfish.furniture.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureBlocks;
import java.util.List;
import java.util.Random;

public class BlockOldLamp extends BlockFurniture
{
	private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.0625, 0.0, 0.0625, 0.9375, 0.9375, 0.9375);
	private static final String[] COLORS = {"§c", "§e", "§a", "§b", "§d", "§6"};

	public BlockOldLamp(Material material, boolean on, String id)
	{
		super(material, id);
		this.setHardness(0.75F);
		this.setSoundType(SoundType.CLOTH);
		if (on)
		{
			this.setCreativeTab(null);
		}
	}

	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
	{
		return BOUNDING_BOX;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
	{
		world.setBlockState(pos, FurnitureBlocks.LAMP_ON_OLD.getDefaultState().withProperty(FACING, state.getValue(FACING)));
		return true;
	}

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.LAMP_OFF_OLD).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.LAMP_OFF_OLD);
    }

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
	{
		tooltip.add(COLORS[(int) ((System.currentTimeMillis() / 500) % COLORS.length)] + "1.7.10 Furniture");
	}
}
