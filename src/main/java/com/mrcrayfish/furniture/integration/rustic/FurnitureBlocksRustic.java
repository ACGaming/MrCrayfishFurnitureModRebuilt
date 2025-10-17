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
    public static final Block HEDGE_RUSTIC_IRONWOOD, HEDGE_RUSTIC_OLIVE ;
    public static final Block COFFEE_TABLE_RUSTIC_IRONWOOD, COFFEE_TABLE_RUSTIC_OLIVE ;
    public static final Block CHAIR_RUSTIC_IRONWOOD, CHAIR_RUSTIC_OLIVE ;
    public static final Block TABLE_RUSTIC_IRONWOOD, TABLE_RUSTIC_OLIVE ;
    public static final Block CABINET_RUSTIC_IRONWOOD, CABINET_RUSTIC_OLIVE ;
    public static final Block BEDSIDE_CABINET_RUSTIC_IRONWOOD, BEDSIDE_CABINET_RUSTIC_OLIVE ;
    public static final Block DESK_RUSTIC_IRONWOOD, DESK_RUSTIC_OLIVE ;
    public static final Block DESK_CABINET_RUSTIC_IRONWOOD, DESK_CABINET_RUSTIC_OLIVE ;
    public static final Block MAIL_BOX_RUSTIC_IRONWOOD, MAIL_BOX_RUSTIC_OLIVE ;
    public static final Block DOOR_BELL_RUSTIC_IRONWOOD, DOOR_BELL_RUSTIC_OLIVE ;
    public static final Block CRATE_RUSTIC_IRONWOOD, CRATE_RUSTIC_OLIVE ;
    public static final Block BLINDS_RUSTIC_IRONWOOD, BLINDS_RUSTIC_OLIVE ;
    public static final Block BLINDS_CLOSED_RUSTIC_IRONWOOD, BLINDS_CLOSED_RUSTIC_OLIVE ;
    public static final Block FENCE_UPGRADED_RUSTIC_IRONWOOD, FENCE_UPGRADED_RUSTIC_OLIVE ;
    public static final Block GATE_UPGRADED_RUSTIC_IRONWOOD, GATE_UPGRADED_RUSTIC_OLIVE ;
    public static final Block PARK_BENCH_RUSTIC_IRONWOOD, PARK_BENCH_RUSTIC_OLIVE ;


    static {
        HEDGE_RUSTIC_IRONWOOD = new BlockHedge("hedge_rustic_ironwood");
        HEDGE_RUSTIC_OLIVE = new BlockHedge("hedge_rustic_olive");

        COFFEE_TABLE_RUSTIC_IRONWOOD = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_rustic_ironwood");
        COFFEE_TABLE_RUSTIC_OLIVE = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_rustic_olive");

        CHAIR_RUSTIC_IRONWOOD = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_rustic_ironwood");
        CHAIR_RUSTIC_OLIVE = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_rustic_olive");

        TABLE_RUSTIC_IRONWOOD = new BlockTable(Material.WOOD, SoundType.WOOD, "table_rustic_ironwood");
        TABLE_RUSTIC_OLIVE = new BlockTable(Material.WOOD, SoundType.WOOD, "table_rustic_olive");

        CABINET_RUSTIC_IRONWOOD = new BlockCabinet(Material.WOOD, "cabinet_rustic_ironwood");
        CABINET_RUSTIC_OLIVE = new BlockCabinet(Material.WOOD, "cabinet_rustic_olive");

        BEDSIDE_CABINET_RUSTIC_IRONWOOD = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_rustic_ironwood");
        BEDSIDE_CABINET_RUSTIC_OLIVE = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_rustic_olive");

        DESK_RUSTIC_IRONWOOD = new BlockDesk(Material.WOOD, "desk_rustic_ironwood");
        DESK_RUSTIC_OLIVE = new BlockDesk(Material.WOOD, "desk_rustic_olive");

        DESK_CABINET_RUSTIC_IRONWOOD = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_rustic_ironwood");
        DESK_CABINET_RUSTIC_OLIVE = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_rustic_olive");

        MAIL_BOX_RUSTIC_IRONWOOD = new BlockMailBox(Material.WOOD, "mail_box_rustic_ironwood");
        MAIL_BOX_RUSTIC_OLIVE = new BlockMailBox(Material.WOOD, "mail_box_rustic_olive");

        DOOR_BELL_RUSTIC_IRONWOOD = new BlockDoorBell(Material.WOOD, "door_bell_rustic_ironwood");
        DOOR_BELL_RUSTIC_OLIVE = new BlockDoorBell(Material.WOOD, "door_bell_rustic_olive");

        CRATE_RUSTIC_IRONWOOD = new BlockCrate(Material.WOOD, "crate_rustic_ironwood");
        CRATE_RUSTIC_OLIVE = new BlockCrate(Material.WOOD, "crate_rustic_olive");

        BLINDS_RUSTIC_IRONWOOD = new BlockBlinds(Material.WOOD, true, "blinds_rustic_ironwood_open");
        BLINDS_RUSTIC_OLIVE = new BlockBlinds(Material.WOOD, true, "blinds_rustic_olive_open");

        BLINDS_CLOSED_RUSTIC_IRONWOOD = new BlockBlinds(Material.WOOD, false, "blinds_rustic_ironwood_closed");
        BLINDS_CLOSED_RUSTIC_OLIVE = new BlockBlinds(Material.WOOD, false, "blinds_rustic_olive_closed");

        FENCE_UPGRADED_RUSTIC_IRONWOOD = new BlockUpgradedFence("upgraded_fence_rustic_ironwood");
        FENCE_UPGRADED_RUSTIC_OLIVE = new BlockUpgradedFence("upgraded_fence_rustic_olive");

        GATE_UPGRADED_RUSTIC_IRONWOOD = new BlockUpgradedGate("upgraded_gate_rustic_ironwood");
        GATE_UPGRADED_RUSTIC_OLIVE = new BlockUpgradedGate("upgraded_gate_rustic_olive");

        PARK_BENCH_RUSTIC_IRONWOOD = new BlockParkBench("park_bench_rustic_ironwood");
        PARK_BENCH_RUSTIC_OLIVE = new BlockParkBench("park_bench_rustic_olive");
    }


    public static void register(){
        FurnitureBlocks.registerBlock(HEDGE_RUSTIC_IRONWOOD, new ItemHedge(HEDGE_RUSTIC_IRONWOOD));
        FurnitureBlocks.registerBlock(HEDGE_RUSTIC_OLIVE, new ItemHedge(HEDGE_RUSTIC_OLIVE));

        FurnitureBlocks.registerBlock(COFFEE_TABLE_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(COFFEE_TABLE_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(CHAIR_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(CHAIR_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(TABLE_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(TABLE_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(CABINET_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(CABINET_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(DESK_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(DESK_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(DESK_CABINET_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(DESK_CABINET_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(MAIL_BOX_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(MAIL_BOX_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(DOOR_BELL_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(DOOR_BELL_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(CRATE_RUSTIC_IRONWOOD, new ItemCrate(CRATE_RUSTIC_IRONWOOD));
        FurnitureBlocks.registerBlock(CRATE_RUSTIC_OLIVE, new ItemCrate(CRATE_RUSTIC_OLIVE));

        FurnitureBlocks.registerBlock(BLINDS_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(BLINDS_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(BLINDS_CLOSED_RUSTIC_IRONWOOD, null);
        FurnitureBlocks.registerBlock(BLINDS_CLOSED_RUSTIC_OLIVE, null);

        FurnitureBlocks.registerBlock(FENCE_UPGRADED_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(FENCE_UPGRADED_RUSTIC_OLIVE);

        FurnitureBlocks.registerBlock(GATE_UPGRADED_RUSTIC_IRONWOOD, new ItemUpgradedGate(GATE_UPGRADED_RUSTIC_IRONWOOD));
        FurnitureBlocks.registerBlock(GATE_UPGRADED_RUSTIC_OLIVE, new ItemUpgradedGate(GATE_UPGRADED_RUSTIC_OLIVE));

        FurnitureBlocks.registerBlock(PARK_BENCH_RUSTIC_IRONWOOD);
        FurnitureBlocks.registerBlock(PARK_BENCH_RUSTIC_OLIVE);
    }

}
