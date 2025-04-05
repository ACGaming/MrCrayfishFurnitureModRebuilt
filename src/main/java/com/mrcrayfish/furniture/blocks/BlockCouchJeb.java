package com.mrcrayfish.furniture.blocks;

public class BlockCouchJeb extends BlockCouch
{
    public BlockCouchJeb(String id)
    {
        super(id);
        this.setCreativeTab(null);
    }

    @Override
    public boolean isSpecial()
    {
        return true;
    }
}
