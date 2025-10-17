package com.mrcrayfish.furniture.integration.rustic;

import com.mrcrayfish.furniture.blocks.*;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemUpgradedGate;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FurnitureBlocksRustic {
    public static final Block HEDGE_RUSTIC_IRONWOOD, HEDGE_RUSTIC_OLIVEWOODC ;
    public static final Block COFFEE_TABLE_RUSTIC_IRONWOOD, COFFEE_TABLE_RUSTIC_OLIVEWOODC ;
    public static final Block CHAIR_RUSTIC_IRONWOOD, CHAIR_RUSTIC_OLIVEWOODC ;
    public static final Block TABLE_RUSTIC_IRONWOOD, TABLE_RUSTIC_OLIVEWOODC ;
    public static final Block CABINET_RUSTIC_IRONWOOD, CABINET_RUSTIC_OLIVEWOODC ;
    public static final Block BEDSIDE_CABINET_RUSTIC_IRONWOOD, BEDSIDE_CABINET_RUSTIC_OLIVEWOODC ;
    public static final Block DESK_RUSTIC_IRONWOOD, DESK_RUSTIC_OLIVEWOODC ;
    public static final Block DESK_CABINET_RUSTIC_IRONWOOD, DESK_CABINET_RUSTIC_OLIVEWOODC ;
    public static final Block MAIL_BOX_RUSTIC_IRONWOOD, MAIL_BOX_RUSTIC_OLIVEWOODC ;
    public static final Block DOOR_BELL_RUSTIC_IRONWOOD, DOOR_BELL_RUSTIC_OLIVEWOODC ;
    public static final Block CRATE_RUSTIC_IRONWOOD, CRATE_RUSTIC_OLIVEWOODC ;
    public static final Block BLINDS_RUSTIC_IRONWOOD, BLINDS_RUSTIC_OLIVEWOODC ;
    public static final Block BLINDS_CLOSED_RUSTIC_IRONWOOD, BLINDS_CLOSED_RUSTIC_OLIVEWOODC ;
    public static final Block FENCE_UPGRADED_RUSTIC_IRONWOOD, FENCE_UPGRADED_RUSTIC_OLIVEWOODC ;
    public static final Block GATE_UPGRADED_RUSTIC_IRONWOOD, GATE_UPGRADED_RUSTIC_OLIVEWOODC ;
    public static final Block PARK_BENCH_RUSTIC_IRONWOOD, PARK_BENCH_RUSTIC_OLIVEWOODC ;


    static {
        HEDGE_RUSTIC_IRONWOOD = new BlockHedge("hedge_rustic_ironwood");
        HEDGE_RUSTIC_OLIVEWOODC = new BlockHedge("hedge_rustic_olivewoodc");

        COFFEE_TABLE_RUSTIC_IRONWOOD = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_rustic_ironwood");
        COFFEE_TABLE_RUSTIC_OLIVEWOODC = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_rustic_olivewoodc");

        CHAIR_RUSTIC_IRONWOOD = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_rustic_ironwood");
        CHAIR_RUSTIC_OLIVEWOODC = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_rustic_olivewoodc");

        TABLE_RUSTIC_IRONWOOD = new BlockTable(Material.WOOD, SoundType.WOOD, "table_rustic_ironwood");
        TABLE_RUSTIC_OLIVEWOODC = new BlockTable(Material.WOOD, SoundType.WOOD, "table_rustic_olivewoodc");

        CABINET_RUSTIC_IRONWOOD = new BlockCabinet(Material.WOOD, "cabinet_rustic_ironwood");
        CABINET_RUSTIC_OLIVEWOODC = new BlockCabinet(Material.WOOD, "cabinet_rustic_olivewoodc");

        BEDSIDE_CABINET_RUSTIC_IRONWOOD = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_rustic_ironwood");
        BEDSIDE_CABINET_RUSTIC_OLIVEWOODC = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_rustic_olivewoodc");

        DESK_RUSTIC_IRONWOOD = new BlockDesk(Material.WOOD, "desk_rustic_ironwood");
        DESK_RUSTIC_OLIVEWOODC = new BlockDesk(Material.WOOD, "desk_rustic_olivewoodc");

        DESK_CABINET_RUSTIC_IRONWOOD = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_rustic_ironwood");
        DESK_CABINET_RUSTIC_OLIVEWOODC = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_rustic_olivewoodc");

        MAIL_BOX_RUSTIC_IRONWOOD = new BlockMailBox(Material.WOOD, "mail_box_rustic_ironwood");
        MAIL_BOX_RUSTIC_OLIVEWOODC = new BlockMailBox(Material.WOOD, "mail_box_rustic_olivewoodc");

        DOOR_BELL_RUSTIC_IRONWOOD = new BlockDoorBell(Material.WOOD, "door_bell_rustic_ironwood");
        DOOR_BELL_RUSTIC_OLIVEWOODC = new BlockDoorBell(Material.WOOD, "door_bell_rustic_olivewoodc");

        CRATE_RUSTIC_IRONWOOD = new BlockCrate(Material.WOOD, "crate_rustic_ironwood");
        CRATE_RUSTIC_OLIVEWOODC = new BlockCrate(Material.WOOD, "crate_rustic_olivewoodc");

        BLINDS_RUSTIC_IRONWOOD = new BlockBlinds(Material.WOOD, true, "blinds_rustic_ironwood_open");
        BLINDS_RUSTIC_OLIVEWOODC = new BlockBlinds(Material.WOOD, true, "blinds_rustic_olivewoodc_open");

        BLINDS_CLOSED_RUSTIC_IRONWOOD = new BlockBlinds(Material.WOOD, false, "blinds_rustic_ironwood_closed");
        BLINDS_CLOSED_RUSTIC_OLIVEWOODC = new BlockBlinds(Material.WOOD, false, "blinds_rustic_olivewoodc_closed");

        FENCE_UPGRADED_RUSTIC_IRONWOOD = new BlockUpgradedFence("upgraded_fence_rustic_ironwood");
        FENCE_UPGRADED_RUSTIC_OLIVEWOODC = new BlockUpgradedFence("upgraded_fence_rustic_olivewoodc");

        GATE_UPGRADED_RUSTIC_IRONWOOD = new BlockUpgradedGate("upgraded_gate_rustic_ironwood");
        GATE_UPGRADED_RUSTIC_OLIVEWOODC = new BlockUpgradedGate("upgraded_gate_rustic_olivewoodc");

        PARK_BENCH_RUSTIC_IRONWOOD = new BlockParkBench("park_bench_rustic_ironwood");
        PARK_BENCH_RUSTIC_OLIVEWOODC = new BlockParkBench("park_bench_rustic_olivewoodc");
    }


    public static void register(){
        FurnitureBlocks.registerBlock(HEDGE_RUSTIC_IRONWOOD, new ItemHedge(HEDGE_RUSTIC_IRONWOOD));
        FurnitureBlocks.registerBlock(HEDGE_RUSTIC_OLIVEWOODC, new ItemHedge(HEDGE_RUSTIC_OLIVEWOODC));

        FurnitureBlocks.registerBlock(COFFEE_TABLE_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(COFFEE_TABLE_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(CHAIR_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(CHAIR_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(TABLE_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(TABLE_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(CABINET_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(CABINET_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(DESK_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(DESK_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(DESK_CABINET_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(DESK_CABINET_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(MAIL_BOX_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(MAIL_BOX_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(DOOR_BELL_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(DOOR_BELL_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(CRATE_RUSTIC_IRONWOOD, new ItemCrate(CRATE_RUSTIC_IRONWOOD));
        FurnitureBlocks.registerBlock(CRATE_RUSTIC_OLIVEWOODC, new ItemCrate(CRATE_RUSTIC_OLIVEWOODC));

        FurnitureBlocks.registerBlock(BLINDS_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(BLINDS_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(BLINDS_CLOSED_RUSTIC_IRONWOOD, null);
        FurnitureBlocks.registerBlock(BLINDS_CLOSED_RUSTIC_OLIVEWOODC, null);

        FurnitureBlocks.registerBlock(FENCE_UPGRADED_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(FENCE_UPGRADED_RUSTIC_OLIVEWOODC);

        FurnitureBlocks.registerBlock(GATE_UPGRADED_RUSTIC_IRONWOOD, new ItemUpgradedGate(GATE_UPGRADED_RUSTIC_IRONWOOD));
        FurnitureBlocks.registerBlock(GATE_UPGRADED_RUSTIC_OLIVEWOODC, new ItemUpgradedGate(GATE_UPGRADED_RUSTIC_OLIVEWOODC));

        FurnitureBlocks.registerBlock(PARK_BENCH_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(PARK_BENCH_RUSTIC_OLIVEWOODC);
    }

}
