package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import com.mrcrayfish.furniture.util.TileEntityUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
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

public class BlockPlate extends Block implements ITileEntityProvider {
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(3 * 0.0625, 0.0, 3 * 0.0625, 13 * 0.0625, 0.125, 13 * 0.0625);

    public BlockPlate(Material material, String id) {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.GLASS);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setTranslationKey(id);
        this.setRegistryName(id);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity instanceof TileEntityPlate) {
            TileEntityPlate tileEntityPlate = (TileEntityPlate) tileEntity;
            if (!heldItem.isEmpty() && tileEntityPlate.getFood().isEmpty()) {
                if (heldItem.getItem() instanceof ItemFood) {
                    tileEntityPlate.setFood(new ItemStack(heldItem.getItem(), 1, heldItem.getItemDamage()));
                    tileEntityPlate.setRotation(playerIn.getHorizontalFacing().getHorizontalIndex());
                    TileEntityUtil.markBlockForUpdate(worldIn, pos);
                    heldItem.shrink(1);
                    return true;
                }
            }
            if (!tileEntityPlate.getFood().isEmpty()) {
                if (!worldIn.isRemote) {
                    EntityItem entityFood = new EntityItem(worldIn, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, tileEntityPlate.getFood());
                    worldIn.spawnEntity(entityFood);
                }
                tileEntityPlate.setFood(ItemStack.EMPTY);
                TileEntityUtil.markBlockForUpdate(worldIn, pos);
            }
        }
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityPlate();
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public boolean canPlaceBlockOnSide(World world, BlockPos pos, EnumFacing side) {
        return side == EnumFacing.UP && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity tile = world.getTileEntity(pos);
        if (tile instanceof TileEntityPlate) {
            TileEntityPlate plate = (TileEntityPlate) tile;
            ItemStack food = plate.getFood();
            if (!food.isEmpty()) {
                world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, food.copy()));
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (!world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP) && !world.isRemote) {

            TileEntity tile = world.getTileEntity(pos);
            ItemStack food = ItemStack.EMPTY;

            if (tile instanceof TileEntityPlate) {
                TileEntityPlate plate = (TileEntityPlate) tile;
                food = plate.getFood();
                if (!food.isEmpty()) {
                    world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.4, pos.getZ() + 0.5, food.copy()));
                    plate.setFood(ItemStack.EMPTY);
                }
            }

            world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, new ItemStack(Item.getItemFromBlock(this))));

            world.playSound(null, pos, SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1.2F, 1F);

            world.setBlockToAir(pos);
        }
    }
}
