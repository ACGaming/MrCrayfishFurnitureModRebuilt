package com.mrcrayfish.furniture.init;

import net.minecraft.block.state.IBlockState;

import com.mrcrayfish.furniture.util.SwitchableBlock;
import java.util.ArrayList;
import java.util.List;

public class SwitchableBlockRegistry
{
    private static final SwitchableBlockRegistry INSTANCE = new SwitchableBlockRegistry();
    private final List<SwitchableBlock> entries = new ArrayList<>();

    private SwitchableBlockRegistry() {}

    public static SwitchableBlockRegistry getInstance()
    {
        return INSTANCE;
    }

    public void reload(String[] entries)
    {
        this.entries.clear();
        for(String entry : entries)
        {
            SwitchableBlock switchable = SwitchableBlock.fromEntry(entry);
            if(switchable != null)
            {
                this.entries.add(switchable);
            }
        }
    }

    public SwitchableBlock getEntryFor(IBlockState state)
    {
        for(SwitchableBlock entry : entries)
        {
            if(entry.matches(state))
            {
                return entry;
            }
        }
        return null;
    }

    public boolean isValidTarget(IBlockState state)
    {
        return getEntryFor(state) != null;
    }
}
