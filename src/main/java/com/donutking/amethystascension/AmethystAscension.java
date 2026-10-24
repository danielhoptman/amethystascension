package com.donutking.amethystascension;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public final class AmethystAscension implements ModInitializer {
    public static final String MOD_ID = "amethystascension";

    @Override
    public void onInitialize() {
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            handler.player.sendSystemMessage(Component.literal("§5✦ Amethyst Ascension §d— your adventure begins."));
            handler.player.sendSystemMessage(Component.literal("§7Try §d/ascend help §7to discover your crystal powers."));
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(literal("ascend")
                .executes(context -> {
                    context.getSource().sendSuccess(
                        () -> Component.literal("§5Amethyst Ascension §7| §d/ascend bless §8• §d/ascend pulse §8• §d/ascend shards <1-16>"),
                        false
                    );
                    return 1;
                })
                .then(literal("help").executes(context -> {
                    context.getSource().sendSuccess(
                        () -> Component.literal("§dbless §7gives a crystal blessing; §dpulse §7releases a sparkle burst; §dshards <1-16> §7gives amethyst shards."),
                        false
                    );
                    return 1;
                }))
                .then(literal("bless").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 45, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 25, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 20 * 15, 0));
                    player.sendSystemMessage(Component.literal("§d✦ The amethyst crystal blesses you with sight, speed, and resilience!"));
                    return 1;
                }))
                .then(literal("pulse").executes(context -> {
                    ServerPlayer player = context.getSource().getPlayerOrException();
                    for (int i = 0; i < 24; i++) {
                        double angle = Math.PI * 2.0 * i / 24.0;
                        ((ServerLevel) player.level()).sendParticles(
                            ParticleTypes.PORTAL,
                            player.getX() + Math.cos(angle) * 1.7,
                            player.getY() + 1.0,
                            player.getZ() + Math.sin(angle) * 1.7,
                            3, 0.08, 0.08, 0.08, 0.02
                        );
                    }
                    player.sendSystemMessage(Component.literal("§5✧ Amethyst pulse released!"));
                    return 1;
                }))
                .then(literal("shards")
                    .then(argument("amount", IntegerArgumentType.integer(1, 16)).executes(context -> {
                        ServerPlayer player = context.getSource().getPlayerOrException();
                        int amount = IntegerArgumentType.getInteger(context, "amount");
                        ItemStack stack = new ItemStack(Items.AMETHYST_SHARD, amount);
                        if (!player.getInventory().add(stack)) {
                            player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), stack));
                        }
                        player.sendSystemMessage(Component.literal("§d✦ You received " + amount + " amethyst shard" + (amount == 1 ? "" : "s") + "."));
                        return 1;
                    }))
                )
            )
        );
    }
}
