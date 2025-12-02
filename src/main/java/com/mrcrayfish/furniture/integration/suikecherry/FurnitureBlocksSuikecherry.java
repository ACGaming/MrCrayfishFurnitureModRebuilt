package com.mrcrayfish.furniture.integration.suikecherry;

import com.mrcrayfish.furniture.blocks.*;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemUpgradedGate;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FurnitureBlocksSuikecherry {
    public static final Block HEDGE_SUIKECHERRY_CHERRY ;
    public static final Block COFFEE_TABLE_SUIKECHERRY_CHERRY ;
    public static final Block CHAIR_SUIKECHERRY_CHERRY ;
    public static final Block TABLE_SUIKECHERRY_CHERRY ;
    public static final Block CABINET_SUIKECHERRY_CHERRY ;
    public static final Block BEDSIDE_CABINET_SUIKECHERRY_CHERRY ;
    public static final Block DESK_SUIKECHERRY_CHERRY ;
    public static final Block DESK_CABINET_SUIKECHERRY_CHERRY ;
    public static final Block MAIL_BOX_SUIKECHERRY_CHERRY ;
    public static final Block DOOR_BELL_SUIKECHERRY_CHERRY ;
    public static final Block CRATE_SUIKECHERRY_CHERRY ;
    public static final Block BLINDS_SUIKECHERRY_CHERRY ;
    public static final Block BLINDS_CLOSED_SUIKECHERRY_CHERRY ;
    public static final Block FENCE_UPGRADED_SUIKECHERRY_CHERRY ;
    public static final Block GATE_UPGRADED_SUIKECHERRY_CHERRY ;
    public static final Block PARK_BENCH_SUIKECHERRY_CHERRY ;


    static {
        HEDGE_SUIKECHERRY_CHERRY = new BlockHedge("hedge_suikecherry_cherry");

        COFFEE_TABLE_SUIKECHERRY_CHERRY = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_suikecherry_cherry");

        CHAIR_SUIKECHERRY_CHERRY = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_suikecherry_cherry");

        TABLE_SUIKECHERRY_CHERRY = new BlockTable(Material.WOOD, SoundType.WOOD, "table_suikecherry_cherry");

        CABINET_SUIKECHERRY_CHERRY = new BlockCabinet(Material.WOOD, "cabinet_suikecherry_cherry");

        BEDSIDE_CABINET_SUIKECHERRY_CHERRY = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_suikecherry_cherry");

        DESK_SUIKECHERRY_CHERRY = new BlockDesk(Material.WOOD, "desk_suikecherry_cherry");

        DESK_CABINET_SUIKECHERRY_CHERRY = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_suikecherry_cherry");

        MAIL_BOX_SUIKECHERRY_CHERRY = new BlockMailBox(Material.WOOD, "mail_box_suikecherry_cherry");

        DOOR_BELL_SUIKECHERRY_CHERRY = new BlockDoorBell(Material.WOOD, "door_bell_suikecherry_cherry");

        CRATE_SUIKECHERRY_CHERRY = new BlockCrate(Material.WOOD, "crate_suikecherry_cherry");

        BLINDS_SUIKECHERRY_CHERRY = new BlockBlinds(Material.WOOD, true, "blinds_suikecherry_cherry_open");

        BLINDS_CLOSED_SUIKECHERRY_CHERRY = new BlockBlinds(Material.WOOD, false, "blinds_suikecherry_cherry_closed");

        FENCE_UPGRADED_SUIKECHERRY_CHERRY = new BlockUpgradedFence("upgraded_fence_suikecherry_cherry");

        GATE_UPGRADED_SUIKECHERRY_CHERRY = new BlockUpgradedGate("upgraded_gate_suikecherry_cherry");

        PARK_BENCH_SUIKECHERRY_CHERRY = new BlockParkBench("park_bench_suikecherry_cherry");
    }


    public static void register(){
        FurnitureBlocks.registerBlock(HEDGE_SUIKECHERRY_CHERRY, new ItemHedge(HEDGE_SUIKECHERRY_CHERRY));

        FurnitureBlocks.registerBlock(COFFEE_TABLE_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(CHAIR_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(TABLE_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(CABINET_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(DESK_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(DESK_CABINET_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(MAIL_BOX_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(DOOR_BELL_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(CRATE_SUIKECHERRY_CHERRY, new ItemCrate(CRATE_SUIKECHERRY_CHERRY));

        FurnitureBlocks.registerBlock(BLINDS_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(BLINDS_CLOSED_SUIKECHERRY_CHERRY, null);

        FurnitureBlocks.registerBlock(FENCE_UPGRADED_SUIKECHERRY_CHERRY);

        FurnitureBlocks.registerBlock(GATE_UPGRADED_SUIKECHERRY_CHERRY, new ItemUpgradedGate(GATE_UPGRADED_SUIKECHERRY_CHERRY));

        FurnitureBlocks.registerBlock(PARK_BENCH_SUIKECHERRY_CHERRY);
    }

}
