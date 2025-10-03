package com.mrcrayfish.furniture.blocks;

import com.mrcrayfish.furniture.MrCrayfishFurnitureMod;
import net.minecraft.block.BlockPane;
import net.minecraft.block.material.Material;

/**
 * Author: MrCrayfish
 */
public class BlockCastleNetting extends BlockPane
{
    public BlockCastleNetting(String id)
    {
        super(Material.CLOTH, true);
        this.setHardness(0.5F);
        this.setTranslationKey(id);
        this.setRegistryName(id);
        this.setCreativeTab(MrCrayfishFurnitureMod.tabFurniture);
    }
}
