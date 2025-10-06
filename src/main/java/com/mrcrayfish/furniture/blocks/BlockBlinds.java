package com.mrcrayfish.furniture.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.util.Bounds;

public class BlockBlinds extends BlockFurniture
{
    public static final PropertyBool LEFT = PropertyBool.create("left");
    private static final AxisAlignedBB[] BOUNDING_BOX = new Bounds(0.875, 0.0, 0.0, 1.0, 1.0, 1.0).getRotatedBounds();

    public BlockBlinds(Material material, boolean open, String id)
    {
        super(material, id);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LEFT, false));
        if(!open)
        {
            this.setLightOpacity(255);
            this.setCreativeTab(null);
        }
        else
        {
            this.setLightOpacity(0);
        }
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
        EnumFacing facing = state.getValue(FACING);
        return BOUNDING_BOX[facing.getHorizontalIndex()];
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        playerIn.playSound(SoundEvents.BLOCK_WOOD_PLACE, 1.0F, 1.0F);
        return worldIn.setBlockState(pos, getReplacementBlockFromState(state).getDefaultState().withProperty(FACING, state.getValue(FACING)));
    }

    private Block getReplacementBlockFromState(IBlockState state)
    {
        ResourceLocation blockRegistryName = state.getBlock().getRegistryName();
        String blockPath = blockRegistryName.getPath();
        if(blockPath.contains("_open"))
        {
            blockPath = blockPath.replace("_open", "_closed");
        }
        else if(blockPath.contains("_closed"))
        {
            blockPath = blockPath.replace("_closed", "_open");
        }
        ResourceLocation replacementBlockRegistryName = new ResourceLocation(blockRegistryName.getNamespace(), blockPath);
        return ForgeRegistries.BLOCKS.getValue(replacementBlockRegistryName);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return getItemFromState(state);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(getItemFromState(state));
    }

    private Item getItemFromState(IBlockState state)
    {
        ResourceLocation blockRegistryName = state.getBlock().getRegistryName();
        String itemName = blockRegistryName.getPath().replace("_closed", "_open");
        ResourceLocation itemRegistryName = new ResourceLocation(blockRegistryName.getNamespace(), itemName);
        return ForgeRegistries.ITEMS.getValue(itemRegistryName);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos)
    {
        EnumFacing facing = state.getValue(FACING);
        return state.withProperty(LEFT, worldIn.getBlockState(pos.offset(facing.rotateYCCW())).getBlock() instanceof BlockBlinds);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, LEFT);
    }
}
