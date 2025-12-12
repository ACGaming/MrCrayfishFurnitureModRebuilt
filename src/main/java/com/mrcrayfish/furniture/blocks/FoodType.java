package com.mrcrayfish.furniture.blocks;

import net.minecraft.util.IStringSerializable;

public enum FoodType implements IStringSerializable {

    DOG("dog"),
    CAT("cat");

    private final String name;

    FoodType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }
}