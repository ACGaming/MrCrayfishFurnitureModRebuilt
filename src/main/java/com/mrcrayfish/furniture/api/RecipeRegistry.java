package com.mrcrayfish.furniture.api;

import com.mrcrayfish.furniture.handler.ConfigurationHandler;
import com.mrcrayfish.furniture.init.FurnitureBlocks;
import com.mrcrayfish.furniture.init.FurnitureItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.*;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Map;

public class RecipeRegistry extends RecipeAPI
{
    private static RecipeRegistry furnitureRegister = null;

    public static RecipeRegistry getInstance()
    {
        if(furnitureRegister == null)
        {
            furnitureRegister = new RecipeRegistry();
        }
        return furnitureRegister;
    }

    public void registerMineBayItem(ItemStack item, ItemStack currency, int price)
    {
        addMineBayRecipe(new RecipeData().setInput(item).setCurrency(currency).setPrice(price), LOCAL);
    }

    public void registerEnderShopItem(ItemStack item, ItemStack currency, int price)
    {
        addEnderShopRecipe(new RecipeData().setInput(item).setCurrency(currency).setPrice(price), LOCAL);
    }

    public void registerOvenRecipe(ItemStack input, ItemStack output)
    {
        addOvenRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerFreezerRecipe(ItemStack input, ItemStack output)
    {
        addFreezerRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerPrinterRecipe(ItemStack input)
    {
        addPrinterRecipe(new RecipeData().setInput(input), LOCAL);
    }

    public void registerChoppingBoardRecipe(ItemStack input, ItemStack output)
    {
        addChoppingBoardRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerToasterRecipe(ItemStack input, ItemStack output)
    {
        addToasterRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerMicrowaveRecipe(ItemStack input, ItemStack output)
    {
        addMicrowaveRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerWashingMachineRecipe(ItemStack input)
    {
        addWashingMachineRecipe(new RecipeData().setInput(input), LOCAL);
    }

    public void registerDishwasherRecipe(ItemStack input)
    {
        addDishwasherRecipe(new RecipeData().setInput(input), LOCAL);
    }

    public void registerGrillRecipe(ItemStack input, ItemStack output)
    {
        addGrillRecipe(new RecipeData().setInput(input).setOutput(output), LOCAL);
    }

    public void registerBlenderRecipe(String name, int heal, ItemStack[] ingredients, int[] rgb)
    {
        RecipeData data = new RecipeData();
        data.setName(name);
        data.setHeal(heal);
        for(ItemStack item : ingredients)
        {
            data.addIngredient(item);
        }
        data.setColour(rgb[0], rgb[1], rgb[2]);
        addBlenderRecipe(data, LOCAL);
    }

    public static void registerPrinterRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            if(input != null)
            {
                int i_metadata;
                try
                {
                    i_metadata = Integer.parseInt(input_metadata);
                }
                catch(NumberFormatException e)
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer.");
                    }
                    return;
                }

                RecipeRegistry.getInstance().registerPrinterRecipe(new ItemStack(input, 1, i_metadata));
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerOvenRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerFreezerRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metdata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerMineBayRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String input_amount = parser.getValue("input-amount", "1");
        String payment_item = parser.getValue("payment-item", "minecraft:emerald");
        String payment_item_metadata = parser.getValue("payment-metadata", "0");
        String price = parser.getValue("payment-price", "1");

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            Item payment = Item.getByNameOrId(payment_item);
            if(input != null)
            {
                if(payment != null)
                {

                    int i_metadata;
                    try
                    {
                        i_metadata = Integer.parseInt(input_metadata);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                        }
                        return;
                    }

                    int i_amount;
                    try
                    {
                        i_amount = Integer.parseInt(input_amount);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "input-amount", "Could not parse the value '" + input_amount + "' to an integer");
                        }
                        return;
                    }

                    int p_metadata = 0;
                    try
                    {
                        p_metadata = Integer.parseInt(payment_item_metadata);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "payment-metadata", "Could not parse the value '" + p_metadata + "' to an integer");
                        }
                        return;
                    }

                    int p_price = 1;
                    try
                    {
                        p_price = Integer.parseInt(price);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "price", "Could not parse the value '" + p_price + "' to an integer");
                        }
                        return;
                    }

                    RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(input, i_amount, i_metadata), new ItemStack(payment, 1, p_metadata), p_price);
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "payment-item", "The payment-item '" + payment_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerEnderShopRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String input_amount = parser.getValue("input-amount", "1");
        String payment_item = parser.getValue("payment-item", "minecraft:emerald");
        String payment_item_metadata = parser.getValue("payment-metadata", "0");
        String price = parser.getValue("payment-price", "1");

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            Item payment = Item.getByNameOrId(payment_item);
            if(input != null)
            {
                if(payment != null)
                {

                    int i_metadata;
                    try
                    {
                        i_metadata = Integer.parseInt(input_metadata);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                        }
                        return;
                    }

                    int i_amount;
                    try
                    {
                        i_amount = Integer.parseInt(input_amount);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "input-amount", "Could not parse the value '" + input_amount + "' to an integer");
                        }
                        return;
                    }

                    int p_metadata = 0;
                    try
                    {
                        p_metadata = Integer.parseInt(payment_item_metadata);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "payment-metadata", "Could not parse the value '" + p_metadata + "' to an integer");
                        }
                        return;
                    }

                    int p_price = 1;
                    try
                    {
                        p_price = Integer.parseInt(price);
                    }
                    catch(NumberFormatException e)
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "price", "Could not parse the value '" + p_price + "' to an integer");
                        }
                        return;
                    }

                    RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(input, i_amount, i_metadata), new ItemStack(payment, 1, p_metadata), p_price);
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "payment-item", "The payment-item '" + payment_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerToasterRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerToasterRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerChoppingBoardRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");
        String output_amount = parser.getValue("output-amount", "1");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_amount;
                        try
                        {
                            o_amount = Integer.parseInt(output_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-amount", "Could not parse the value '" + output_amount + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerChoppingBoardRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, o_amount, o_metadata));
                    }
                    else
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerBlenderRecipe(Parser parser, int num)
    {
        String name = parser.getValue("name", null);
        String heal_amount = parser.getValue("heal", null);
        String ingredients = parser.getValue("ingredients", null);
        String colour = parser.getValue("colour", null);

        if(name != null)
        {
            if(heal_amount != null)
            {
                if(ingredients != null)
                {
                    if(colour != null)
                    {
                        int h_amount;
                        try
                        {
                            h_amount = Integer.parseInt(heal_amount);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "heal", "Could not parse the value '" + heal_amount + "' to an integer");
                            }
                            return;
                        }

                        ItemStack[] in = parseIngredients(ingredients, num, parser);
                        if(in == null)
                        {
                            return;
                        }

                        int[] rgb = parseColour(colour, num, parser);
                        if(rgb == null)
                        {
                            return;
                        }

                        name = parseFormatting(parseSpaces(name));

                        RecipeRegistry.getInstance().registerBlenderRecipe(name, h_amount, in, rgb);
                    }
                    else
                    {

                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printMissing(parser, num, "colour", "A colour is required");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printMissing(parser, num, "ingredients", "Ingredients are required");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "heal", "A heal amount is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "name", "A name is required");
            }
        }
    }

    public static ItemStack[] parseIngredients(String ingredients, int num, Parser parser)
    {
        ArrayList<ItemStack> list = new ArrayList<>();
        String[] ingredientData = ingredients.split("/");

        if(ingredientData.length == 0)
        {
            return null;
        }

        int length = ingredientData.length > 4 ? 4 : ingredientData.length;
        for(int i = 0; i < length; i++)
        {
            String[] itemData = ingredientData[i].split(":");
            String itemName = itemData[0] + ":" + itemData[1];
            String itemAmount = "1";
            String itemMetadata = "0";

            if(itemData.length > 2)
            {
                itemAmount = itemData[2];
                if(itemData.length > 3)
                {
                    itemMetadata = itemData[3];
                }
            }

            Item item = Item.getByNameOrId(itemName);

            int i_amount;
            try
            {
                i_amount = Integer.parseInt(itemAmount);
            }
            catch(NumberFormatException e)
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "ingredients", "Could not parse the value '" + itemAmount + "' to an integer for ingredient number " + i);
                }
                return null;
            }

            int i_metadata;
            try
            {
                i_metadata = Integer.parseInt(itemMetadata);
            }
            catch(NumberFormatException e)
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "ingredients", "Could not parse the value '" + itemMetadata + "' to an integer for ingredient number " + i);
                }
                return null;
            }

            list.add(new ItemStack(item, i_amount, i_metadata));
        }
        return list.toArray(new ItemStack[0]);
    }

    public static int[] parseColour(String colour, int num, Parser parser)
    {
        String[] rgb = colour.split(":");
        if(rgb.length == 3)
        {
            String r = rgb[0];
            String g = rgb[1];
            String b = rgb[2];

            int red;
            try
            {
                red = Integer.parseInt(r);
            }
            catch(NumberFormatException e)
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "colour", "Could not parse the value '" + r + "' to an integer");
                }
                return null;
            }

            int green;
            try
            {
                green = Integer.parseInt(g);
            }
            catch(NumberFormatException e)
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "colour", "Could not parse the value '" + g + "' to an integer");
                }
                return null;
            }

            int blue;
            try
            {
                blue = Integer.parseInt(b);
            }
            catch(NumberFormatException e)
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "colour", "Could not parse the value '" + b + "' to an integer");
                }
                return null;
            }

            return new int[]{red, green, blue};
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printReport(parser, num, "colour", "The colour variable doesn't have all three rgb values set, it needs to be colour=r-g-b");
            }
            return null;
        }
    }

    public static String parseSpaces(String name)
    {
        return name.replaceAll("_", " ");
    }

    public static String parseFormatting(String name)
    {
        name = name.replaceAll("&0", TextFormatting.BLACK.toString());
        name = name.replaceAll("&1", TextFormatting.DARK_BLUE.toString());
        name = name.replaceAll("&2", TextFormatting.DARK_GREEN.toString());
        name = name.replaceAll("&3", TextFormatting.DARK_AQUA.toString());
        name = name.replaceAll("&4", TextFormatting.DARK_RED.toString());
        name = name.replaceAll("&5", TextFormatting.DARK_PURPLE.toString());
        name = name.replaceAll("&6", TextFormatting.GOLD.toString());
        name = name.replaceAll("&7", TextFormatting.GRAY.toString());
        name = name.replaceAll("&8", TextFormatting.DARK_GRAY.toString());
        name = name.replaceAll("&9", TextFormatting.BLUE.toString());
        name = name.replaceAll("&a", TextFormatting.GREEN.toString());
        name = name.replaceAll("&b", TextFormatting.AQUA.toString());
        name = name.replaceAll("&c", TextFormatting.RED.toString());
        name = name.replaceAll("&d", TextFormatting.LIGHT_PURPLE.toString());
        name = name.replaceAll("&e", TextFormatting.YELLOW.toString());
        name = name.replaceAll("&f", TextFormatting.WHITE.toString());
        name = name.replaceAll("&k", TextFormatting.OBFUSCATED.toString());
        name = name.replaceAll("&l", TextFormatting.BOLD.toString());
        name = name.replaceAll("&m", TextFormatting.STRIKETHROUGH.toString());
        name = name.replaceAll("&n", TextFormatting.UNDERLINE.toString());
        name = name.replaceAll("&o", TextFormatting.ITALIC.toString());
        name = name.replaceAll("&r", TextFormatting.RESET.toString());
        return name;
    }

    public static void registerMicrowaveRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, 1, o_metadata));
                    }
                    else
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerWashingMachineRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            if(input != null)
            {
                RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(input));
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerDishwasherRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);

        if(input_item != null)
        {
            Item input = Item.getByNameOrId(input_item);
            if(input != null)
            {
                RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(input));
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerGrillRecipe(Parser parser, int num)
    {
        String input_item = parser.getValue("input-item", null);
        String input_metadata = parser.getValue("input-metadata", "0");
        String output_item = parser.getValue("output-item", null);
        String output_metadata = parser.getValue("output-metadata", "0");

        if(input_item != null)
        {
            if(output_item != null)
            {
                Item input = Item.getByNameOrId(input_item);
                Item output = Item.getByNameOrId(output_item);
                if(input != null)
                {
                    if(output != null)
                    {
                        int i_metadata;
                        try
                        {
                            i_metadata = Integer.parseInt(input_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "input-metadata", "Could not parse the value '" + input_metadata + "' to an integer");
                            }
                            return;
                        }

                        int o_metadata;
                        try
                        {
                            o_metadata = Integer.parseInt(output_metadata);
                        }
                        catch(NumberFormatException e)
                        {
                            if(ConfigurationHandler.api_debug)
                            {
                                RecipeUtil.printReport(parser, num, "output-metadata", "Could not parse the value '" + output_metadata + "' to an integer");
                            }
                            return;
                        }

                        RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(input, 1, i_metadata), new ItemStack(output, 1, o_metadata));
                    }
                    else
                    {
                        if(ConfigurationHandler.api_debug)
                        {
                            RecipeUtil.printReport(parser, num, "output-item", "The output-item '" + output_item + "' does not exist");
                        }
                    }
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, num, "input-item", "The input-item '" + input_item + "' does not exist");
                    }
                }
            }
            else
            {
                if(ConfigurationHandler.api_debug)
                {
                    RecipeUtil.printMissing(parser, num, "output-item", "An output-item is required");
                }
            }
        }
        else
        {
            if(ConfigurationHandler.api_debug)
            {
                RecipeUtil.printMissing(parser, num, "input-item", "An input-item is required");
            }
        }
    }

    public static void registerConfigRecipes()
    {
        if(ConfigurationHandler.items.length > 0 && ConfigurationHandler.api_debug)
        {
            System.out.println("RecipeAPI (Configuration): Registering " + ConfigurationHandler.items.length + " recipes from the config.");
        }
        Parser parser = Parser.getInstance();
        for(int i = 0; i < ConfigurationHandler.items.length; i++)
        {
            parser.parseLine(ConfigurationHandler.items[i], true);
            String type = parser.getValue("type", null);

            int realNum = i + 1;

            if(type != null)
            {
                if(type.equalsIgnoreCase("printer"))
                {
                    registerPrinterRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("oven"))
                {
                    registerOvenRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("freezer"))
                {
                    registerFreezerRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("minebay"))
                {
                    registerMineBayRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("endershop"))
                {
                    registerEnderShopRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("choppingboard"))
                {
                    registerChoppingBoardRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("toaster"))
                {
                    registerToasterRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("microwave"))
                {
                    registerMicrowaveRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("blender"))
                {
                    registerBlenderRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("washingmachine"))
                {
                    registerWashingMachineRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("dishwasher"))
                {
                    registerDishwasherRecipe(parser, realNum);
                }
                else if(type.equalsIgnoreCase("grill"))
                {
                    registerGrillRecipe(parser, realNum);
                }
                else
                {
                    if(ConfigurationHandler.api_debug)
                    {
                        RecipeUtil.printReport(parser, realNum, "type", "The recipe type '" + type + "' does not exist");
                    }
                }
            }
        }
    }

    public static void registerDefaultRecipes()
    {
        if(ConfigurationHandler.printer_1)
            RecipeRegistry.getInstance().registerPrinterRecipe(new ItemStack(Items.ENCHANTED_BOOK));
        if(ConfigurationHandler.printer_2)
            RecipeRegistry.getInstance().registerPrinterRecipe(new ItemStack(Items.WRITTEN_BOOK));

        if(ConfigurationHandler.oven_1)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
        if(ConfigurationHandler.oven_2)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.PORKCHOP), new ItemStack(Items.COOKED_PORKCHOP));
        if(ConfigurationHandler.oven_3)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.POTATO), new ItemStack(Items.BAKED_POTATO));
        if(ConfigurationHandler.oven_4)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.CHICKEN), new ItemStack(Items.COOKED_CHICKEN));
        if(ConfigurationHandler.oven_5)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.FISH, 1, 0), new ItemStack(Items.COOKED_FISH, 1, 0));
        if(ConfigurationHandler.oven_6)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.FISH, 1, 1), new ItemStack(Items.COOKED_FISH, 1, 1));
        if(ConfigurationHandler.oven_7)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(FurnitureItems.FLESH), new ItemStack(FurnitureItems.COOKED_FLESH));
        if(ConfigurationHandler.oven_8)
            RecipeRegistry.getInstance().registerOvenRecipe(new ItemStack(Items.MUTTON), new ItemStack(Items.COOKED_MUTTON));
        if(ConfigurationHandler.oven_modded)
        {
            Map<ItemStack, ItemStack> furnaceRecipes = FurnaceRecipes.instance().getSmeltingList();
            for(Map.Entry<ItemStack, ItemStack> entry : furnaceRecipes.entrySet())
            {
                ItemStack input = entry.getKey();
                ItemStack output = entry.getValue();
                if(output.getItem() instanceof ItemFood)
                {
                    ResourceLocation registryName = output.getItem().getRegistryName();
                    if(registryName != null && !registryName.getNamespace().equals("minecraft"))
                    {
                        RecipeRegistry.getInstance().registerOvenRecipe(input, output);
                    }
                }
            }
        }

        if(ConfigurationHandler.frez_1)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.WATER_BUCKET), new ItemStack(Blocks.ICE));
        if(ConfigurationHandler.frez_2)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Blocks.ICE), new ItemStack(Blocks.PACKED_ICE));
        if(ConfigurationHandler.frez_3)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.LAVA_BUCKET), new ItemStack(Blocks.OBSIDIAN));
        if(ConfigurationHandler.frez_4)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.SLIME_BALL), new ItemStack(Items.SNOWBALL));
        if(ConfigurationHandler.frez_5)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.POISONOUS_POTATO), new ItemStack(Items.POTATO));
        if(ConfigurationHandler.frez_6)
            RecipeRegistry.getInstance().registerFreezerRecipe(new ItemStack(Items.ROTTEN_FLESH), new ItemStack(FurnitureItems.FLESH));

        if(ConfigurationHandler.mine_1)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Blocks.HARDENED_CLAY, 16), new ItemStack(FurnitureItems.COIN), 1);
        if(ConfigurationHandler.mine_2)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.SKULL), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.mine_3)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.SADDLE), new ItemStack(FurnitureItems.COIN), 2);
        if(ConfigurationHandler.mine_4)
        {
            ItemStack spawnEgg = new ItemStack(Items.SPAWN_EGG);
            ItemMonsterPlacer.applyEntityIdToItemStack(spawnEgg, new ResourceLocation("horse"));
            RecipeRegistry.getInstance().registerMineBayItem(spawnEgg, new ItemStack(FurnitureItems.COIN), 8);
        }
        if(ConfigurationHandler.mine_5)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.DIAMOND_HORSE_ARMOR), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.mine_6)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(Items.EXPERIENCE_BOTTLE), new ItemStack(FurnitureItems.COIN), 2);
        if(ConfigurationHandler.mine_7)
        {
            ItemStack stack = new ItemStack(Items.FIREWORKS, 4);
            NBTTagCompound master = new NBTTagCompound();
            NBTTagCompound firework = new NBTTagCompound();
            firework.setByte("Flight", (byte) 1);
            NBTTagList list = new NBTTagList();
            NBTTagCompound data = new NBTTagCompound();
            data.setByte("Trail", (byte) 1);
            data.setByte("Type", (byte) 0);
            data.setIntArray("Colors", new int[]{11743532, 3887386});
            list.appendTag(data);
            firework.setTag("Explosions", list);
            master.setTag("Fireworks", firework);
            stack.setTagCompound(master);
            stack.setStackDisplayName(TextFormatting.RED + "Christmas" + TextFormatting.GREEN + " Firework");
            RecipeRegistry.getInstance().registerMineBayItem(stack, new ItemStack(FurnitureItems.COIN), 5);
        }
        if(ConfigurationHandler.mine_8)
        {
            ItemStack stack = new ItemStack(Items.ENCHANTED_BOOK);
            NBTTagCompound master = new NBTTagCompound();
            NBTTagList list = new NBTTagList();
            NBTTagCompound data = new NBTTagCompound();
            data.setShort("id", (short) 33);
            data.setShort("lvl", (short) 1);
            list.appendTag(data);
            master.setTag("StoredEnchantments", list);
            stack.setTagCompound(master);
            RecipeRegistry.getInstance().registerMineBayItem(stack, new ItemStack(FurnitureItems.COIN), 8);
        }
        if(ConfigurationHandler.mine_9)
        {
            ItemStack stack = new ItemStack(Items.POTIONITEM, 2);
            NBTTagCompound data = new NBTTagCompound();
            data.setString("Potion", "night_vision");
            stack.setTagCompound(data);
            RecipeRegistry.getInstance().registerMineBayItem(stack, new ItemStack(FurnitureItems.COIN), 4);
        }
        if(ConfigurationHandler.mine_10)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(FurnitureItems.RECIPE_BOOK), new ItemStack(FurnitureItems.COIN), 2);
        if(ConfigurationHandler.mine_11)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(FurnitureItems.COIN, 1), new ItemStack(Items.IRON_INGOT), 2);
        if(ConfigurationHandler.mine_12)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(FurnitureItems.COIN, 4), new ItemStack(Items.GOLD_INGOT), 4);
        if(ConfigurationHandler.mine_13)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(FurnitureItems.COIN, 8), new ItemStack(Items.DIAMOND), 4);
        if(ConfigurationHandler.mine_14)
            RecipeRegistry.getInstance().registerMineBayItem(new ItemStack(FurnitureItems.COIN, 12), new ItemStack(Items.EMERALD), 4);

        if(ConfigurationHandler.ender_1)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.FREEZER), new ItemStack(FurnitureItems.COIN), 48);
        if(ConfigurationHandler.ender_2)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.WASHING_MACHINE), new ItemStack(FurnitureItems.COIN), 44);
        if(ConfigurationHandler.ender_3)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.GRILL), new ItemStack(FurnitureItems.COIN), 32);
        if(ConfigurationHandler.ender_4)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.PRINTER), new ItemStack(FurnitureItems.COIN), 28);
        if(ConfigurationHandler.ender_5)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.MICROWAVE), new ItemStack(FurnitureItems.COIN), 20);
        if(ConfigurationHandler.ender_6)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.BLENDER), new ItemStack(FurnitureItems.COIN), 16);
        if(ConfigurationHandler.ender_7)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.TOASTER), new ItemStack(FurnitureItems.COIN), 16);
        if(ConfigurationHandler.ender_8)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.BIRD_BATH), new ItemStack(FurnitureItems.COIN), 32);
        if(ConfigurationHandler.ender_9)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.COOKIE_JAR), new ItemStack(FurnitureItems.COIN), 2);
        if(ConfigurationHandler.ender_10)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.PLATE, 2), new ItemStack(FurnitureItems.COIN), 1);
        if(ConfigurationHandler.ender_11)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.CHOPPING_BOARD), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.ender_12)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.SOAP, 16), new ItemStack(FurnitureItems.COIN), 1);
        if(ConfigurationHandler.ender_13)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.DISHWASHER, 4), new ItemStack(FurnitureItems.COIN), 44);
        if(ConfigurationHandler.ender_14)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.DAYLIGHT_DETECTOR, 2), new ItemStack(FurnitureItems.COIN), 3);
        if(ConfigurationHandler.ender_15)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.LAMP_OFF), new ItemStack(FurnitureItems.COIN), 12);
        if(ConfigurationHandler.ender_16)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.CEILING_LIGHT_OFF, 2), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.ender_17)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.LIGHT_SWITCH_OFF, 8), new ItemStack(FurnitureItems.COIN), 16);
        if(ConfigurationHandler.ender_18)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.NOTEBLOCK, 2), new ItemStack(FurnitureItems.COIN), 1);
        if(ConfigurationHandler.ender_19)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.ACTIVATOR_RAIL, 4), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.ender_20)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.GOLDEN_RAIL, 4), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.ender_21)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.DETECTOR_RAIL, 4), new ItemStack(FurnitureItems.COIN), 12);
        if(ConfigurationHandler.ender_22)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.RAIL, 4), new ItemStack(FurnitureItems.COIN), 4);
        if(ConfigurationHandler.ender_23)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.SEA_LANTERN, 2), new ItemStack(FurnitureItems.COIN), 5);
        if(ConfigurationHandler.ender_24)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.MODERN_LIGHT_OFF), new ItemStack(FurnitureItems.COIN), 12);
        if(ConfigurationHandler.ender_25)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Blocks.JUKEBOX), new ItemStack(FurnitureItems.COIN), 1);
        if(ConfigurationHandler.ender_26)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.MODERN_TV), new ItemStack(FurnitureItems.COIN), 24);
        if(ConfigurationHandler.ender_27)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.TV_REMOTE, 2), new ItemStack(FurnitureItems.COIN), 16);
        if(ConfigurationHandler.ender_28)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.TRAMPOLINE), new ItemStack(FurnitureItems.COIN), 24);
        if(ConfigurationHandler.ender_29)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Items.NAME_TAG, 4), new ItemStack(FurnitureItems.COIN), 3);
        if(ConfigurationHandler.ender_30)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(Items.LEAD), new ItemStack(FurnitureItems.COIN), 3);
        if(ConfigurationHandler.ender_31)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.DOG_FOOD, 4), new ItemStack(FurnitureItems.COIN), 8);
        if(ConfigurationHandler.ender_32)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.COIN, 1), new ItemStack(Items.IRON_INGOT), 2);
        if(ConfigurationHandler.ender_33)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.COIN, 4), new ItemStack(Items.GOLD_INGOT), 4);
        if(ConfigurationHandler.ender_34)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.COIN, 8), new ItemStack(Items.DIAMOND), 4);
        if(ConfigurationHandler.ender_35)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.COIN, 12), new ItemStack(Items.EMERALD), 4);
        if(ConfigurationHandler.ender_36)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.BOWL_FOOD_SECRET, 1), new ItemStack(FurnitureItems.COIN), 50);
        if(ConfigurationHandler.ender_37)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureItems.LIGHT_BULB, 1), new ItemStack(FurnitureItems.COIN), 6);
        if(ConfigurationHandler.ender_38)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.PIXEL_BOX, 1), new ItemStack(FurnitureItems.COIN), 20);
        if(ConfigurationHandler.ender_39)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.BIRD_HOUSE, 1), new ItemStack(FurnitureItems.COIN), 4);
        if(ConfigurationHandler.ender_40)
            RecipeRegistry.getInstance().registerEnderShopItem(new ItemStack(FurnitureBlocks.SMALL_LIGHT, 1), new ItemStack(FurnitureItems.COIN), 4);

        if(ConfigurationHandler.blen_1)
            RecipeRegistry.getInstance().registerBlenderRecipe("Fruit Crush", 4, new ItemStack[]{new ItemStack(Items.APPLE, 2), new ItemStack(Items.MELON, 4)}, new int[]{255, 58, 37});
        if(ConfigurationHandler.blen_2)
            RecipeRegistry.getInstance().registerBlenderRecipe("Veggie Juice", 6, new ItemStack[]{new ItemStack(Items.CARROT, 4), new ItemStack(Items.POTATO, 1), new ItemStack(Items.PUMPKIN_PIE, 2)}, new int[]{247, 139, 122});
        if(ConfigurationHandler.blen_3)
            RecipeRegistry.getInstance().registerBlenderRecipe("Cookies and Cream Milkshake", 4, new ItemStack[]{new ItemStack(Items.COOKIE, 2), new ItemStack(Items.MILK_BUCKET)}, new int[]{255, 214, 164});
        if(ConfigurationHandler.blen_4)
            RecipeRegistry.getInstance().registerBlenderRecipe("Energy", 8, new ItemStack[]{new ItemStack(Items.REDSTONE, 8), new ItemStack(Items.FIRE_CHARGE, 1), new ItemStack(Items.SUGAR, 16)}, new int[]{92, 23, 8});

        RecipeRegistry.getInstance().registerBlenderRecipe("Egg Nog", 5, new ItemStack[]{new ItemStack(Items.EGG, 4), new ItemStack(Items.MILK_BUCKET, 1), new ItemStack(Items.SUGAR, 2)}, new int[]{255, 234, 178});

        if(ConfigurationHandler.chop_1)
            RecipeRegistry.getInstance().registerChoppingBoardRecipe(new ItemStack(Items.BREAD), new ItemStack(FurnitureItems.BREAD_SLICE, 6));

        if(ConfigurationHandler.dish_1)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.BOW));
        if(ConfigurationHandler.dish_2)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_PICKAXE));
        if(ConfigurationHandler.dish_3)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_AXE));
        if(ConfigurationHandler.dish_4)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_SHOVEL));
        if(ConfigurationHandler.dish_5)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_HOE));
        if(ConfigurationHandler.dish_6)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.WOODEN_SWORD));
        if(ConfigurationHandler.dish_7)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_PICKAXE));
        if(ConfigurationHandler.dish_8)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_AXE));
        if(ConfigurationHandler.dish_9)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_SHOVEL));
        if(ConfigurationHandler.dish_10)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_HOE));
        if(ConfigurationHandler.dish_11)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.STONE_SWORD));
        if(ConfigurationHandler.dish_12)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_PICKAXE));
        if(ConfigurationHandler.dish_13)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_AXE));
        if(ConfigurationHandler.dish_14)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_SHOVEL));
        if(ConfigurationHandler.dish_15)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_HOE));
        if(ConfigurationHandler.dish_16)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.IRON_SWORD));
        if(ConfigurationHandler.dish_17)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_PICKAXE));
        if(ConfigurationHandler.dish_18)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_AXE));
        if(ConfigurationHandler.dish_19)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_SHOVEL));
        if(ConfigurationHandler.dish_20)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_HOE));
        if(ConfigurationHandler.dish_21)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.GOLDEN_SWORD));
        if(ConfigurationHandler.dish_22)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_PICKAXE));
        if(ConfigurationHandler.dish_23)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_AXE));
        if(ConfigurationHandler.dish_24)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_SHOVEL));
        if(ConfigurationHandler.dish_25)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_HOE));
        if(ConfigurationHandler.dish_26)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.DIAMOND_SWORD));
        if(ConfigurationHandler.dish_27)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.FISHING_ROD));
        if(ConfigurationHandler.dish_28)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.FLINT_AND_STEEL));
        if(ConfigurationHandler.dish_29)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.SHEARS));
        if(ConfigurationHandler.dish_30)
            RecipeRegistry.getInstance().registerDishwasherRecipe(new ItemStack(Items.SHIELD));

        if(ConfigurationHandler.micr_1)
            RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
        if(ConfigurationHandler.micr_2)
            RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(Items.POTATO), new ItemStack(Items.BAKED_POTATO));
        if(ConfigurationHandler.micr_3)
            RecipeRegistry.getInstance().registerMicrowaveRecipe(new ItemStack(Items.EGG), new ItemStack(Items.AIR));

        if(ConfigurationHandler.toast_1)
            RecipeRegistry.getInstance().registerToasterRecipe(new ItemStack(FurnitureItems.BREAD_SLICE), new ItemStack(FurnitureItems.TOAST));

        if(ConfigurationHandler.wash_1)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_HELMET));
        if(ConfigurationHandler.wash_2)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_CHESTPLATE));
        if(ConfigurationHandler.wash_3)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_LEGGINGS));
        if(ConfigurationHandler.wash_4)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.LEATHER_BOOTS));
        if(ConfigurationHandler.wash_5)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_HELMET));
        if(ConfigurationHandler.wash_6)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_CHESTPLATE));
        if(ConfigurationHandler.wash_7)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_LEGGINGS));
        if(ConfigurationHandler.wash_8)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.CHAINMAIL_BOOTS));
        if(ConfigurationHandler.wash_9)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_HELMET));
        if(ConfigurationHandler.wash_10)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_CHESTPLATE));
        if(ConfigurationHandler.wash_11)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_LEGGINGS));
        if(ConfigurationHandler.wash_12)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.IRON_BOOTS));
        if(ConfigurationHandler.wash_13)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_HELMET));
        if(ConfigurationHandler.wash_14)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_CHESTPLATE));
        if(ConfigurationHandler.wash_15)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_LEGGINGS));
        if(ConfigurationHandler.wash_16)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.GOLDEN_BOOTS));
        if(ConfigurationHandler.wash_17)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_HELMET));
        if(ConfigurationHandler.wash_18)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_CHESTPLATE));
        if(ConfigurationHandler.wash_19)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_LEGGINGS));
        if(ConfigurationHandler.wash_20)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.DIAMOND_BOOTS));
        if(ConfigurationHandler.wash_21)
            RecipeRegistry.getInstance().registerWashingMachineRecipe(new ItemStack(Items.ELYTRA));

        if(ConfigurationHandler.grill_1)
            RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(Items.BEEF), new ItemStack(Items.COOKED_BEEF));
        if(ConfigurationHandler.grill_2)
            RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(FurnitureItems.SAUSAGE), new ItemStack(FurnitureItems.SAUSAGE_COOKED));
        if(ConfigurationHandler.grill_3)
            RecipeRegistry.getInstance().registerGrillRecipe(new ItemStack(FurnitureItems.KEBAB), new ItemStack(FurnitureItems.KEBAB_COOKED));
    }
}
