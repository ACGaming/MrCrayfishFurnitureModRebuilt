package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.blocks.tv.Channels;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.items.ItemTVRemote;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageTVPlaySound;
import com.mrcrayfish.furniture.network.message.MessageTVStopSound;
import com.mrcrayfish.furniture.tileentity.TileEntityRetroTV;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRetroTV extends BlockFurnitureTile
{
    public static final PropertyInteger CHANNEL = PropertyInteger.create("channel", 0, 5);

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0, 0, 0, 1, 0.865, 1);

    public BlockRetroTV(Material material, String id)
    {
        super(material, id);
        this.setSoundType(SoundType.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CHANNEL, 0));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote && !(player.getHeldItem(hand).getItem() instanceof ItemTVRemote))
        {
            if(player.isSneaking())
            {
                PacketHandler.INSTANCE.sendToAllAround(new MessageTVStopSound(pos), new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 64));
                return true;
            }

            TileEntity tileEntity = world.getTileEntity(pos);
            if(tileEntity instanceof TileEntityRetroTV)
            {
                TileEntityRetroTV tileEntityRetroTV = (TileEntityRetroTV) tileEntity;
                tileEntityRetroTV.nextChannel();
                world.updateComparatorOutputLevel(pos, this);
                world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, FurnitureSounds.white_noise, SoundCategory.BLOCKS, 0.75F, 1.0F);
                PacketHandler.INSTANCE.sendToAllAround(new MessageTVPlaySound(pos, tileEntityRetroTV.getChannel()), new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 64));
            }
        }
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        super.onBlockHarvested(world, pos, state, player);
        if(!world.isRemote)
        {
            world.updateComparatorOutputLevel(pos, this);
            PacketHandler.INSTANCE.sendToAllAround(new MessageTVStopSound(pos), new TargetPoint(player.dimension, pos.getX(), pos.getY(), pos.getZ(), 64));
        }
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        TileEntityRetroTV tileEntityRetroTV = (TileEntityRetroTV) world.getTileEntity(pos);
        return super.getActualState(state, world, pos).withProperty(CHANNEL, tileEntityRetroTV.getChannel());
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityRetroTV();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING, CHANNEL);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta % 4));
    }

    @Override
    public boolean hasComparatorInputOverride(IBlockState state)
    {
        return true;
    }

    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos)
    {
        if(world.getTileEntity(pos) instanceof TileEntityRetroTV)
        {
            TileEntityRetroTV tv = (TileEntityRetroTV) world.getTileEntity(pos);
            return tv.getChannel() + 1;
        }
        return 0;
    }
}
