package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.init.FurnitureBlocks;

public class BlockLampOn extends BlockLamp
{
    public BlockLampOn(Material material, String id)
    {
        super(material, true, id);
        this.setLightLevel(1.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        ItemStack heldItem = player.getHeldItem(hand);
        if (heldItem.getItem() instanceof ItemDye)
        {
            world.setBlockState(pos, state.withProperty(COLOUR, 15 - heldItem.getItemDamage()).withProperty(FACING, state.getValue(FACING)));
            if (!player.capabilities.isCreativeMode)
            {
                heldItem.shrink(1);
            }
            return true;
        }

        world.setBlockState(pos, FurnitureBlocks.LAMP_OFF.getDefaultState().withProperty(COLOUR, state.getValue(COLOUR)).withProperty(FACING, state.getValue(FACING)), 3);
        return true;
    }
}
