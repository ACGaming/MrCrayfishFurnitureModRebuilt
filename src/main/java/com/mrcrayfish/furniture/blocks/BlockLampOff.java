package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;

public class BlockLampOff extends BlockFurniture
{
    public BlockLampOff(Material material, String id)
    {
        super(material, id);
        this.setHardness(0.75F);
        this.setSoundType(SoundType.CLOTH);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
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
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos)
    {
        if (world.isBlockPowered(pos))
        {
            world.setBlockState(pos, FurnitureBlocks.LAMP_ON.getDefaultState(), 2);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (world.isBlockPowered(pos) || world.getBlockState(pos.down()).getBlock() instanceof BlockBedsideCabinet)
        {
            world.setBlockState(pos, FurnitureBlocks.LAMP_ON.getDefaultState(), 2);
            world.notifyNeighborsOfStateChange(pos.down(), this, true);
        }
        else if (!world.isRemote)
        {
            player.sendMessage(new TextComponentTranslation("cfm.message.lamp"));
        }
        return true;
    }
}
