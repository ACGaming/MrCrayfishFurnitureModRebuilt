package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.tileentity.TileEntityKitchenCounter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockOldCounterSink extends BlockFurnitureTile
{

    public BlockOldCounterSink(Material material, String id)
    {
        super(material, id);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(BlockBasin.FILLED, false));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        boolean result = FurnitureBlocks.BASIN.onBlockActivated(
                worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ
        );

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if (tileEntity != null)
        {
            tileEntity.validate();
            worldIn.setTileEntity(pos, tileEntity);
        }
        return result;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing,
                                            float hitX, float hitY, float hitZ,
                                            int meta, EntityLivingBase placer)
    {
        return this.getDefaultState()
                .withProperty(FACING, placer.getHorizontalFacing())
                .withProperty(BlockBasin.FILLED, false);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState()
                .withProperty(FACING, EnumFacing.byHorizontalIndex(meta))
                .withProperty(BlockBasin.FILLED, meta / 4 > 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex()
                + (state.getValue(BlockBasin.FILLED) ? 4 : 0);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, BlockBasin.FILLED);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
                                      AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                      Entity entityIn, boolean isActualState)
    {
        for (AxisAlignedBB box : getCollisionBoxList(state))
        {
            Block.addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);
        list.add(BlockCounter.COUNTER_TOP);
        list.add(BlockCounter.FORWARD_BOXES[facing.getHorizontalIndex()]);
        return list;
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World worldIn,
                                            BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> results = Lists.newArrayList();

        for (AxisAlignedBB box : getCollisionBoxList(state))
        {
            RayTraceResult hit = this.rayTrace(pos, start, end, box);
            if (hit != null)
            {
                results.add(hit);
            }
        }

        RayTraceResult result = null;
        double maxDistance = 0.0D;

        for (RayTraceResult hit : results)
        {
            double distance = hit.hitVec.squareDistanceTo(end);
            if (distance > maxDistance)
            {
                result = hit;
                maxDistance = distance;
            }
        }
        return result;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target,
                                  World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this);
    }

    private static final String[] COLORS = {"§c","§e","§a","§b","§d","§6"};

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(COLORS[(int)((System.currentTimeMillis() / 500) % COLORS.length)] + "1.7.10 Furniture");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityKitchenCounter();
    }
}
