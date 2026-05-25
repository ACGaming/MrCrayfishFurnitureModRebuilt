package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.tileentity.TileEntityModernComputer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class BlockModernComputer extends BlockFurnitureTile
{
    public BlockModernComputer(Material material, String id)
    {
        super(material, id);
        this.setHardness(1.5F);
        this.setLightLevel(0.5F);
        this.setSoundType(SoundType.ANVIL);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        TileEntity tile_entity = worldIn.getTileEntity(pos);

        if(tile_entity instanceof TileEntityModernComputer)
        {
            TileEntityModernComputer computer = (TileEntityModernComputer) tile_entity;
            if(!computer.isTrading())
            {
                computer.setTrading(true);
                playerIn.openGui(MrCrayfishFurnitureMod.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());

                worldIn.playSound(null, pos, FurnitureSounds.computer, SoundCategory.BLOCKS, 1F, 1F);
            }
            else
            {
                if(!worldIn.isRemote)
                {
                    playerIn.sendMessage(new TextComponentTranslation("cfm.message.computer"));
                }
            }
        }
        return true;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);
        switch(facing) {
            case NORTH: return new AxisAlignedBB(0D, 0D, 0D, 1.6875D, 1D, 1D);
            case SOUTH: return new AxisAlignedBB(-0.6875D, 0D, 0D, 1D, 1D, 1D);
            case WEST:  return new AxisAlignedBB(0D, 0D, -0.6875D, 1D, 1D, 1D);
            case EAST:  return new AxisAlignedBB(0D, 0D, 0D, 1D, 1D, 1.6875D);
            default:    return new AxisAlignedBB(0D, 0D, 0D, 1.6875D, 1D, 1D);
        }
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean isActualState)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getCollisionBoundingBox(worldIn, pos));
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityModernComputer();
    }
}
