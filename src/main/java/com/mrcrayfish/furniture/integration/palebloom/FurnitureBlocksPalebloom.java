package com.mrcrayfish.furniture.integration.palebloom;

import com.mrcrayfish.furniture.blocks.*;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemUpgradedGate;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FurnitureBlocksPalebloom {
    public static final Block HEDGE_PALEBLOOM_PALE_OAK ;
    public static final Block COFFEE_TABLE_PALEBLOOM_PALE_OAK ;
    public static final Block CHAIR_PALEBLOOM_PALE_OAK ;
    public static final Block TABLE_PALEBLOOM_PALE_OAK ;
    public static final Block CABINET_PALEBLOOM_PALE_OAK ;
    public static final Block BEDSIDE_CABINET_PALEBLOOM_PALE_OAK ;
    public static final Block DESK_PALEBLOOM_PALE_OAK ;
    public static final Block DESK_CABINET_PALEBLOOM_PALE_OAK ;
    public static final Block MAIL_BOX_PALEBLOOM_PALE_OAK ;
    public static final Block DOOR_BELL_PALEBLOOM_PALE_OAK ;
    public static final Block CRATE_PALEBLOOM_PALE_OAK ;
    public static final Block BLINDS_PALEBLOOM_PALE_OAK ;
    public static final Block BLINDS_CLOSED_PALEBLOOM_PALE_OAK ;
    public static final Block FENCE_UPGRADED_PALEBLOOM_PALE_OAK ;
    public static final Block GATE_UPGRADED_PALEBLOOM_PALE_OAK ;
    public static final Block PARK_BENCH_PALEBLOOM_PALE_OAK ;


    static {
        HEDGE_PALEBLOOM_PALE_OAK = new BlockHedge("hedge_palebloom_pale_oak");

        COFFEE_TABLE_PALEBLOOM_PALE_OAK = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_palebloom_pale_oak");

        CHAIR_PALEBLOOM_PALE_OAK = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_palebloom_pale_oak");

        TABLE_PALEBLOOM_PALE_OAK = new BlockTable(Material.WOOD, SoundType.WOOD, "table_palebloom_pale_oak");

        CABINET_PALEBLOOM_PALE_OAK = new BlockCabinet(Material.WOOD, "cabinet_palebloom_pale_oak");

        BEDSIDE_CABINET_PALEBLOOM_PALE_OAK = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_palebloom_pale_oak");

        DESK_PALEBLOOM_PALE_OAK = new BlockDesk(Material.WOOD, "desk_palebloom_pale_oak");

        DESK_CABINET_PALEBLOOM_PALE_OAK = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_palebloom_pale_oak");

        MAIL_BOX_PALEBLOOM_PALE_OAK = new BlockMailBox(Material.WOOD, "mail_box_palebloom_pale_oak");

        DOOR_BELL_PALEBLOOM_PALE_OAK = new BlockDoorBell(Material.WOOD, "door_bell_palebloom_pale_oak");

        CRATE_PALEBLOOM_PALE_OAK = new BlockCrate(Material.WOOD, "crate_palebloom_pale_oak");

        BLINDS_PALEBLOOM_PALE_OAK = new BlockBlinds(Material.WOOD, true, "blinds_palebloom_pale_oak_open");

        BLINDS_CLOSED_PALEBLOOM_PALE_OAK = new BlockBlinds(Material.WOOD, false, "blinds_palebloom_pale_oak_closed");

        FENCE_UPGRADED_PALEBLOOM_PALE_OAK = new BlockUpgradedFence("upgraded_fence_palebloom_pale_oak");

        GATE_UPGRADED_PALEBLOOM_PALE_OAK = new BlockUpgradedGate("upgraded_gate_palebloom_pale_oak");

        PARK_BENCH_PALEBLOOM_PALE_OAK = new BlockParkBench("park_bench_palebloom_pale_oak");
    }


    public static void register(){
        FurnitureBlocks.registerBlock(HEDGE_PALEBLOOM_PALE_OAK, new ItemHedge(HEDGE_PALEBLOOM_PALE_OAK));

        FurnitureBlocks.registerBlock(COFFEE_TABLE_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(CHAIR_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(TABLE_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(CABINET_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(DESK_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(DESK_CABINET_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(MAIL_BOX_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(DOOR_BELL_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(CRATE_PALEBLOOM_PALE_OAK, new ItemCrate(CRATE_PALEBLOOM_PALE_OAK));

        FurnitureBlocks.registerBlock(BLINDS_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(BLINDS_CLOSED_PALEBLOOM_PALE_OAK, null);

        FurnitureBlocks.registerBlock(FENCE_UPGRADED_PALEBLOOM_PALE_OAK);

        FurnitureBlocks.registerBlock(GATE_UPGRADED_PALEBLOOM_PALE_OAK, new ItemUpgradedGate(GATE_UPGRADED_PALEBLOOM_PALE_OAK));

        FurnitureBlocks.registerBlock(PARK_BENCH_PALEBLOOM_PALE_OAK);
    }

}
