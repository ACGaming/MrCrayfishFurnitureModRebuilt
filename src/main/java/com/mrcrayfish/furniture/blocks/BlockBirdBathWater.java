package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

public class BlockBirdBathWater extends Block {
    public static final AxisAlignedBB CENTER = new AxisAlignedBB(5 * 0.0625, 1.75 * 0.0625, 5 * 0.0625, 11 * 0.0625, 12 * 0.0625, 11 * 0.0625);
    public static final AxisAlignedBB BOTTOM_LIP = new AxisAlignedBB(4 * 0.0625, 0, 4 * 0.0625, 12 * 0.0625, 1.5 * 0.0625, 12 * 0.0625);
    public static final AxisAlignedBB TOP = new AxisAlignedBB(1 * 0.0625, 11.25 * 0.0625, 1 * 0.0625, 15 * 0.0625, 12.75 * 0.0625, 15 * 0.0625);
    public static final AxisAlignedBB BOWL = new AxisAlignedBB(0, 12.75 * 0.0625, 0, 1, 14.5 * 0.0625, 1);

    public BlockBirdBathWater(Material material, String id) {
        super(material);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        this.setCreativeTab(null);
        this.setTranslationKey(id);
        this.setRegistryName(id);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (placer instanceof EntityPlayer) {
            Triggers.trigger(Triggers.PLACE_OUTDOOR_FURNITURE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState) {
        List<AxisAlignedBB> list = getCollisionBoxList(this.getActualState(state, worldIn, pos));
        for (AxisAlignedBB box : list) {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state) {
        List<AxisAlignedBB> list = Lists.newArrayList();
        list.add(CENTER);
        list.add(BOTTOM_LIP);
        list.add(TOP);
        list.add(BOWL);
        return list;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(FurnitureBlocks.BIRD_BATH);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(FurnitureBlocks.BIRD_BATH);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        if (heldItem.getItem() == Items.BUCKET) {
            if (!worldIn.isRemote) {
                worldIn.setBlockState(pos, FurnitureBlocks.BIRD_BATH.getDefaultState());
                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                if (!playerIn.capabilities.isCreativeMode) {
                    heldItem.shrink(1);
                    if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.WATER_BUCKET))) {
                        playerIn.dropItem(new ItemStack(Items.WATER_BUCKET), false);
                    }
                }
            }
            return true;
        }

        return false;
    }
}