package com.mrcrayfish.furniture.items;

import javax.annotation.Nullable;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemBottle extends Item
{
    private boolean hasLiquid;

    public ItemBottle(boolean hasLiquid)
    {
        this.hasLiquid = hasLiquid;
        if(hasLiquid)
        {
            setMaxStackSize(1);
        }
        else
        {
            setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
        }
    }

    @Override
    public String getItemStackDisplayName(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            if(nbt.hasKey("Name", 8))
            {
                return nbt.getString("Name") + " " + super.getItemStackDisplayName(stack);
            }
        }
        return super.getItemStackDisplayName(stack);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack bottle, World world, EntityLivingBase entity)
    {
        if(entity instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) entity;
            if(hasLiquid)
            {
                NBTTagCompound nbt = bottle.getTagCompound();
                int remainingServings = nbt.getInteger("Servings");
                if(remainingServings > 0)
                {
                    int heal = nbt.getInteger("HealAmount");
                    player.getFoodStats().addStats(heal, 0.5F);
                    remainingServings--;
                    nbt.setInteger("Servings", remainingServings);
                    if(remainingServings == 0)
                    {
                        return new ItemStack(FurnitureItems.BOTTLE);
                    }
                }
            }
        }
        return bottle;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack bottle)
    {
        return 32;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack)
    {
        return EnumAction.DRINK;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand hand)
    {
        if(hasLiquid)
        {
            if(playerIn.getFoodStats().needFood())
            {
                playerIn.setActiveHand(hand);
                return new ActionResult<>(EnumActionResult.SUCCESS, playerIn.getHeldItem(hand));
            }
        }
        return new ActionResult<>(EnumActionResult.FAIL, playerIn.getHeldItem(hand));
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        Block block = iblockstate.getBlock();

        if(!block.isReplaceable(worldIn, pos))
        {
            pos = pos.offset(facing);
        }

        ItemStack itemstack = player.getHeldItem(hand);

        if(!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(FurnitureBlocks.BOTTLE, pos, false, facing, null))
        {
            int i = this.getMetadata(itemstack.getMetadata());
            IBlockState iblockstate1 = FurnitureBlocks.BOTTLE.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

            if(placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
            {
                SoundType soundtype = worldIn.getBlockState(pos).getBlock().getSoundType(worldIn.getBlockState(pos), worldIn, pos, player);
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                itemstack.shrink(1);
            }

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.FAIL;
        }
    }

    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if(!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if(state.getBlock() == FurnitureBlocks.BOTTLE)
        {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            FurnitureBlocks.BOTTLE.onBlockPlacedBy(world, pos, state, player, stack);
        }

        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if(stack.hasTagCompound())
        {
            NBTTagCompound nbt = stack.getTagCompound();
            tooltip.add(nbt.getInteger("Servings") + "/6");
        }
    }
}
