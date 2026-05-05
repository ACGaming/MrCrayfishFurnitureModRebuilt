package com.mrcrayfish.furniture.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;

public class SwitchableBlock
{
    private final IBlockState offState;
    private final IBlockState onState;

    public SwitchableBlock(IBlockState offState, IBlockState onState)
    {
        this.offState = offState;
        this.onState = onState;
    }

    public IBlockState getOffState()
    {
        return offState;
    }

    public IBlockState getOnState()
    {
        return onState;
    }

    public boolean matches(IBlockState state)
    {
        return state == offState || state == onState;
    }

    public IBlockState getOpposite(IBlockState current)
    {
        if(current == offState) return onState;
        if(current == onState) return offState;
        return null;
    }

    public static SwitchableBlock fromEntry(String entry)
    {
        String[] parts = entry.split(";");
        if(parts.length != 2) return null;
        IBlockState offState = parseBlockState(parts[0].trim());
        IBlockState onState  = parseBlockState(parts[1].trim());
        if(offState == null || onState == null) return null;
        return new SwitchableBlock(offState, onState);
    }

    private static IBlockState parseBlockState(String entry)
    {
        String[] parts = entry.trim().split(":");
        try
        {
            String domain = parts[0];
            String path = parts[1];
            int meta = parts.length >= 3 ? Integer.parseInt(parts[2]) : 0;
            Block block = Block.getBlockFromName(domain + ":" + path);
            if (block == null) return null;
            return block.getStateFromMeta(meta);
        }
        catch(Exception e)
        {
            return null;
        }
    }
}
