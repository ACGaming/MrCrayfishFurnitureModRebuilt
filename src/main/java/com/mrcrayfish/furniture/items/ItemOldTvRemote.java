package com.mrcrayfish.furniture.items;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import com.mrcrayfish.furniture.init.FurnitureSounds;
import com.mrcrayfish.furniture.network.PacketHandler;
import com.mrcrayfish.furniture.network.message.MessageTVPlaySound;
import com.mrcrayfish.furniture.tileentity.TileEntityRetroTV;
import com.mrcrayfish.furniture.tileentity.TileEntityTV;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Author: MrCrayfish and edited by MisterIceCat
 */
public class ItemOldTvRemote extends Item {
    public ItemOldTvRemote() {
        this.setTranslationKey("old_tv_remote");
        this.setRegistryName("old_tv_remote");
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (GuiScreen.isShiftKeyDown()) {
            tooltip.addAll(Minecraft.getMinecraft().fontRenderer.listFormattedStringToWidth(
                    I18n.format("cfm.old_tv_remote.info"), 150));
        } else {
            tooltip.add(TextFormatting.YELLOW + I18n.format("cfm.info"));
        }
    }

    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return activateRetroTV(world, player);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        worldIn.playSound(null, playerIn.getPosition(), FurnitureSounds.tv_remote, SoundCategory.PLAYERS, 1.0F, 1.0F);
        return new ActionResult<>(activateRetroTV(worldIn, playerIn), playerIn.getHeldItem(handIn));
    }

    public EnumActionResult activateRetroTV(World worldIn, EntityPlayer playerIn) {
        Vec3d startVec = playerIn.getPositionEyes(0F);
        Vec3d endVec = startVec.add(playerIn.getLookVec().normalize().scale(16));
        RayTraceResult result = worldIn.rayTraceBlocks(startVec, endVec, false);

        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            TileEntity tileEntity = worldIn.getTileEntity(pos);

            if (tileEntity instanceof TileEntityRetroTV && !worldIn.isRemote) {
                TileEntityRetroTV retroTV = (TileEntityRetroTV) tileEntity;
                retroTV.nextChannel();
                worldIn.updateComparatorOutputLevel(pos, retroTV.getBlockType());
                worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5,
                        FurnitureSounds.white_noise, SoundCategory.BLOCKS, 0.75F, 1.0F);
                PacketHandler.INSTANCE.sendToAllAround(
                        new MessageTVPlaySound(pos, retroTV.getChannel()),
                        new NetworkRegistry.TargetPoint(playerIn.dimension, pos.getX(), pos.getY(), pos.getZ(), 64)
                );
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.PASS;
    }
}
