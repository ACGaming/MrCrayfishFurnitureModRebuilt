package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

public class BlockOldCurtainClosed extends BlockOldCurtain
{
    public BlockOldCurtainClosed(Material material, String id)
    {
        super(material, id);
        this.setCreativeTab(null);
    }

    @Override
    public boolean isOpen()
    {
        return false;
    }
}
