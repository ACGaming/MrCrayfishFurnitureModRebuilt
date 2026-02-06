package com.mrcrayfish.furniture.blocks;

import net.minecraft.block.material.Material;

public class BlockOldCurtainOpen extends BlockOldCurtain
{
    public BlockOldCurtainOpen(Material material, String id)
    {
        super(material, id);
    }

    @Override
    public boolean isOpen()
    {
        return true;
    }
}
