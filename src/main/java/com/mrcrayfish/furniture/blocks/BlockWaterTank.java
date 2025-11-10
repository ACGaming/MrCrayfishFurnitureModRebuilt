package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class BlockWaterTank extends Block implements ITileEntityProvider
{
    private static final AxisAlignedBB BASE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.2, 1.0);
    private static final AxisAlignedBB SIDE_NORTH = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.3, 0.1);
    private static final AxisAlignedBB SIDE_SOUTH = new AxisAlignedBB(0.0, 0.0, 0.9, 1.0, 1.3, 1.0);
    private static final AxisAlignedBB SIDE_WEST  = new AxisAlignedBB(0.0, 0.0, 0.0, 0.1, 1.3, 1.0);
    private static final AxisAlignedBB SIDE_EAST  = new AxisAlignedBB(0.9, 0.0, 0.0, 1.0, 1.3, 1.0);
    private static final AxisAlignedBB TANK_OUTLINE = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 1.3, 1.0);
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BlockWaterTank(Material material, String id)
    {
        super(material);
        this.setHardness(1F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
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
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("§6This is a Work in Progress... You can't use it");
        super.addInformation(stack, worldIn, tooltip, flagIn);
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
}
