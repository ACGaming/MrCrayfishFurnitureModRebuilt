package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityPlate;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BlockWaterTankWater extends Block implements ITileEntityProvider {
    private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0);
    private static final AxisAlignedBB SIDE_NORTH = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.3, 0.1);
    private static final AxisAlignedBB SIDE_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.9, 1.0, 1.3, 1.0);
    private static final AxisAlignedBB SIDE_WEST = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1, 1.3, 1.0);
    private static final AxisAlignedBB SIDE_EAST = new AxisAlignedBB(0.9, 0.0, 0.0, 1.0, 1.3, 1.0);
    private static final AxisAlignedBB TANK_OUTLINE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.3, 1.0);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockWaterTankWater(Material material, String id) {
        super(material);
        this.setHardness(1F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        this.setCreativeTab(null);
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
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_NORTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_SOUTH);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_WEST);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, SIDE_EAST);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return TANK_OUTLINE;
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
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta % 4));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);

    }

    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ, int meta,
                                            EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public List<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        List<ItemStack> drops = new ArrayList<>();
        drops.add(new ItemStack(FurnitureBlocks.WATER_TANK));
        return drops;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(FurnitureBlocks.WATER_TANK);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = playerIn.getHeldItem(hand);

        if (heldItem.getItem() == Items.WATER_BUCKET) {
            if (!worldIn.isRemote) {
                EnumFacing blockFacing = state.getValue(BlockHorizontal.FACING);
                worldIn.setBlockState(pos, FurnitureBlocks.WATER_TANK_WATER_FULL.getDefaultState().withProperty(BlockHorizontal.FACING, blockFacing));

                if (!playerIn.capabilities.isCreativeMode)
                    playerIn.setHeldItem(hand, new ItemStack(Items.BUCKET));

                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
            }
            return true;
        }

        if (heldItem.getItem() == Items.BUCKET) {
            if (!worldIn.isRemote) {
                EnumFacing blockFacing = state.getValue(BlockHorizontal.FACING);
                worldIn.setBlockState(pos, FurnitureBlocks.WATER_TANK.getDefaultState().withProperty(BlockHorizontal.FACING, blockFacing));

                if (!playerIn.capabilities.isCreativeMode)
                    playerIn.setHeldItem(hand, new ItemStack(Items.WATER_BUCKET));

                worldIn.playSound(null, pos, SoundEvents.ITEM_BUCKET_FILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                if (worldIn.isRemote) {
                    EnumFacing Facing = state.getValue(FACING);

                    double x = pos.getX();
                    double y = pos.getY() + 0.2;
                    double z = pos.getZ();

                    double frontOffset = 0.7;
                    double sideOffset  = 0.1;

                    double px = 0, pz = 0;
                    switch(Facing) {
                        case NORTH: px = 0.5; pz = 0.5 - frontOffset; break;
                        case SOUTH: px = 0.5; pz = 0.5 + frontOffset; break;
                        case WEST:  px = 0.5 - frontOffset; pz = 0.5; break;
                        case EAST:  px = 0.5 + frontOffset; pz = 0.5; break;
                    }

                    for (int i = 0; i < 5; i++) {
                        double particleX = x + px + (worldIn.rand.nextDouble() - 0.5) * sideOffset * 2;
                        double particleZ = z + pz + (worldIn.rand.nextDouble() - 0.5) * sideOffset * 2;

                        worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER,
                                particleX, y, particleZ,
                                0, -0.05, 0); // It's too hard to fix that, ACGaming please help me
                    }
                }
            }
            return true;
        }
        return false;
    }
}
