package com.donutking.amethystascension;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.core.Registry;

public final class AmethystContent {
    private AmethystContent() {}

    public static final Block AMETHYST_ORE = registerBlock("amethyst_ore", BlockBehaviour.Properties.of().strength(3.2F).requiresCorrectToolForDrops().sound(SoundType.STONE));
    public static final Block DEEPSLATE_AMETHYST_ORE = registerBlock("deepslate_amethyst_ore", BlockBehaviour.Properties.of().strength(4.5F).requiresCorrectToolForDrops().sound(SoundType.DEEPSLATE));
    public static final Block POLISHED_AMETHYST_BLOCK = registerBlock("polished_amethyst_block", BlockBehaviour.Properties.of().strength(2.0F).sound(SoundType.AMETHYST));
    public static final Block CRYSTAL_ALTAR = registerBlock("crystal_altar", BlockBehaviour.Properties.of().strength(3.0F).lightLevel(state -> 9).sound(SoundType.AMETHYST));

    public static final Item AMETHYST_BLADE = registerItem("amethyst_blade", new Item.Properties().stacksTo(1).durability(900));
    public static final Item CRYSTAL_WAND = registerItem("crystal_wand", new Item.Properties().stacksTo(1).durability(500));
    public static final Item ASCENSION_CORE = registerItem("ascension_core", new Item.Properties().stacksTo(16));
    public static final Item CRYSTAL_APPLE = registerItem("crystal_apple", new Item.Properties().stacksTo(16));

    private static Block registerBlock(String name, BlockBehaviour.Properties properties) {
        Identifier id = Identifier.fromNamespaceAndPath(AmethystAscension.MOD_ID, name);
        ResourceKey<Block> key = ResourceKey.create(Registries.BLOCK, id);
        Block block = new Block(properties.setId(key));
        Registry.register(Registries.BLOCK, id, block);
        Registry.register(Registries.ITEM, id, new BlockItem(block, new Item.Properties().setId(ResourceKey.create(Registries.ITEM, id))));
        return block;
    }

    private static Item registerItem(String name, Item.Properties properties) {
        Identifier id = Identifier.fromNamespaceAndPath(AmethystAscension.MOD_ID, name);
        return Registry.register(Registries.ITEM, id, new Item(properties.setId(ResourceKey.create(Registries.ITEM, id))));
    }

    public static void register() { /* Static fields register all content. */ }
}
