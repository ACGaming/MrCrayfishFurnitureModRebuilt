package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BlockCatTree extends BlockFurniture
{
    private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D);
    private static final AxisAlignedBB POST_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.125D, 0.625D, 0.0625D);
    private static final AxisAlignedBB POST_SW = new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 0.0625D, 0.625D, 0.75D);
    private static final AxisAlignedBB POST_SE = new AxisAlignedBB(0.0625D, 0.0D, 0.6875D, 0.6875D, 0.625D, 0.75D);
    private static final AxisAlignedBB POST_NE = new AxisAlignedBB(0.5625D, 0.0D, 0.0D, 0.6875D, 0.625D, 0.0625D);
    private static final AxisAlignedBB POST_CORE_SW = new AxisAlignedBB(0.625D, 0.0D, 0.0625D, 0.6875D, 0.625D, 0.6875D);
    private static final AxisAlignedBB LOWER_PLATFORM_MAIN = new AxisAlignedBB(0.0625D, 0.0625D, 0.0625D, 0.625D, 0.09375D, 0.6875D);
    private static final AxisAlignedBB LOWER_PLATFORM_N = new AxisAlignedBB(0.6875D, 0.0625D, 0.0D, 1.0D, 0.09375D, 0.3125D);
    private static final AxisAlignedBB LOWER_PLATFORM_N_SMALL = new AxisAlignedBB(0.125D, 0.0625D, 0.0D, 0.5625D, 0.09375D, 0.0625D);
    private static final AxisAlignedBB LOWER_PLATFORM_S_PART1 = new AxisAlignedBB(0.6875D, 0.0625D, 0.5D, 1.0D, 0.09375D, 1.0D);
    private static final AxisAlignedBB LOWER_PLATFORM_S_PART2 = new AxisAlignedBB(0.0D, 0.0625D, 0.75D, 0.6875D, 0.09375D, 1.0D);
    private static final AxisAlignedBB UPPER_POST = new AxisAlignedBB(0.75D, 0.0625D, 0.3125D, 0.9375D, 1.0625D, 0.5D);
    private static final AxisAlignedBB TOP_PLATFORM = new AxisAlignedBB(0.5D, 1.0625D, 0.0625D, 1.1875D, 1.125D, 0.75D);
    private static final AxisAlignedBB TOP_RIM_N = new AxisAlignedBB(0.5D, 1.125D, 0.0625D, 1.1875D, 1.1875D, 0.125D);
    private static final AxisAlignedBB TOP_RIM_S = new AxisAlignedBB(0.5D, 1.125D, 0.6875D, 1.1875D, 1.1875D, 0.75D);
    private static final AxisAlignedBB TOP_RIM_W = new AxisAlignedBB(0.5D, 1.125D, 0.125D, 0.5625D, 1.1875D, 0.6875D);
    private static final AxisAlignedBB TOP_RIM_E = new AxisAlignedBB(1.125D, 1.125D, 0.125D, 1.1875D, 1.1875D, 0.6875D);
    private static final AxisAlignedBB TOP_RIM_INNER = new AxisAlignedBB(0.5625D, 1.125D, 0.125D, 1.125D, 1.15625D, 0.6875D);

    private static final AxisAlignedBB BOUNDING_BOX_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.1875D, 1.1875D, 1.0D);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.1875D, 1.1875D);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = new AxisAlignedBB(-0.1875D, 0.0D, 0.0D, 1.0D, 1.1875D, 1.0D);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = new AxisAlignedBB(0.0D, 0.0D, -0.1875D, 1.0D, 1.1875D, 1.0D);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockCatTree(Material material, String id)
    {
        super(material, id);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, BASE);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_NW);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_SW);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_SE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_NE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, POST_CORE_SW);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LOWER_PLATFORM_MAIN);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LOWER_PLATFORM_N);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LOWER_PLATFORM_N_SMALL);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LOWER_PLATFORM_S_PART1);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, LOWER_PLATFORM_S_PART2);

        AxisAlignedBB upperPost = getRotatedAABB(UPPER_POST, facing);
        AxisAlignedBB topPlatform = getRotatedAABB(TOP_PLATFORM, facing);
        AxisAlignedBB topRimN = getRotatedAABB(TOP_RIM_N, facing);
        AxisAlignedBB topRimS = getRotatedAABB(TOP_RIM_S, facing);
        AxisAlignedBB topRimW = getRotatedAABB(TOP_RIM_W, facing);
        AxisAlignedBB topRimE = getRotatedAABB(TOP_RIM_E, facing);
        AxisAlignedBB topRimInner = getRotatedAABB(TOP_RIM_INNER, facing);

        addCollisionBoxToList(pos, entityBox, collidingBoxes, upperPost);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, topPlatform);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, topRimN);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, topRimS);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, topRimW);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, topRimE);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, topRimInner);
    }

    private AxisAlignedBB getRotatedAABB(AxisAlignedBB aabb, EnumFacing facing)
    {
        double minX = aabb.minX - 0.5;
        double minZ = aabb.minZ - 0.5;
        double maxX = aabb.maxX - 0.5;
        double maxZ = aabb.maxZ - 0.5;

        double newMinX;
        double newMinZ;
        double newMaxX;
        double newMaxZ;

        switch (facing)
        {
            case NORTH:
                newMinX = minX;
                newMinZ = minZ;
                newMaxX = maxX;
                newMaxZ = maxZ;
                break;
            case EAST:
                newMinX = -minZ;
                newMinZ = minX;
                newMaxX = -maxZ;
                newMaxZ = maxX;
                break;
            case SOUTH:
                newMinX = -maxX;
                newMinZ = -maxZ;
                newMaxX = -minX;
                newMaxZ = -minZ;
                break;
            case WEST:
                newMinX = minZ;
                newMinZ = -maxX;
                newMaxX = maxZ;
                newMaxZ = -minX;
                break;
            default:
                return aabb;
        }

        return new AxisAlignedBB(newMinX + 0.5, aabb.minY, newMinZ + 0.5, newMaxX + 0.5, aabb.maxY, newMaxZ + 0.5);
    }
}
