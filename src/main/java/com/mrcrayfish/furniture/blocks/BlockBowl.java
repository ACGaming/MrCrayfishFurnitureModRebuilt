package com.mrcrayfish.furniture.blocks;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.init.FurnitureSounds;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import java.util.List;
import java.util.Random;

public class BlockBowl extends Block
{
    private static final AxisAlignedBB BOUNDING_BOX = new AxisAlignedBB(0.125, 0.0, 0.125, 0.875, 0.315, 0.875);

    public BlockBowl(Material material, String id)
    {
        super(material);
        this.setHardness(0.5F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        this.setTranslationKey(id);
        this.setRegistryName(id);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BOUNDING_BOX;
    }

    @Override
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face)
    {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World world, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entity, boolean p_185477_7_)
    {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNDING_BOX);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        if (world.isRemote) return;

        AxisAlignedBB area = new AxisAlignedBB(pos).grow(1.5);

        if (this == FurnitureBlocks.BOWL_FOOD)
        {
            healDogs(world, pos, area, 4.0F, false);
        }
        else if (this == FurnitureBlocks.BOWL_CAT_FOOD)
        {
            healCats(world, pos, area, 4.0F, false);
        }
        else if (this == FurnitureBlocks.BOWL_WATER)
        {
            healDogs(world, pos, area, 2.0F, true);
            healCats(world, pos, area, 2.0F, true);
        }

        world.scheduleUpdate(pos, this, 20);
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        if (!world.isRemote)
        {
            world.scheduleUpdate(pos, this, 1);
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        if (this == FurnitureBlocks.BOWL)
        {
            ItemStack held = player.getHeldItem(hand);
            if (!held.isEmpty())
            {
                if (held.getItem() == FurnitureItems.DOG_FOOD)
                {
                    world.setBlockState(pos, FurnitureBlocks.BOWL_FOOD.getDefaultState());
                    world.playSound(null, pos, FurnitureSounds.bowl_fill, SoundCategory.BLOCKS, 1F, 1F);
                    exchangeItem(player, held, FurnitureItems.CONSERVE_CAN);
                    return true;
                }
                if (held.getItem() == FurnitureItems.CAT_FOOD)
                {
                    world.setBlockState(pos, FurnitureBlocks.BOWL_CAT_FOOD.getDefaultState());
                    world.playSound(null, pos, FurnitureSounds.bowl_fill, SoundCategory.BLOCKS, 1F, 1F);
                    exchangeItem(player, held, FurnitureItems.CONSERVE_CAN);
                    return true;
                }
                if (held.getItem() == Items.WATER_BUCKET)
                {
                    world.setBlockState(pos, FurnitureBlocks.BOWL_WATER.getDefaultState());
                    world.playSound(null, pos, SoundEvents.ITEM_BOTTLE_FILL, SoundCategory.BLOCKS, 1F, 1F);
                    exchangeItem(player, held, Items.BUCKET);
                    return true;
                }
            }
        }
        return false;
    }

    private void healDogs(World world, BlockPos pos, AxisAlignedBB area, float amount, boolean drink)
    {
        if (this == FurnitureBlocks.BOWL) return;
        List<EntityWolf> wolves = world.getEntitiesWithinAABB(EntityWolf.class, area);
        for (EntityWolf wolf : wolves)
        {
            if (wolf.getHealth() < wolf.getMaxHealth())
            {
                wolf.heal(amount);
                world.playSound(null, wolf.getPosition(), drink ? SoundEvents.ENTITY_GENERIC_DRINK : SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 0.8F);
                world.setBlockState(pos, FurnitureBlocks.BOWL.getDefaultState());
            }
        }
    }

    private void healCats(World world, BlockPos pos, AxisAlignedBB area, float amount, boolean drink)
    {
        if (this == FurnitureBlocks.BOWL) return;
        List<EntityOcelot> cats = world.getEntitiesWithinAABB(EntityOcelot.class, area);
        for (EntityOcelot cat : cats)
        {
            if (cat.getHealth() < cat.getMaxHealth())
            {
                cat.heal(amount);
                world.playSound(null, cat.getPosition(), drink ? SoundEvents.ENTITY_GENERIC_DRINK : SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 0.8F);
                world.setBlockState(pos, FurnitureBlocks.BOWL.getDefaultState());
            }
        }
    }

    private void exchangeItem(EntityPlayer player, ItemStack held, Item itemNew)
    {
        if (!player.isCreative())
        {
            held.shrink(1);
            if (!player.inventory.addItemStackToInventory(new ItemStack(itemNew)))
            {
                player.dropItem(new ItemStack(itemNew), false);
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced)
    {
        if(this != FurnitureBlocks.BOWL) return;
        if(GuiScreen.isShiftKeyDown())
        {
            String info = I18n.format("cfm.bowl.info");
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(info, 150));
        }
        else
        {
            tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.info"));
        }
    }
}
