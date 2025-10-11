package com.mrcrayfish.furniture;

import com.mrcrayfish.cfm.Tags;

public class Reference
{
    public static final String MOD_ID = Tags.MOD_ID;
    public static final String NAME = "MrCrayfish's Furniture Mod Rebuilt";
    public static final String VERSION = Tags.VERSION;
    public static final String ACCEPTED_MC_VERSIONS = "[1.12.2]";
    public static final String DEPENDENCIES = "after:biomesoplenty;after:crafttweaker";
    public static final String CLIENT_PROXY_CLASS = "com.mrcrayfish.furniture.proxy.ClientProxy";
    public static final String SERVER_PROXY_CLASS = "com.mrcrayfish.furniture.proxy.CommonProxy";
    public static final String GUI_FACTORY_CLASS = "com.mrcrayfish.furniture.gui.GuiFactory";
}
