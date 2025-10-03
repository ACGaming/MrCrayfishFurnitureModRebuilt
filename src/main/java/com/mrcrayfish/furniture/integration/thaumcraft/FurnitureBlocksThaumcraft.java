package com.mrcrayfish.furniture.integration.thaumcraft;

import com.mrcrayfish.furniture.blocks.*;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.items.ItemCrate;
import com.mrcrayfish.furniture.items.ItemHedge;
import com.mrcrayfish.furniture.items.ItemUpgradedGate;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class FurnitureBlocksThaumcraft {
    public static final Block HEDGE_THAUMCRAFT_SILVERWOOD, HEDGE_THAUMCRAFT_GREATWOOD ;
    public static final Block COFFEE_TABLE_THAUMCRAFT_SILVERWOOD, COFFEE_TABLE_THAUMCRAFT_GREATWOOD ;
    public static final Block CHAIR_THAUMCRAFT_SILVERWOOD, CHAIR_THAUMCRAFT_GREATWOOD ;
    public static final Block TABLE_THAUMCRAFT_SILVERWOOD, TABLE_THAUMCRAFT_GREATWOOD ;
    public static final Block CABINET_THAUMCRAFT_SILVERWOOD, CABINET_THAUMCRAFT_GREATWOOD ;
    public static final Block BEDSIDE_CABINET_THAUMCRAFT_SILVERWOOD,BEDSIDE_CABINET_THAUMCRAFT_GREATWOOD ;
    public static final Block DESK_THAUMCRAFT_SILVERWOOD, DESK_THAUMCRAFT_GREATWOOD;
    public static final Block DESK_CABINET_THAUMCRAFT_SILVERWOOD, DESK_CABINET_THAUMCRAFT_GREATWOOD;
    public static final Block MAIL_BOX_THAUMCRAFT_SILVERWOOD,MAIL_BOX_THAUMCRAFT_GREATWOOD;
    public static final Block DOOR_BELL_THAUMCRAFT_SILVERWOOD,DOOR_BELL_THAUMCRAFT_GREATWOOD;
    public static final Block CRATE_THAUMCRAFT_SILVERWOOD,CRATE_THAUMCRAFT_GREATWOOD;
    public static final Block BLINDS_THAUMCRAFT_SILVERWOOD,BLINDS_THAUMCRAFT_GREATWOOD ;
    public static final Block BLINDS_THAUMCRAFT_SILVERWOOD_CLOSED,BLINDS_THAUMCRAFT_GREATWOOD_CLOSED;
    public static final Block FENCE_UPGRADED_THAUMCRAFT_SILVERWOOD,FENCE_UPGRADED_THAUMCRAFT_GREATWOOD;
    public static final Block GATE_UPGRADED_THAUMCRAFT_SILVERWOOD,GATE_UPGRADED_THAUMCRAFT_GREATWOOD;
    public static final Block PARK_BENCH_THAUMCRAFT_SILVERWOOD,PARK_BENCH_THAUMCRAFT_GREATWOOD;
    
    
    

    static {
        HEDGE_THAUMCRAFT_SILVERWOOD = new BlockHedge("hedge_thaumcraft_silverwood");
        HEDGE_THAUMCRAFT_GREATWOOD = new BlockHedge("hedge_thaumcraft_greatwood");

        COFFEE_TABLE_THAUMCRAFT_SILVERWOOD = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_thaumcraft_silverwood");
        COFFEE_TABLE_THAUMCRAFT_GREATWOOD = new BlockCoffeeTable(Material.WOOD, SoundType.WOOD, "coffee_table_thaumcraft_greatwood");

        CHAIR_THAUMCRAFT_SILVERWOOD = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_thaumcraft_silverwood");
        CHAIR_THAUMCRAFT_GREATWOOD = new BlockChair(Material.WOOD, SoundType.WOOD, "chair_thaumcraft_greatwood");
        
        TABLE_THAUMCRAFT_SILVERWOOD = new BlockTable(Material.WOOD, SoundType.WOOD, "table_thaumcraft_silverwood");
        TABLE_THAUMCRAFT_GREATWOOD = new BlockTable(Material.WOOD, SoundType.WOOD, "table_thaumcraft_greatwood");
        
        CABINET_THAUMCRAFT_SILVERWOOD = new BlockCabinet(Material.WOOD, "cabinet_thaumcraft_silverwood");
        CABINET_THAUMCRAFT_GREATWOOD = new BlockCabinet(Material.WOOD, "cabinet_thaumcraft_greatwood");
        
        BEDSIDE_CABINET_THAUMCRAFT_SILVERWOOD = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_thaumcraft_silverwood");
        BEDSIDE_CABINET_THAUMCRAFT_GREATWOOD = new BlockBedsideCabinet(Material.WOOD, "bedside_cabinet_thaumcraft_greatwood");
        
        DESK_THAUMCRAFT_SILVERWOOD = new BlockDesk(Material.WOOD, "desk_thaumcraft_silverwood");
        DESK_THAUMCRAFT_GREATWOOD = new BlockDesk(Material.WOOD, "desk_thaumcraft_greatwood");
        
        DESK_CABINET_THAUMCRAFT_SILVERWOOD = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_thaumcraft_silverwood");
        DESK_CABINET_THAUMCRAFT_GREATWOOD = new BlockDeskCabinet(Material.WOOD, "desk_cabinet_thaumcraft_greatwood");
        
        MAIL_BOX_THAUMCRAFT_SILVERWOOD = new BlockMailBox(Material.WOOD, "mail_box_thaumcraft_silverwood");
        MAIL_BOX_THAUMCRAFT_GREATWOOD = new BlockMailBox(Material.WOOD, "mail_box_thaumcraft_greatwood");
        
        DOOR_BELL_THAUMCRAFT_SILVERWOOD = new BlockDoorBell(Material.WOOD, "door_bell_thaumcraft_silverwood");
        DOOR_BELL_THAUMCRAFT_GREATWOOD = new BlockDoorBell(Material.WOOD, "door_bell_thaumcraft_greatwood");
        
        CRATE_THAUMCRAFT_SILVERWOOD = new BlockCrate(Material.WOOD, "crate_thaumcraft_silverwood");
        CRATE_THAUMCRAFT_GREATWOOD = new BlockCrate(Material.WOOD, "crate_thaumcraft_greatwood");
        
        BLINDS_THAUMCRAFT_SILVERWOOD = new BlockBlinds(Material.WOOD, true, "blinds_thaumcraft_silverwood_open");
        BLINDS_THAUMCRAFT_GREATWOOD = new BlockBlinds(Material.WOOD, true, "blinds_thaumcraft_greatwood_open");

        BLINDS_THAUMCRAFT_SILVERWOOD_CLOSED = new BlockBlinds(Material.WOOD, false, "blinds_thaumcraft_silverwood_closed");
        BLINDS_THAUMCRAFT_GREATWOOD_CLOSED = new BlockBlinds(Material.WOOD, false, "blinds_thaumcraft_greatwood_closed");
        
        FENCE_UPGRADED_THAUMCRAFT_SILVERWOOD = new BlockUpgradedFence("upgraded_fence_thaumcraft_silverwood");
        FENCE_UPGRADED_THAUMCRAFT_GREATWOOD = new BlockUpgradedFence("upgraded_fence_thaumcraft_greatwood");
        
        GATE_UPGRADED_THAUMCRAFT_SILVERWOOD = new BlockUpgradedGate("upgraded_gate_thaumcraft_silverwood");
        GATE_UPGRADED_THAUMCRAFT_GREATWOOD = new BlockUpgradedGate("upgraded_gate_thaumcraft_greatwood");
        
        PARK_BENCH_THAUMCRAFT_SILVERWOOD = new BlockParkBench("park_bench_thaumcraft_silverwood");
        PARK_BENCH_THAUMCRAFT_GREATWOOD = new BlockParkBench("park_bench_thaumcraft_greatwood");
        
    }


    public static void register(){
        FurnitureBlocks.registerBlock(HEDGE_THAUMCRAFT_SILVERWOOD, new ItemHedge(HEDGE_THAUMCRAFT_SILVERWOOD));
        FurnitureBlocks.registerBlock(HEDGE_THAUMCRAFT_GREATWOOD, new ItemHedge(HEDGE_THAUMCRAFT_GREATWOOD));

        FurnitureBlocks.registerBlock(COFFEE_TABLE_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(COFFEE_TABLE_THAUMCRAFT_GREATWOOD);

        FurnitureBlocks.registerBlock(CHAIR_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(CHAIR_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(TABLE_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(TABLE_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(CABINET_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(CABINET_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(BEDSIDE_CABINET_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(DESK_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(DESK_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(DESK_CABINET_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(DESK_CABINET_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(MAIL_BOX_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(MAIL_BOX_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(DOOR_BELL_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(DOOR_BELL_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(CRATE_THAUMCRAFT_SILVERWOOD, new ItemCrate(CRATE_THAUMCRAFT_SILVERWOOD));
        FurnitureBlocks.registerBlock(CRATE_THAUMCRAFT_GREATWOOD, new ItemCrate(CRATE_THAUMCRAFT_GREATWOOD));


        FurnitureBlocks.registerBlock(BLINDS_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(BLINDS_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(BLINDS_THAUMCRAFT_SILVERWOOD_CLOSED, null);
        FurnitureBlocks.registerBlock(BLINDS_THAUMCRAFT_GREATWOOD_CLOSED, null);


        FurnitureBlocks.registerBlock(FENCE_UPGRADED_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(FENCE_UPGRADED_THAUMCRAFT_GREATWOOD);


        FurnitureBlocks.registerBlock(GATE_UPGRADED_THAUMCRAFT_SILVERWOOD, new ItemUpgradedGate(GATE_UPGRADED_THAUMCRAFT_SILVERWOOD));
        FurnitureBlocks.registerBlock(GATE_UPGRADED_THAUMCRAFT_GREATWOOD, new ItemUpgradedGate(GATE_UPGRADED_THAUMCRAFT_GREATWOOD));


        FurnitureBlocks.registerBlock(PARK_BENCH_THAUMCRAFT_SILVERWOOD);
        FurnitureBlocks.registerBlock(PARK_BENCH_THAUMCRAFT_GREATWOOD);


    }

}
