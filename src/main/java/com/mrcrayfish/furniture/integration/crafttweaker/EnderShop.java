package com.mrcrayfish.furniture.integration.crafttweaker;

import javax.annotation.Nonnull;

import com.mrcrayfish.furniture.api.RecipeData;
import com.mrcrayfish.furniture.api.Recipes;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenDoc;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IIngredient;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.item.IngredientAny;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.mc1120.item.MCItemStack;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass(CraftTweakerIntegration.CLASS_PREFIX + "EnderShop")
public class EnderShop
{
    @ZenMethod
    @ZenDoc("Remove matching trades.")
    public static void remove(@Optional IIngredient item)
    {
        if(item == null) item = IngredientAny.INSTANCE;

        final IIngredient finalItem = item;
        CraftTweakerIntegration.defer("Remove trade(s) matching " + item + " from EnderShop", () ->
        {
            if(!Recipes.localEnderShopRecipes.removeIf(data ->
            {
                if(finalItem.matchesExact(new MCItemStack(data.getInput())))
                {
                    CraftTweakerAPI.logInfo("EnderShop: Removed trade " + data);
                    return true;
                }
                return false;
            }))
            {
                CraftTweakerAPI.logError("EnderShop: No trades matched " + finalItem);
            }
        });
    }

    @ZenMethod
    @ZenDoc("Add a trade.")
    public static void addTrade(@Nonnull IItemStack item, @Nonnull IItemStack currency)
    {
        if(item == null) throw new IllegalArgumentException("item cannot be null");
        if(currency == null) throw new IllegalArgumentException("currency cannot be null");

        final RecipeData data = new RecipeData();
        // EnderShop RecipeData uses setInput for the purchasable item
        data.setInput(CraftTweakerMC.getItemStack(item));
        // MineBar RecipeData uses setCurrency for the currency item and setPrice for the amount of that item
        data.setCurrency(CraftTweakerMC.getItemStack(currency.withAmount(1)));
        data.setPrice(currency.getAmount());

        CraftTweakerIntegration.defer("Add trade " + data + " to EnderShop", () ->
        {
            if(data.getPrice() < 1 || data.getPrice() > 64)
            {
                CraftTweakerAPI.logError("EnderShop: Invalid trade price. Must be from 1 to 64.");
                return;
            }
            Recipes.localEnderShopRecipes.add(data);
            CraftTweakerAPI.logInfo("EnderShop: Added trade " + data);
        });
    }
}
