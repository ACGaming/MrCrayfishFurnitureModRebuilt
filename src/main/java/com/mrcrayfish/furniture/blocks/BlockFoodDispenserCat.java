package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureItems;
import java.util.List;
import java.util.Random;

public class BlockFoodDispenserCat extends BlockFurniture
{
    public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 3);

    public BlockFoodDispenserCat(Material material, String id)
    {
        super(material, id);
        this.setHardness(1F);
        this.setSoundType(SoundType.METAL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(LEVEL, 0));
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta, net.minecraft.entity.EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
                                    EntityPlayer player, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ)
    {
        ItemStack held = player.getHeldItem(hand);

        if (state.getValue(LEVEL) == 3) return false;

        if (!held.isEmpty() && held.getItem() == FurnitureItems.CAT_FOOD)
        {
            if (!world.isRemote)
            {
                world.setBlockState(pos, state.withProperty(LEVEL, 3), 3);
                if (!player.isCreative()) held.shrink(1);

                ItemStack conserve = new ItemStack(FurnitureItems.CONSERVE_CAN);
                if (!player.inventory.addItemStackToInventory(conserve))
                    player.dropItem(conserve, false);
            }
            return true;
        }

        return false;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if (!world.isRemote)
            world.scheduleUpdate(pos, this, 1);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.isRemote) return;

        int level = state.getValue(LEVEL);
        if (level <= 0)
        {
            world.scheduleUpdate(pos, this, 20);
            return;
        }

        boolean ate = false;
        AxisAlignedBB area = new AxisAlignedBB(pos).grow(1.5);
        List<EntityOcelot> cats = world.getEntitiesWithinAABB(EntityOcelot.class, area);

        for (EntityOcelot cat : cats)
        {
            if (cat.getHealth() < cat.getMaxHealth())
            {
                cat.heal(4.0F);
                world.playSound(null, cat.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F);
                ate = true;
            }
        }

        if (ate)
            world.setBlockState(pos, state.withProperty(LEVEL, level - 1), 3);

        world.scheduleUpdate(pos, this, 20);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        int meta = state.getValue(FACING).getHorizontalIndex();
        meta |= state.getValue(LEVEL) << 2;
        return meta;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        int level = (meta >> 2) & 3;
        return this.getDefaultState().withProperty(FACING, facing).withProperty(LEVEL, level);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEVEL);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, net.minecraft.util.math.RayTraceResult target,
                                  World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(com.mrcrayfish.furniture.init.FurnitureBlocks.FOOD_DISPENSER);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
    }

    @Override
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
}
