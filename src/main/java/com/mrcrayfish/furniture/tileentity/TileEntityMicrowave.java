package com.mrcrayfish.furniture.tileentity;

import com.mrcrayfish.furniture.advancement.Triggers;
import com.mrcrayfish.furniture.api.RecipeAPI;
import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.blocks.BlockMicrowave;
import com.mrcrayfish.furniture.gui.containers.ContainerMicrowave;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.util.ParticleSpawner;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;

import java.util.Random;

public class TileEntityMicrowave extends TileEntityFurniture implements ISidedInventory, ITickable
{
    private Random rand = new Random();

    private static final int[] slot = new int[]{0};

    private boolean cooking = false;
    public int progress = 0;
    private int timer = 0;

    public TileEntityMicrowave()
    {
        super("microwave", 1);
    }

    public ItemStack getItem()
    {
        return getStackInSlot(0);
    }

    public void startCooking()
    {
        if(!getItem().isEmpty())
        {
            RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(getItem());
            if(data != null)
            {
                cooking = true;
                world.updateComparatorOutputLevel(pos, blockType);
            }
        }
    }

    public void stopCooking()
    {
        timer = 0;
        progress = 0;
        cooking = false;
        world.updateComparatorOutputLevel(pos, blockType);
    }

    public boolean isCooking()
    {
        return cooking;
    }

    @Override
    public void update()
    {
        if(isCooking())
        {
            if(this.world.isRemote)
            {
                double posX = pos.getX() + 0.35D + (rand.nextDouble() / 3);
                double posZ = pos.getZ() + 0.35D + (rand.nextDouble() / 3);
                ParticleSpawner.spawnParticle("smoke", posX, pos.getY() + 0.065D, posZ);
            }

            progress++;
            if(progress >= 35 && getItem().getItem() instanceof ItemEgg)
            {
                if(this.world.isRemote)
                {
                    for(int i = 0; i < 3; i++)
                    {
                        double posX = pos.getX() + 0.35D + (rand.nextDouble() / 3);
                        double posZ = pos.getZ() + 0.35D + (rand.nextDouble() / 3);
                        world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, posX, pos.getY() + 0.065D, posZ, 0, 0, 0);
                    }
                }
                world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.BLOCKS, 0.5F, 1.5F, false);
                world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.ENTITY_SLIME_DEATH, SoundCategory.BLOCKS, 1.0F, 1.5F, false);
                Triggers.trigger(Triggers.EGG_MICROWAVE, world.getClosestPlayer(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, 5.0D, false));
                stopCooking();
                setInventorySlotContents(0, ItemStack.EMPTY);
                world.setBlockState(pos, world.getBlockState(pos).withProperty(BlockMicrowave.DIRTY, true));
            }
            else if(progress >= 40)
            {
                if(!getItem().isEmpty())
                {
                    RecipeData data = RecipeAPI.getMicrowaveRecipeFromIngredients(getItem());
                    if(data != null)
                    {
                        this.setInventorySlotContents(0, data.getOutput().copy());
                    }
                }
                if(world.isRemote)
                {
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.microwave_finish, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
                }
                stopCooking();
            }
            else
            {
                if(timer == 20)
                {
                    timer = 0;
                }
                if(timer == 0)
                {
                    world.playSound(pos.getX(), pos.getY(), pos.getZ(), FurnitureSounds.microwave_running, SoundCategory.BLOCKS, 0.75F, 1.0F, true);
                }
                timer++;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound tagCompound)
    {
        super.readFromNBT(tagCompound);
        this.cooking = tagCompound.getBoolean("Coooking");
        this.progress = tagCompound.getInteger("Progress");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tagCompound)
    {
        super.writeToNBT(tagCompound);
        tagCompound.setBoolean("Coooking", cooking);
        tagCompound.setInteger("Progress", progress);
        return tagCompound;
    }

    @Override
    public int getInventoryStackLimit()
    {
        return 1;
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack)
    {
        return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) != null;
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side)
    {
        return slot;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction)
    {
        return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) != null;
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction)
    {
        return RecipeAPI.getMicrowaveRecipeFromIngredients(stack) == null;
    }

    @Override
    public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn)
    {
        this.fillWithLoot(playerIn);
        return new ContainerMicrowave(playerInventory, this);
    }
}
