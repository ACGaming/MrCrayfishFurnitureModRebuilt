package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import java.util.ArrayList;
import java.util.List;

public class BlockWaterTank extends Block
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 2);

    private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0);
    private static final AxisAlignedBB SIDE_NORTH = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.3, 0.1);
    private static final AxisAlignedBB SIDE_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.9, 1.0, 1.3, 1.0);
    private static final AxisAlignedBB SIDE_WEST = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1, 1.3, 1.0);
    private static final AxisAlignedBB SIDE_EAST = new AxisAlignedBB(0.9, 0.0, 0.0, 1.0, 1.3, 1.0);
    private static final AxisAlignedBB TANK_OUTLINE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.3, 1.0);

    public BlockWaterTank(Material material, String id)
    {
        super(material);
        this.setHardness(1F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, 0));
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setTranslationKey(id);
        this.setRegistryName(id);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        int level = (meta >> 2) & 3;
        if (level > 2) level = 2;
        return this.getDefaultState().withProperty(FACING, facing).withProperty(LEVEL, level);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facing = state.getValue(FACING).getHorizontalIndex();
        int level = state.getValue(LEVEL);
        return facing | (level << 2);
    }

    @Override
    public boolean isFullCube(IBlockState state) {return false;}

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return TANK_OUTLINE;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_SOUTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_EAST);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ)
    {
        ItemStack held = player.getHeldItem(hand);
        int currentLevel = state.getValue(LEVEL);
        EnumFacing tankFacing = state.getValue(FACING);
        if (held.getItem() == Items.WATER_BUCKET)
        {
            int nextLevel = currentLevel + 1;
            if (nextLevel > 2) return false;
            world.setBlockState(pos, state.withProperty(LEVEL, nextLevel).withProperty(FACING, tankFacing));
            if (!player.capabilities.isCreativeMode) player.setHeldItem(hand, new ItemStack(Items.BUCKET));
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            return true;
        }
        if (held.getItem() == Items.BUCKET && currentLevel > 0)
        {
            int nextLevel = currentLevel - 1;
            world.setBlockState(pos, state.withProperty(LEVEL, nextLevel).withProperty(FACING, tankFacing));
            if (!player.capabilities.isCreativeMode) player.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));
            world.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
            if (world.isRemote)
            {
                EnumFacing facingTank = state.getValue(FACING);
                double x = pos.getX();
                double y = pos.getY() + 0.2;
                double z = pos.getZ();
                double frontOffset = 0.7;
                double sideOffset = 0.1;
                double px = 0;
                double pz = 0;
                switch (facingTank)
                {
                    case NORTH:
                        px = 0.5;
                        pz = 0.5 - frontOffset;
                        break;
                    case SOUTH:
                        px = 0.5;
                        pz = 0.5 + frontOffset;
                        break;
                    case WEST:
                        px = 0.5 - frontOffset;
                        pz = 0.5;
                        break;
                    case EAST:
                        px = 0.5 + frontOffset;
                        pz = 0.5;
                        break;
                }
                for (int i = 0; i < 5; i++)
                {
                    double particleX = x + px + (world.rand.nextDouble() - 0.5) * sideOffset * 2;
                    double particleZ = z + pz + (world.rand.nextDouble() - 0.5) * sideOffset * 2;
                    world.spawnParticle(EnumParticleTypes.DRIP_WATER, particleX, y, particleZ, 0, 0, 0);
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite()).withProperty(LEVEL, 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEVEL);
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune)
    {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(FurnitureBlocks.WATER_TANK));
        return drops;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.WATER_TANK);
    }
}
