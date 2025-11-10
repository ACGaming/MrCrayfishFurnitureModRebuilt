package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockBowl extends Block implements ITileEntityProvider
{
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.315, 0.875);

    public BlockBowl(Material material, String id)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setTranslationKey(id);
        this.setRegistryName(id);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPlate();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack held = playerIn.getHeldItem(hand);

        if (!held.isEmpty()) {
            if (held.getItem() == FurnitureItems.DOG_FOOD) {
                worldIn.setBlockState(pos, FurnitureBlocks.BOWL_FOOD.getDefaultState());
                held.shrink(1);
                if (!playerIn.inventory.addItemStackToInventory(new ItemStack(FurnitureItems.CONSERVE_CAN))) {
                    playerIn.dropItem(new ItemStack(FurnitureItems.CONSERVE_CAN), false);
                }
                return true;
            }

            if (held.getItem() == Items.WATER_BUCKET) {
                worldIn.setBlockState(pos, FurnitureBlocks.BOWL_WATER.getDefaultState());
                held.shrink(1);
                if (!playerIn.inventory.addItemStackToInventory(new ItemStack(Items.BUCKET))) {
                    playerIn.dropItem(new ItemStack(Items.BUCKET), false);
                }
                return true;
            }
        }

        return false;
    }
}
