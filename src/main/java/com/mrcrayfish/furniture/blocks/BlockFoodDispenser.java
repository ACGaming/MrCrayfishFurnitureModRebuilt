package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockFoodDispenser extends BlockFurniture
{
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyEnum<FoodType> TYPE = PropertyEnum.create("type", FoodType.class);
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);

    public BlockFoodDispenser(Material material, String id)
    {
        super(material, id);
        this.setHardness(1F);
        this.setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState()
                .withProperty(FACING, EnumFacing.NORTH)
                .withProperty(TYPE, FoodType.DOG)
                .withProperty(LEVEL, 0));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack held = player.getHeldItem(hand);

        if (held.getItem() == FurnitureItems.DOG_FOOD)
        {
            world.setBlockState(pos, state.withProperty(TYPE, FoodType.DOG).withProperty(LEVEL, 3), 3);
            if (!player.isCreative()) held.shrink(1);
            return true;
        }

        if (held.getItem() == FurnitureItems.CAT_FOOD)
        {
            world.setBlockState(pos, state.withProperty(TYPE, FoodType.CAT).withProperty(LEVEL, 3), 3);
            if (!player.isCreative()) held.shrink(1);
            return true;
        }

        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.isRemote) return;

        int level = state.getValue(LEVEL);
        if (level <= 0) return;

        AxisAlignedBB area = new AxisAlignedBB(pos).grow(1.5);

        if (state.getValue(TYPE) == FoodType.DOG)
        {
            List<EntityWolf> wolves = world.getEntitiesWithinAABB(EntityWolf.class, area);
            if (!wolves.isEmpty()) world.setBlockState(pos, state.withProperty(LEVEL, level - 1), 3);
        }

        if (state.getValue(TYPE) == FoodType.CAT)
        {
            List<EntityOcelot> cats = world.getEntitiesWithinAABB(EntityOcelot.class, area);
            if (!cats.isEmpty()) world.setBlockState(pos, state.withProperty(LEVEL, level - 1), 3);
        }

        world.scheduleUpdate(pos, this, 20);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if (!world.isRemote) world.scheduleUpdate(pos, this, 20);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int facingMeta = state.getValue(FACING).ordinal();
        int typeMeta = state.getValue(TYPE).ordinal();
        int levelMeta = state.getValue(LEVEL);
        return (facingMeta) | (typeMeta << 2) | (levelMeta << 3);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.values()[meta & 3];
        FoodType type = FoodType.values()[(meta >> 2) & 1];
        int level = (meta >> 3) & 3;
        return getDefaultState().withProperty(FACING, facing).withProperty(TYPE, type).withProperty(LEVEL, level);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, TYPE, LEVEL);
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
