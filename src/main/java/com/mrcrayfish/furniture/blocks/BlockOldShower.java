package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;
import java.util.Random;

public class BlockOldShower extends BlockFurniture
{
    private static final AxisAlignedBB NOTHING = new AxisAlignedBB(0.0, 0.0, 0.0, 0.0, 0.0, 0.0);

    private static final AxisAlignedBB SIDE_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB SIDE_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB SIDE_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB SIDE_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 0.9375, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOTTOM = new AxisAlignedBB(0, 0, 0, 1, 0.0625, 1);

    public static final AxisAlignedBB TOP_BOUNDING_BOX = new AxisAlignedBB(0, -1, 0, 1, 1, 1);
    public static final AxisAlignedBB BOTTOM_BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 2, 1);

    public BlockOldShower(Material material, boolean top, String id)
    {
        super(material, id);
        this.setHardness(1.0F);
        this.setSoundType(SoundType.STONE);
        if(top) this.setCreativeTab(null);
    }

    @Override
    public boolean canPlaceBlockAt(World world, BlockPos pos)
    {
        return super.canPlaceBlockAt(world, pos) && super.canPlaceBlockAt(world, pos.up());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        world.setBlockState(pos.up(), FurnitureBlocks.SHOWER_TOP_OLD.getDefaultState().withProperty(FACING, state.getValue(FACING)), 2);
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BATHROOM_FURNITURE, (EntityPlayer) placer);
        }
    }

    @Override
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        world.destroyBlock(this == FurnitureBlocks.SHOWER_BOTTOM_OLD ? pos.up() : pos.down(), false);
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            boolean top = this == FurnitureBlocks.SHOWER_TOP_OLD;

            IBlockState head = world.getBlockState(pos.up(top ? 1 : 2));
            if(head.getBlock() == FurnitureBlocks.SHOWER_HEAD_ON)
            {
                Triggers.trigger(Triggers.PLAYER_SHOWER, player);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return state.getBlock() == FurnitureBlocks.SHOWER_TOP_OLD ? TOP_BOUNDING_BOX : BOTTOM_BOUNDING_BOX;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return new ItemStack(FurnitureBlocks.SHOWER_BOTTOM_OLD).getItem();
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(FurnitureBlocks.SHOWER_BOTTOM_OLD);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state, World world, BlockPos pos)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);

        if(state.getBlock() == FurnitureBlocks.SHOWER_BOTTOM_OLD)
        {
            list.add(BOTTOM);
        }
        if(facing != EnumFacing.NORTH)
        {
            list.add(SIDE_SOUTH);
        }
        if(facing != EnumFacing.SOUTH)
        {
            list.add(SIDE_NORTH);
        }
        if(facing != EnumFacing.EAST)
        {
            list.add(SIDE_WEST);
        }
        if(facing != EnumFacing.WEST)
        {
            list.add(SIDE_EAST);
        }

        return list;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        List<AxisAlignedBB> boxes = this.getCollisionBoxList(this.getActualState(state, worldIn, pos), worldIn, pos);
        for(AxisAlignedBB box : boxes)
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState blockState, World world, BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, world, pos), world, pos))
        {
            list.add(this.rayTrace(pos, start, end, axisalignedbb));
        }

        RayTraceResult raytraceresult1 = null;
        double d1 = 0.0D;

        for(RayTraceResult raytraceresult : list)
        {
            if(raytraceresult != null)
            {
                double d0 = raytraceresult.hitVec.squareDistanceTo(end);

                if(d0 > d1)
                {
                    raytraceresult1 = raytraceresult;
                    d1 = d0;
                }
            }
        }

        return raytraceresult1;
    }
}
