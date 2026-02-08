package com.mrcrayfish.furniture.blocks;

import com.google.common.collect.Lists;
import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.tileentity.TileEntityCabinetKitchen;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
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

public class BlockOldCabinetKitchen extends BlockFurnitureTile
{
    public static final AxisAlignedBB[] COLLISION_BOXES = {new AxisAlignedBB(0, 0, 2 * 0.0625, 1, 1, 1), new AxisAlignedBB(0, 0, 0, 14 * 0.0625, 1, 1), new AxisAlignedBB(0, 0, 0, 1, 1, 14 * 0.0625), new AxisAlignedBB(2 * 0.0625, 0, 0, 1, 1, 1)};

    public BlockOldCabinetKitchen(Material material, String id)
    {
        super(material, id);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
                                      AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                      Entity entityIn, boolean isActualState)
    {
        for(AxisAlignedBB box : getCollisionBoxList(state))
        {
            addCollisionBoxToList(pos, entityBox, collidingBoxes, box);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand,
                                    EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if(!worldIn.isRemote)
        {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            if(tileEntity instanceof TileEntityCabinetKitchen)
            {
                playerIn.openGui(
                        MrCrayfishFurnitureMod.instance,
                        0,
                        worldIn,
                        pos.getX(),
                        pos.getY(),
                        pos.getZ()
                );
            }
        }
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityCabinetKitchen();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, FACING);
    }

    private List<AxisAlignedBB> getCollisionBoxList(IBlockState state)
    {
        List<AxisAlignedBB> list = Lists.newArrayList();
        EnumFacing facing = state.getValue(FACING);
        list.add(COLLISION_BOXES[facing.getHorizontalIndex()]);
        return list;
    }

    @Override
    public RayTraceResult collisionRayTrace(IBlockState state, World worldIn,
                                            BlockPos pos, Vec3d start, Vec3d end)
    {
        List<RayTraceResult> list = Lists.newArrayList();

        for(AxisAlignedBB box : getCollisionBoxList(state))
        {
            list.add(this.rayTrace(pos, start, end, box));
        }

        RayTraceResult result = null;
        double maxDistance = 0.0D;

        for(RayTraceResult ray : list)
        {
            if(ray != null)
            {
                double distance = ray.hitVec.squareDistanceTo(end);
                if(distance > maxDistance)
                {
                    result = ray;
                    maxDistance = distance;
                }
            }
        }

        return result;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos,
                             IBlockState state, @Nullable TileEntity tileEntity, ItemStack stack)
    {
        spawnAsEntity(worldIn, pos, new ItemStack(this));
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target,
                                  World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this);
    }

    @Override
    public void getSubBlocks(CreativeTabs item, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }

    private static final String[] COLORS = {"§c","§e","§a","§b","§d","§6"};

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(COLORS[(int)((System.currentTimeMillis() / 500) % COLORS.length)] + "1.7.10 Furniture");
    }
}
