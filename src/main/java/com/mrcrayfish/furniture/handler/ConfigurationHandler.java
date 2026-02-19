package com.mrcrayfish.furniture.handler;

import java.io.File;
import com.mrcrayfish.furniture.api.RecipeRegistry;
import com.mrcrayfish.furniture.api.Recipes;
import com.mrcrayfish.furniture.integration.crafttweaker.CraftTweakerIntegration;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler
{
    public static final String CATEGORY_API = "recipe-api";
    public static final String CATEGORY_INTEGRATION_SETTINGS = "integration-settings";
    public static final String CATEGORY_RECIPE_SETTINGS = "recipe-settings";
    public static final String CATEGORY_SETTINGS = "settings";

    public static Configuration config;

    public static boolean allowAllDishwasher = false;
    public static boolean allowAllWashingMachine = false;
    public static boolean api_debug = false;
    public static boolean integrationBiomesOPlenty = true;
    public static boolean integrationRustic = true;
    public static boolean integrationThaumcraft = true;
    public static boolean integrationSuiKeCherry = true;
    public static boolean integrationPalebloom = true;
    public static boolean legacyFurniture = true;
    public static boolean mirrorEnabled = true;
    public static float mirrorFov = 80F;
    public static int mirrorQuality = 64;
    public static boolean seasonalPresents = true;
    public static String[] trustedUrlDomains = {};
    public static String trustedUrlDomainsListType = "BLACKLIST";
    public static int maxFileSize = 2;

    public static String[] items = {};

    public static boolean printer_1 = true, printer_2 = true;
    public static boolean oven_1 = true, oven_2 = true, oven_3 = true, oven_4 = true, oven_5 = true, oven_6 = true, oven_7 = true, oven_8 = true, oven_modded = true;
    public static boolean frez_1 = true, frez_2 = true, frez_3 = true, frez_4 = true, frez_5 = true, frez_6 = true;
    public static boolean mine_1 = true, mine_2 = true, mine_3 = true, mine_4 = true, mine_5 = true, mine_6 = true, mine_7 = true, mine_8 = true, mine_9 = true, mine_10 = true, mine_11 = true, mine_12 = true, mine_13 = true, mine_14 = true;
    public static boolean ender_1 = true, ender_2 = true, ender_3 = true, ender_4 = true, ender_5 = true, ender_6 = true, ender_7 = true, ender_8 = true, ender_9 = true, ender_10 = true;
    public static boolean ender_11 = true, ender_12 = true, ender_13 = true, ender_14 = true, ender_15 = true, ender_16 = true, ender_17 = true, ender_18 = true, ender_19 = true, ender_20 = true;
    public static boolean ender_21 = true, ender_22 = true, ender_23 = true, ender_24 = true, ender_25 = true, ender_26 = true, ender_27 = true, ender_28 = true, ender_29 = true, ender_30 = true, ender_31 = true, ender_32 = true, ender_33 = true, ender_34 = true, ender_35 = true, ender_36 = true, ender_37 = true, ender_38 = true, ender_39 = true, ender_40 = true, ender_41 = true, ender_42 = true;
    public static boolean blen_1 = true, blen_2 = true, blen_3 = true, blen_4 = true;
    public static boolean chop_1 = true;
    public static boolean dish_1 = true, dish_2 = true, dish_3 = true, dish_4 = true, dish_5 = true, dish_6 = true, dish_7 = true, dish_8 = true, dish_9 = true, dish_10 = true;
    public static boolean dish_11 = true, dish_12 = true, dish_13 = true, dish_14 = true, dish_15 = true, dish_16 = true, dish_17 = true, dish_18 = true, dish_19 = true, dish_20 = true;
    public static boolean dish_21 = true, dish_22 = true, dish_23 = true, dish_24 = true, dish_25 = true, dish_26 = true, dish_27 = true, dish_28 = true, dish_29 = true, dish_30 = true;

    public static boolean micr_1 = true, micr_2 = true, micr_3 = true;

    public static boolean toast_1 = true;

    public static boolean wash_1 = true, wash_2 = true, wash_3 = true, wash_4 = true, wash_5 = true, wash_6 = true, wash_7 = true, wash_8 = true, wash_9 = true, wash_10 = true;
    public static boolean wash_11 = true, wash_12 = true, wash_13 = true, wash_14 = true, wash_15 = true, wash_16 = true, wash_17 = true, wash_18 = true, wash_19 = true, wash_20 = true, wash_21 = true;

    public static boolean grill_1 = true, grill_2 = true, grill_3 = true;

    public static void init(File file)
    {
        if (config == null)
        {
            config = new Configuration(file);
            loadConfig(false);
        }
    }

    public static void loadConfig(boolean shouldChange)
    {
        allowAllDishwasher = config.getBoolean("allow-all-dishwasher", CATEGORY_SETTINGS, false, "Whether to allow all tools to be cleaned inside a dishwasher.");
        allowAllWashingMachine = config.getBoolean("allow-all-washing-machine", CATEGORY_SETTINGS, false, "Whether to allow all armor to be cleaned inside a washing machine.");
        api_debug = config.getBoolean("recipe-api-debug", CATEGORY_SETTINGS, false, "Whether to print out information about RecipeAPI. Recommended 'true' for people trying to add custom recipes.");
        legacyFurniture = config.getBoolean("legacy-furniture", CATEGORY_SETTINGS, true, "Whether furniture from older versions of the mod should be enabled. Disable to save block IDs.");
        mirrorEnabled = config.getBoolean("mirror-enabled", CATEGORY_SETTINGS, true, "Whether the mirror should render a reflection.");
        mirrorFov = config.getFloat("mirror-fov", CATEGORY_SETTINGS, 80F, 10F, 100F, "The field of view for the mirror.");
        mirrorQuality = config.getInt("mirror-quality", CATEGORY_SETTINGS, 64, 16, 512, "The resolution for the mirror. Higher numbers result in better quality but worse performance.");
        seasonalPresents = config.getBoolean("seasonal-presents", CATEGORY_SETTINGS, true, "Whether opening presents should display a Happy Christmas notification.");
        trustedUrlDomains = config.getStringList("trusted-url-domains", CATEGORY_SETTINGS, trustedUrlDomains, "List of trusted domains to download images for the TV and Photo Frame. For example, the domain of the URL (https://i.imgur.com/Jvh1OQm.jpeg) is i.imgur.com");
        trustedUrlDomainsListType = config.getString("trusted-url-domains-list-type", CATEGORY_SETTINGS, "BLACKLIST", "Which list type the list of trusted domains is. Can be either WHITELIST or BLACKLIST.");
        maxFileSize = config.getInt("max-file-size", CATEGORY_SETTINGS, 2, 1, 100, "The maximum file size of images and GIFs in MB the TV and Photo Frame can download.");

        integrationBiomesOPlenty = config.getBoolean("integration-biomesoplenty", CATEGORY_INTEGRATION_SETTINGS, true, "Whether furniture with support for Biomes O' Plenty should be enabled. Disabled automatically when Cosmetic Wood is detected.");
        integrationRustic = config.getBoolean("integration-rustic", CATEGORY_INTEGRATION_SETTINGS, true, "Whether furniture with support for Rustic should be enabled. Disabled automatically when Cosmetic Wood is detected.");
        integrationThaumcraft = config.getBoolean("integration-Thaumcraft", CATEGORY_INTEGRATION_SETTINGS, true, "Whether furniture with support for Thaumcraft should be enabled. Disabled automatically when Cosmetic Wood is detected.");
        integrationSuiKeCherry = config.getBoolean("integration-SuiKeCherry", CATEGORY_INTEGRATION_SETTINGS, true, "Whether furniture with support for SuiKeCherry should be enabled. Disabled automatically when Cosmetic Wood is detected.");
        integrationPalebloom = config.getBoolean("integration-palebloom", CATEGORY_INTEGRATION_SETTINGS, true, "Whether furniture with support for Palebloom should be enabled. Disabled automatically when Cosmetic Wood is detected.");

        items = config.getStringList("custom-recipes", CATEGORY_API, items, "Insert custom recipes here");

        config.addCustomCategoryComment(CATEGORY_RECIPE_SETTINGS, "Enable or disable the default recipes");
        config.addCustomCategoryComment(CATEGORY_API, "RecipeAPI Configuration. How to use: https://mrcrayfishs-furniture-mod.fandom.com/wiki/Configuration");

        updateEnabledRecipes();

        if (config.hasChanged() && shouldChange)
        {
            Recipes.clearLocalRecipes();
            Recipes.clearCommRecipes();
            RecipeRegistry.registerDefaultRecipes();
            RecipeRegistry.registerConfigRecipes();
            Recipes.addCommRecipesToLocal();
            if (Loader.isModLoaded("crafttweaker"))
            {
                CraftTweakerIntegration.apply();
            }
            Recipes.updateDataList();
        }
        config.save();
    }

    private static void updateEnabledRecipes()
    {
        printer_1 = config.getBoolean("printer-1", CATEGORY_RECIPE_SETTINGS, printer_1, "Enchanted Book");
        printer_2 = config.getBoolean("printer-2", CATEGORY_RECIPE_SETTINGS, printer_2, "Written Book");
        oven_1 = config.getBoolean("oven-1", CATEGORY_RECIPE_SETTINGS, oven_1, "Beef -> Cooked Beef");
        oven_2 = config.getBoolean("oven-2", CATEGORY_RECIPE_SETTINGS, oven_2, "Porkchop -> Cooked Porkchop");
        oven_3 = config.getBoolean("oven-3", CATEGORY_RECIPE_SETTINGS, oven_3, "Potato -> Cooked Potato");
        oven_4 = config.getBoolean("oven-4", CATEGORY_RECIPE_SETTINGS, oven_4, "Chicken -> Cooked Chicken");
        oven_5 = config.getBoolean("oven-5", CATEGORY_RECIPE_SETTINGS, oven_5, "Raw Fish -> Cooked Fish");
        oven_6 = config.getBoolean("oven-6", CATEGORY_RECIPE_SETTINGS, oven_6, "Raw Salmon -> Cooked Salmon");
        oven_7 = config.getBoolean("oven-7", CATEGORY_RECIPE_SETTINGS, oven_7, "Flesh -> Cooked Flesh");
        oven_8 = config.getBoolean("oven-8", CATEGORY_RECIPE_SETTINGS, oven_8, "Mutton -> Cooked Mutton");
        oven_modded = config.getBoolean("oven_modded", CATEGORY_RECIPE_SETTINGS, oven_modded, "Whether to support most modded food recipes in the oven.");
        frez_1 = config.getBoolean("freezer-1", CATEGORY_RECIPE_SETTINGS, frez_1, "Water Bucket -> Ice");
        frez_2 = config.getBoolean("freezer-2", CATEGORY_RECIPE_SETTINGS, frez_2, "Ice -> Packet Ice");
        frez_3 = config.getBoolean("freezer-3", CATEGORY_RECIPE_SETTINGS, frez_3, "Lava Bucket -> Obsidian");
        frez_4 = config.getBoolean("freezer-4", CATEGORY_RECIPE_SETTINGS, frez_4, "Slime Ball -> Snow Ball");
        frez_5 = config.getBoolean("freezer-5", CATEGORY_RECIPE_SETTINGS, frez_5, "Poisonous Potato -> Potato");
        frez_6 = config.getBoolean("freezer-6", CATEGORY_RECIPE_SETTINGS, frez_6, "Rotten Flesh -> Flesh");
        mine_1 = config.getBoolean("minebay-1", CATEGORY_RECIPE_SETTINGS, mine_1, "16 Hardened Clay for 1 Coin");
        mine_2 = config.getBoolean("minebay-2", CATEGORY_RECIPE_SETTINGS, mine_2, "1 Skeleton Skull for 8 Coin");
        mine_3 = config.getBoolean("minebay-3", CATEGORY_RECIPE_SETTINGS, mine_3, "1 Saddle for 2 Coin");
        mine_4 = config.getBoolean("minebay-4", CATEGORY_RECIPE_SETTINGS, mine_4, "1 Horse Spawn Egg for 8 Coin");
        mine_5 = config.getBoolean("minebay-5", CATEGORY_RECIPE_SETTINGS, mine_5, "1 Diamond Horse Armour for 8 Coin");
        mine_6 = config.getBoolean("minebay-6", CATEGORY_RECIPE_SETTINGS, mine_6, "1 Experience Bottle for 2 Coin");
        mine_7 = config.getBoolean("minebay-7", CATEGORY_RECIPE_SETTINGS, mine_7, "4 Christmas Firework for 5 Coin");
        mine_8 = config.getBoolean("minebay-8", CATEGORY_RECIPE_SETTINGS, mine_8, "1 Silk Touch Book for 8 Coin");
        mine_9 = config.getBoolean("minebay-9", CATEGORY_RECIPE_SETTINGS, mine_9, "2 Night Vision Potion for 4 Coin");
        mine_10 = config.getBoolean("minebay-10", CATEGORY_RECIPE_SETTINGS, mine_10, "1 Recipe Book for 1 Coin");
        mine_11 = config.getBoolean("minebay-11", CATEGORY_RECIPE_SETTINGS, mine_11, "2 Iron Ingot for 1 Coin");
        mine_12 = config.getBoolean("minebay-12", CATEGORY_RECIPE_SETTINGS, mine_12, "4 Gold Ingot for 4 Coin");
        mine_13 = config.getBoolean("minebay-13", CATEGORY_RECIPE_SETTINGS, mine_13, "4 Diamonds for 8 Coin");
        mine_14 = config.getBoolean("minebay-14", CATEGORY_RECIPE_SETTINGS, mine_14, "4 Emerald for 12 Coin");
        ender_1 = config.getBoolean("ender-1", CATEGORY_RECIPE_SETTINGS, ender_1, "1 Freezer for 48 Coin");
        ender_2 = config.getBoolean("ender-2", CATEGORY_RECIPE_SETTINGS, ender_2, "1 Washing Machine for 44 Coin");
        ender_3 = config.getBoolean("ender-3", CATEGORY_RECIPE_SETTINGS, ender_3, "1 Grill for 32 Coin");
        ender_4 = config.getBoolean("ender-4", CATEGORY_RECIPE_SETTINGS, ender_4, "1 Printer for 28 Coin");
        ender_5 = config.getBoolean("ender-5", CATEGORY_RECIPE_SETTINGS, ender_5, "1 Microwave for 20 Coin");
        ender_6 = config.getBoolean("ender-6", CATEGORY_RECIPE_SETTINGS, ender_6, "1 Blender for 16 Coin");
        ender_7 = config.getBoolean("ender-7", CATEGORY_RECIPE_SETTINGS, ender_7, "1 Toaster for 16 Coin");
        ender_8 = config.getBoolean("ender-8", CATEGORY_RECIPE_SETTINGS, ender_8, "1 Bird Bath for 2 Coin");
        ender_9 = config.getBoolean("ender-9", CATEGORY_RECIPE_SETTINGS, ender_9, "1 Cookie Jar for 2 Coin");
        ender_10 = config.getBoolean("ender-10", CATEGORY_RECIPE_SETTINGS, ender_10, "2 Plate for 12 Coin");
        ender_11 = config.getBoolean("ender-11", CATEGORY_RECIPE_SETTINGS, ender_11, "1 Chopping Board for 8 Coin");
        ender_12 = config.getBoolean("ender-12", CATEGORY_RECIPE_SETTINGS, ender_12, "16 Soap for 1 Coin");
        ender_13 = config.getBoolean("ender-13", CATEGORY_RECIPE_SETTINGS, ender_13, "1 Dishwasher for 44 Coin");
        ender_14 = config.getBoolean("ender-14", CATEGORY_RECIPE_SETTINGS, ender_14, "2 Daylight Sensor for 3 Coin");
        ender_15 = config.getBoolean("ender-15", CATEGORY_RECIPE_SETTINGS, ender_15, "1 Lamp for 12 Coin");
        ender_16 = config.getBoolean("ender-16", CATEGORY_RECIPE_SETTINGS, ender_16, "2 Ceiling Light for 8 Coin");
        ender_17 = config.getBoolean("ender-17", CATEGORY_RECIPE_SETTINGS, ender_17, "8 Light Switch for 16 Coin");
        ender_18 = config.getBoolean("ender-18", CATEGORY_RECIPE_SETTINGS, ender_18, "2 Note Block for 4 Coin");
        ender_19 = config.getBoolean("ender-19", CATEGORY_RECIPE_SETTINGS, ender_19, "4 Activator Rail for 8 Coin");
        ender_20 = config.getBoolean("ender-20", CATEGORY_RECIPE_SETTINGS, ender_20, "4 Golden Rail for 8 Coin");
        ender_21 = config.getBoolean("ender-21", CATEGORY_RECIPE_SETTINGS, ender_21, "4 Detector Rail for 12 Coin");
        ender_22 = config.getBoolean("ender-22", CATEGORY_RECIPE_SETTINGS, ender_22, "4 Rail for 4 Coin");
        ender_23 = config.getBoolean("ender-23", CATEGORY_RECIPE_SETTINGS, ender_23, "2 Sea Lantern for 5 Coin");
        ender_24 = config.getBoolean("ender-24", CATEGORY_RECIPE_SETTINGS, ender_24, "1 Modern Light for 12 Coin");
        ender_25 = config.getBoolean("ender-25", CATEGORY_RECIPE_SETTINGS, ender_25, "1 Radio for 24 Coin");
        ender_26 = config.getBoolean("ender-26", CATEGORY_RECIPE_SETTINGS, ender_26, "1 Modern TV for 24 Coin");
        ender_27 = config.getBoolean("ender-27", CATEGORY_RECIPE_SETTINGS, ender_27, "2 TV Remote for 16 Coin");
        ender_28 = config.getBoolean("ender-28", CATEGORY_RECIPE_SETTINGS, ender_28, "1 Trampoline for 24 Coin");
        ender_29 = config.getBoolean("ender-29", CATEGORY_RECIPE_SETTINGS, ender_29, "4 Name Tag for 3 Coin");
        ender_30 = config.getBoolean("ender-30", CATEGORY_RECIPE_SETTINGS, ender_30, "4 Lead for 3 Coin");
        ender_31 = config.getBoolean("ender-31", CATEGORY_RECIPE_SETTINGS, ender_31, "4 Dog Food for 8 Coin");
        ender_32 = config.getBoolean("ender-32", CATEGORY_RECIPE_SETTINGS, ender_32, "2 Iron Ingot for 1 Coin");
        ender_33 = config.getBoolean("ender-33", CATEGORY_RECIPE_SETTINGS, ender_33, "4 Gold Ingot for 4 Coin");
        ender_34 = config.getBoolean("ender-34", CATEGORY_RECIPE_SETTINGS, ender_34, "4 Diamonds for 8 Coin");
        ender_35 = config.getBoolean("ender-35", CATEGORY_RECIPE_SETTINGS, ender_35, "4 Emerald for 12 Coin");
        ender_36 = config.getBoolean("ender-36", CATEGORY_RECIPE_SETTINGS, ender_36, "1 Bowl Food Secret for 50 Coin");
        ender_37 = config.getBoolean("ender-37", CATEGORY_RECIPE_SETTINGS, ender_37, "1 Light Bulb for 6 Coin");
        ender_38 = config.getBoolean("ender-38", CATEGORY_RECIPE_SETTINGS, ender_38, "1 Pixel Box for 20 Coin");
        ender_39 = config.getBoolean("ender-39", CATEGORY_RECIPE_SETTINGS, ender_39, "1 Bird House for 4 Coin");
        ender_40 = config.getBoolean("ender-40", CATEGORY_RECIPE_SETTINGS, ender_40, "1 Small Light for 4 Coin");
        ender_41 = config.getBoolean("ender-41", CATEGORY_RECIPE_SETTINGS, ender_41, "4 Cat Food for 6 Coin");
        ender_42 = config.getBoolean("ender-42", CATEGORY_RECIPE_SETTINGS, ender_42, "1 Figurine Mrcrayfish for 20 Coin");
        blen_1 = config.getBoolean("blender-1", CATEGORY_RECIPE_SETTINGS, blen_1, "Fruit Crush");
        blen_2 = config.getBoolean("blender-2", CATEGORY_RECIPE_SETTINGS, blen_2, "Veggie Juice");
        blen_3 = config.getBoolean("blender-3", CATEGORY_RECIPE_SETTINGS, blen_3, "Fishy Blend");
        blen_4 = config.getBoolean("blender-4", CATEGORY_RECIPE_SETTINGS, blen_4, "Energy Drink");
        chop_1 = config.getBoolean("chopping-board-1", CATEGORY_RECIPE_SETTINGS, chop_1, "Bread -> 6 Bread Slices");
        dish_1 = config.getBoolean("dishwasher-1", CATEGORY_RECIPE_SETTINGS, dish_1, "Bow");
        dish_2 = config.getBoolean("dishwasher-2", CATEGORY_RECIPE_SETTINGS, dish_2, "Wooden Pickaxe");
        dish_3 = config.getBoolean("dishwasher-3", CATEGORY_RECIPE_SETTINGS, dish_3, "Wooden Axe");
        dish_4 = config.getBoolean("dishwasher-4", CATEGORY_RECIPE_SETTINGS, dish_4, "Wooden Shovel");
        dish_5 = config.getBoolean("dishwasher-5", CATEGORY_RECIPE_SETTINGS, dish_5, "Wooden Hoe");
        dish_6 = config.getBoolean("dishwasher-6", CATEGORY_RECIPE_SETTINGS, dish_6, "Wooden Sword");
        dish_7 = config.getBoolean("dishwasher-7", CATEGORY_RECIPE_SETTINGS, dish_7, "Stone Pickaxe");
        dish_8 = config.getBoolean("dishwasher-8", CATEGORY_RECIPE_SETTINGS, dish_8, "Stone Axe");
        dish_9 = config.getBoolean("dishwasher-9", CATEGORY_RECIPE_SETTINGS, dish_9, "Stone Shovel");
        dish_10 = config.getBoolean("dishwasher-10", CATEGORY_RECIPE_SETTINGS, dish_10, "Stone Hoe");
        dish_11 = config.getBoolean("dishwasher-11", CATEGORY_RECIPE_SETTINGS, dish_11, "Stone Sword");
        dish_12 = config.getBoolean("dishwasher-12", CATEGORY_RECIPE_SETTINGS, dish_12, "Iron Pickaxe");
        dish_13 = config.getBoolean("dishwasher-13", CATEGORY_RECIPE_SETTINGS, dish_13, "Iron Axe");
        dish_14 = config.getBoolean("dishwasher-14", CATEGORY_RECIPE_SETTINGS, dish_14, "Iron Shovel");
        dish_15 = config.getBoolean("dishwasher-15", CATEGORY_RECIPE_SETTINGS, dish_15, "Iron Hoe");
        dish_16 = config.getBoolean("dishwasher-16", CATEGORY_RECIPE_SETTINGS, dish_16, "Iron Sword");
        dish_17 = config.getBoolean("dishwasher-17", CATEGORY_RECIPE_SETTINGS, dish_17, "Golden Pickaxe");
        dish_18 = config.getBoolean("dishwasher-18", CATEGORY_RECIPE_SETTINGS, dish_18, "Golden Axe");
        dish_19 = config.getBoolean("dishwasher-19", CATEGORY_RECIPE_SETTINGS, dish_19, "Golden Shovel");
        dish_20 = config.getBoolean("dishwasher-20", CATEGORY_RECIPE_SETTINGS, dish_20, "Golden Hoe");
        dish_21 = config.getBoolean("dishwasher-21", CATEGORY_RECIPE_SETTINGS, dish_21, "Golden Sword");
        dish_22 = config.getBoolean("dishwasher-22", CATEGORY_RECIPE_SETTINGS, dish_22, "Diamond Pickaxe");
        dish_23 = config.getBoolean("dishwasher-23", CATEGORY_RECIPE_SETTINGS, dish_23, "Diamond Axe");
        dish_24 = config.getBoolean("dishwasher-24", CATEGORY_RECIPE_SETTINGS, dish_24, "Diamond Shovel");
        dish_25 = config.getBoolean("dishwasher-25", CATEGORY_RECIPE_SETTINGS, dish_25, "Diamond Hoe");
        dish_26 = config.getBoolean("dishwasher-26", CATEGORY_RECIPE_SETTINGS, dish_26, "Diamond Sword");
        dish_27 = config.getBoolean("dishwasher-27", CATEGORY_RECIPE_SETTINGS, dish_27, "Fishing Rod");
        dish_28 = config.getBoolean("dishwasher-28", CATEGORY_RECIPE_SETTINGS, dish_28, "Flint and Steel");
        dish_29 = config.getBoolean("dishwasher-29", CATEGORY_RECIPE_SETTINGS, dish_29, "Shears");
        dish_30 = config.getBoolean("dishwasher-30", CATEGORY_RECIPE_SETTINGS, dish_30, "Shield");

        micr_1 = config.getBoolean("microwave-1", CATEGORY_RECIPE_SETTINGS, micr_1, "Beef -> Cooked Beef");
        micr_2 = config.getBoolean("microwave-2", CATEGORY_RECIPE_SETTINGS, micr_2, "Potato -> Baked Potato");
        micr_3 = config.getBoolean("microwave-3", CATEGORY_RECIPE_SETTINGS, micr_3, "Egg -> ???");

        toast_1 = config.getBoolean("toast-2", CATEGORY_RECIPE_SETTINGS, toast_1, "Bread Slice -> Toast");

        wash_1 = config.getBoolean("washing-machine-1", CATEGORY_RECIPE_SETTINGS, wash_1, "Leather Helmet");
        wash_2 = config.getBoolean("washing-machine-2", CATEGORY_RECIPE_SETTINGS, wash_2, "Leather Chestplate");
        wash_3 = config.getBoolean("washing-machine-3", CATEGORY_RECIPE_SETTINGS, wash_3, "Leather Leggings");
        wash_4 = config.getBoolean("washing-machine-4", CATEGORY_RECIPE_SETTINGS, wash_4, "Leather Boots");
        wash_5 = config.getBoolean("washing-machine-5", CATEGORY_RECIPE_SETTINGS, wash_5, "Chainmail Helmet");
        wash_6 = config.getBoolean("washing-machine-6", CATEGORY_RECIPE_SETTINGS, wash_6, "Chainmail Chestplate");
        wash_7 = config.getBoolean("washing-machine-7", CATEGORY_RECIPE_SETTINGS, wash_7, "Chainmail Leggings");
        wash_8 = config.getBoolean("washing-machine-8", CATEGORY_RECIPE_SETTINGS, wash_8, "Chainmail Boots");
        wash_9 = config.getBoolean("washing-machine-9", CATEGORY_RECIPE_SETTINGS, wash_9, "Iron Helmet");
        wash_10 = config.getBoolean("washing-machine-10", CATEGORY_RECIPE_SETTINGS, wash_10, "Iron Chestplate");
        wash_11 = config.getBoolean("washing-machine-11", CATEGORY_RECIPE_SETTINGS, wash_11, "Iron Leggings");
        wash_12 = config.getBoolean("washing-machine-12", CATEGORY_RECIPE_SETTINGS, wash_12, "Iron Boots");
        wash_13 = config.getBoolean("washing-machine-13", CATEGORY_RECIPE_SETTINGS, wash_13, "Golden Helmet");
        wash_14 = config.getBoolean("washing-machine-14", CATEGORY_RECIPE_SETTINGS, wash_14, "Golden Chestplate");
        wash_15 = config.getBoolean("washing-machine-15", CATEGORY_RECIPE_SETTINGS, wash_15, "Golden Leggings");
        wash_16 = config.getBoolean("washing-machine-16", CATEGORY_RECIPE_SETTINGS, wash_16, "Golden Boots");
        wash_17 = config.getBoolean("washing-machine-17", CATEGORY_RECIPE_SETTINGS, wash_17, "Diamond Helmet");
        wash_18 = config.getBoolean("washing-machine-18", CATEGORY_RECIPE_SETTINGS, wash_18, "Diamond Chestplate");
        wash_19 = config.getBoolean("washing-machine-19", CATEGORY_RECIPE_SETTINGS, wash_19, "Diamond Leggings");
        wash_20 = config.getBoolean("washing-machine-20", CATEGORY_RECIPE_SETTINGS, wash_20, "Diamond Boots");
        wash_21 = config.getBoolean("washing-machine-21", CATEGORY_RECIPE_SETTINGS, wash_21, "Elytra");

        grill_1 = config.getBoolean("grill-1", CATEGORY_RECIPE_SETTINGS, grill_1, "Beef -> Cooked Beef");
        grill_2 = config.getBoolean("grill-2", CATEGORY_RECIPE_SETTINGS, grill_2, "Sausage -> Cooked Sausage");
        grill_3 = config.getBoolean("grill-3", CATEGORY_RECIPE_SETTINGS, grill_3, "Raw Kebab -> Cooked Kebab");
    }

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent eventArgs)
    {
        if (eventArgs.getModID().equals("cfm"))
        {
            loadConfig(true);
        }
    }
}
