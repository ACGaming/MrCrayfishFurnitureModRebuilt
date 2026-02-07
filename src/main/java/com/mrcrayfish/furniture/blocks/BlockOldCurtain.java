package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.util.CollisionHelper;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public abstract class BlockOldCurtain extends BlockFurniture
{
    private static final AxisAlignedBB BOUNDING_BOX_NORTH = CollisionHelper.getBlockBounds(EnumFacing.NORTH, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_EAST  = CollisionHelper.getBlockBounds(EnumFacing.EAST,  0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_SOUTH = CollisionHelper.getBlockBounds(EnumFacing.SOUTH, 0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB BOUNDING_BOX_WEST  = CollisionHelper.getBlockBounds(EnumFacing.WEST,  0.875, 0.0, 0.0, 1.0, 1.0, 1.0);
    private static final AxisAlignedBB[] BOUNDING_BOX = {
            BOUNDING_BOX_SOUTH,
            BOUNDING_BOX_WEST,
            BOUNDING_BOX_NORTH,
            BOUNDING_BOX_EAST
    };

    public BlockOldCurtain(Material material, String id)
    {
        super(material, id);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);
        this.setLightOpacity(isOpen() ? 0 : 255);
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        if(placer instanceof EntityPlayer)
        {
            Triggers.trigger(Triggers.PLACE_BLINDS_OR_CURTAINS, (EntityPlayer) placer);
        }
        super.onBlockPlacedBy(world, pos, state, placer, stack);
    }

    @Override
    public boolean isTranslucent(IBlockState state)
    {
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()];
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
                                      AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                      Entity entityIn, boolean unknown)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes,
                BOUNDING_BOX[state.getValue(FACING).getHorizontalIndex()]);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state,
                                    EntityPlayer player, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(world.isRemote)
            return true;

        if(isOpen())
        {
            world.setBlockState(pos,
                    FurnitureBlocks.CURTAIN_CLOSED_OLD.getDefaultState()
                            .withProperty(FACING, state.getValue(FACING)), 3);
        }
        else
        {
            world.setBlockState(pos,
                    FurnitureBlocks.CURTAIN_OLD.getDefaultState()
                            .withProperty(FACING, state.getValue(FACING)), 3);
        }
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target,
                                  World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this);
    }

    public abstract boolean isOpen();

    @Override
    public boolean isTopSolid(IBlockState state)
    {
        return true;
    }

    private static final String[] COLORS = {"§c","§e","§a","§b","§d","§6"};

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(COLORS[(int)((System.currentTimeMillis() / 500) % COLORS.length)] + "1.7.10 Furniture");
    }
}
