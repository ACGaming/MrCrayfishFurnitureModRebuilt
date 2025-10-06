package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.tileentity.TileEntityMicrowave;
import com.mrcrayfish.furniture.util.CollisionHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockMicrowave extends BlockFurnitureTile
{
    public static final PropertyBool DIRTY = PropertyBool.create("dirty");
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);

    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 2 * 0.0625, 0.0, 0 * 0.0625, 14 * 0.0625, 9 * 0.0625, 16 * 0.0625);
    private static final AxisAlignedBB BOUNDING_BOX_EAST = CollisionHelper.getBlockBounds(EnumFacing.EAST, 2 * 0.0625, 0.0, 0 * 0.0625, 14 * 0.0625, 9 * 0.0625, 16 * 0.0625);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 2 * 0.0625, 0.0, 0 * 0.0625, 14 * 0.0625, 9 * 0.0625, 16 * 0.0625);
    private static final AxisAlignedBB BOUNDING_BOX_WEST = CollisionHelper.getBlockBounds(EnumFacing.WEST, 2 * 0.0625, 0.0, 0 * 0.0625, 14 * 0.0625, 9 * 0.0625, 16 * 0.0625);
    private static final AxisAlignedBB[] BOUNDING_BOX = {BOUNDING_BOX_SOUTH, BOUNDING_BOX_WEST, BOUNDING_BOX_NORTH, BOUNDING_BOX_EAST};

    public BlockMicrowave(Material material, String id)
    {
        super(material, id);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.ANVIL);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(DIRTY, false));
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_APPLIANCE, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
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
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(state.getValue(DIRTY))
        {
            worldIn.playSound(null, pos, SoundEvents.ENTITY_SLIME_SQUISH, SoundCategory.BLOCKS, 1.0F, 1.5F);
            worldIn.setBlockState(pos, state.withProperty(DIRTY, false));
            return true;
        }
        if(!worldIn.isRemote)
        {
            TileEntity tile_entity = worldIn.getTileEntity(pos);

            if(tile_entity instanceof TileEntityMicrowave)
            {
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_)
    {
        EnumFacing facing = state.getValue(FACING);
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX[facing.getHorizontalIndex()]);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityMicrowave();
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        TileEntityMicrowave microwave = (TileEntityMicrowave) world.getTileEntity(pos);
        return microwave.isCooking() ? 1 : 0;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing()).withProperty(DIRTY, false);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, DIRTY);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return (state.getValue(FACING).getHorizontalIndex() << 1) | (state.getValue(DIRTY) ? 1 : 0);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return getDefaultState()
            .withProperty(FACING, EnumFacing.byHorizontalIndex(meta >> 1))
            .withProperty(DIRTY, (meta & 1) == 1);
    }
}
