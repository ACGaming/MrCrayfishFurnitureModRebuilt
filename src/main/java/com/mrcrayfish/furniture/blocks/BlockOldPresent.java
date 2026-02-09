package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.gui.inventory.ISimpleInventory;
import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import com.mrcrayfish.furniture.tileentity.TileEntityPresent;
import com.mrcrayfish.furniture.util.InventoryUtil;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class BlockOldPresent extends Block implements ITileEntityProvider
{

    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.35, 0.75);

    public BlockOldPresent(Material material, String id)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.CLOTH);
        this.hasTileEntity = true;
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
    public void onBlockHarvested(World world, BlockPos pos, IBlockState state, EntityPlayer player)
    {
        TileEntityPresent present = (TileEntityPresent) world.getTileEntity(pos);
        if(present != null)
        {
            world.playSound(
                    pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.ENTITY_PLAYER_LEVELUP,
                    SoundCategory.BLOCKS,
                    0.75F, 1.0F, false
            );

            if(world.isRemote && ConfigurationHandler.seasonalPresents)
            {
                player.sendMessage(
                        new TextComponentTranslation(
                                "cfm.message.present_christmas",
                                TextFormatting.RED + present.ownerName
                        )
                );
            }
            Triggers.trigger(Triggers.UNWRAP_PRESENT, player);
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state)
    {
        TileEntity tileEntity = world.getTileEntity(pos);

        if(tileEntity instanceof IInventory)
        {
            InventoryHelper.dropInventoryItems(world, pos, (IInventory) tileEntity);
        }
        if(tileEntity instanceof ISimpleInventory)
        {
            InventoryUtil.dropInventoryItems(world, pos, (ISimpleInventory) tileEntity);
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos,
                                      AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes,
                                      Entity entityIn, boolean unused)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return null;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta)
    {
        return new TileEntityPresent();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this);
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items)
    {
        items.add(new ItemStack(this));
    }

    private static final String[] COLORS = {"§c","§e","§a","§b","§d","§6"};

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        tooltip.add(COLORS[(int)((System.currentTimeMillis() / 500) % COLORS.length)] + "1.7.10 Furniture");
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state,
                                            BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }
}
