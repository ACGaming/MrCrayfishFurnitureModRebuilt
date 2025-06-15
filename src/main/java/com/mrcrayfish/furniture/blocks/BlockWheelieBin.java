package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.entity.EntitySeat;
import com.mrcrayfish.furniture.tileentity.TileEntityBin;
import java.util.List;

// Made by Groobler
public class BlockWheelieBin extends BlockFurnitureTile
{
    public BlockWheelieBin(Material material, String id)
    {
        super(material, id);
        this.setSoundType(SoundType.STONE);
        this.setHardness(1.0F);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!world.isRemote)
        {
            TileEntity tile_entity = world.getTileEntity(pos);

            if(tile_entity instanceof TileEntityBin)
            {
                player.openGui(MrCrayfishFurnitureMod.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta)
    {
        return new TileEntityBin();
    }
}
