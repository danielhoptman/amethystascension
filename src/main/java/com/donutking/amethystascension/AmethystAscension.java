package com.donutking.amethystascension;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetSubtitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitleTextPacket;
import net.minecraft.network.protocol.game.ClientboundSetTitlesAnimationPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;

import static net.minecraft.commands.Commands.argument;
import static net.minecraft.commands.Commands.literal;

public final class AmethystAscension implements ModInitializer {
    public static final String MOD_ID = "amethystascension";

    @Override
    public void onInitialize() {
        AmethystContent.register();
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayer player = handler.player;
            player.sendSystemMessage(Component.literal("§5✦ §dAmethyst Ascension §5✦ §7— welcome, crystal adventurer!"));
            player.sendSystemMessage(Component.literal("§7Discover your powers with §d/ascend help§7. Ranks appear above player names."));
            player.connection.send(new ClientboundSetTitlesAnimationPacket(10, 65, 20));
            player.connection.send(new ClientboundSetTitleTextPacket(Component.literal("§5✦ AMETHYST ASCENSION ✦")));
            player.connection.send(new ClientboundSetSubtitleTextPacket(Component.literal("§dYour legend begins now")));
            // Every joining player receives the default Member rank unless they already have a team.
            if (server.getScoreboard().getPlayerTeam(player.getScoreboardName()) == null) {
                setRank(server.getScoreboard(), player, "member");
            }
        });

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
            dispatcher.register(literal("ascend")
                .executes(context -> { help(context.getSource().getPlayerOrException()); return 1; })
                .then(literal("help").executes(context -> { help(context.getSource().getPlayerOrException()); return 1; }))
                .then(literal("bless").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 60, 0));
                    p.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 30, 1));
                    p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 20 * 20, 0));
                    p.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 20 * 30, 1));
                    sparkle(p, 48, 2.3);
                    msg(p, "✦ The Ascension blessing surges through you!"); return 1;
                }))
                .then(literal("pulse").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException(); sparkle(p, 64, 2.8);
                    msg(p, "✧ Amethyst pulse released!"); return 1;
                }))
                .then(literal("dash").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    p.addEffect(new MobEffectInstance(MobEffects.SPEED, 20 * 8, 4));
                    p.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 20 * 8, 2));
                    sparkle(p, 24, 1.4); msg(p, "➤ Crystal dash activated!"); return 1;
                }))
                .then(literal("heal").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    p.heal(8.0F); p.getFoodData().eat(8, 0.8F); sparkle(p, 28, 1.5);
                    msg(p, "♥ Crystal restoration complete!"); return 1;
                }))
                .then(literal("shield").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    p.addEffect(new MobEffectInstance(MobEffects.RESISTANCE, 20 * 30, 2));
                    p.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 30, 2));
                    sparkle(p, 36, 1.8); msg(p, "⬡ Amethyst shield raised!"); return 1;
                }))
                .then(literal("levitate").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    p.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 20 * 18, 0));
                    p.addEffect(new MobEffectInstance(MobEffects.JUMP_BOOST, 20 * 10, 4));
                    sparkle(p, 32, 1.8); msg(p, "↑ Gravity bends to your will!"); return 1;
                }))
                .then(literal("night").executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    p.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 180, 0));
                    p.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 20 * 60, 0));
                    msg(p, "☾ Your eyes shine with crystal light."); return 1;
                }))
                .then(literal("shards").then(argument("amount", IntegerArgumentType.integer(1, 64)).executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    int amount = IntegerArgumentType.getInteger(context, "amount");
                    give(p, new ItemStack(Items.AMETHYST_SHARD, amount));
                    msg(p, "✦ You received " + amount + " amethyst shard" + (amount == 1 ? "" : "s") + "."); return 1;
                })))
                .then(literal("give").then(argument("item", StringArgumentType.word()).executes(context -> {
                    ServerPlayer p = context.getSource().getPlayerOrException();
                    String item = StringArgumentType.getString(context, "item").toLowerCase();
                    ItemStack stack = switch (item) {
                        case "ore" -> new ItemStack(AmethystContent.AMETHYST_ORE, 16);
                        case "deepore" -> new ItemStack(AmethystContent.DEEPSLATE_AMETHYST_ORE, 16);
                        case "block" -> new ItemStack(AmethystContent.POLISHED_AMETHYST_BLOCK, 16);
                        case "blade" -> new ItemStack(AmethystContent.AMETHYST_BLADE);
                        case "wand" -> new ItemStack(AmethystContent.CRYSTAL_WAND);
                        case "core" -> new ItemStack(AmethystContent.ASCENSION_CORE);
                        case "apple" -> new ItemStack(AmethystContent.CRYSTAL_APPLE);
                        default -> ItemStack.EMPTY;
                    };
                    if (stack.isEmpty()) { msg(p, "Use: ore, deepore, block, blade, wand, core, apple"); return 0; }
                    give(p, stack); msg(p, "✦ Item granted: " + item); return 1;
                })))
                .then(literal("rank").then(literal("set")
                    .then(argument("player", StringArgumentType.word())
                        .then(argument("rank", StringArgumentType.word()).executes(context -> {
                            if (!context.getSource().getServer().getPlayerList().isOp(
                                context.getSource().getPlayerOrException().nameAndId())) {
                                context.getSource().sendFailure(Component.literal("Only an operator can change ranks.")); return 0;
                            }
                            String name = StringArgumentType.getString(context, "player");
                            String rank = StringArgumentType.getString(context, "rank").toLowerCase();
                            ServerPlayer target = context.getSource().getServer().getPlayerList().getPlayerByName(name);
                            if (target == null) { context.getSource().sendFailure(Component.literal("That player must be online.")); return 0; }
                            if (!setRank(context.getSource().getServer().getScoreboard(), target, rank)) {
                                context.getSource().sendFailure(Component.literal("Ranks: owner, admin, mod, ascendant, member")); return 0;
                            }
                            target.sendSystemMessage(Component.literal("§dYour Amethyst Ascension rank is now " + rank + "!"));
                            context.getSource().sendSuccess(() -> Component.literal("Set " + name + " rank to " + rank + "."), true); return 1;
                        })))))
            )
        );
    }

    private static void help(ServerPlayer p) {
        p.sendSystemMessage(Component.literal("§5✦ §dAMETHYST ASCENSION COMMANDS"));
        p.sendSystemMessage(Component.literal("§d/ascend bless §7— all-round blessing"));
        p.sendSystemMessage(Component.literal("§d/ascend pulse §7— crystal particle burst"));
        p.sendSystemMessage(Component.literal("§d/ascend dash §7— speed burst and leap"));
        p.sendSystemMessage(Component.literal("§d/ascend heal §7— restore health and hunger"));
        p.sendSystemMessage(Component.literal("§d/ascend shield §7— resistance and absorption"));
        p.sendSystemMessage(Component.literal("§d/ascend levitate §7— high jump and slow falling"));
        p.sendSystemMessage(Component.literal("§d/ascend night §7— crystal sight"));
        p.sendSystemMessage(Component.literal("§d/ascend give <ore|deepore|block|blade|wand|core|apple>"));
        p.sendSystemMessage(Component.literal("§d/ascend rank set <player> <rank> §7— operator only"));
    }

    private static void msg(ServerPlayer p, String message) { p.sendSystemMessage(Component.literal("§5✦ §d" + message)); }

    private static void give(ServerPlayer p, ItemStack stack) {
        if (!p.getInventory().add(stack)) {
            p.level().addFreshEntity(new ItemEntity(p.level(), p.getX(), p.getY(), p.getZ(), stack));
        }
    }

    private static void sparkle(ServerPlayer p, int count, double radius) {
        ServerLevel level = (ServerLevel) p.level();
        for (int i = 0; i < count; i++) {
            double angle = Math.PI * 2.0 * i / count;
            level.sendParticles(ParticleTypes.PORTAL, p.getX() + Math.cos(angle) * radius,
                p.getY() + 0.8 + Math.sin(angle * 3) * 0.45, p.getZ() + Math.sin(angle) * radius,
                2, 0.08, 0.12, 0.08, 0.02);
        }
        level.sendParticles(ParticleTypes.END_ROD, p.getX(), p.getY() + 1, p.getZ(), 18, 0.55, 0.7, 0.55, 0.03);
    }

    private static boolean setRank(Scoreboard scoreboard, ServerPlayer player, String rank) {
        String teamName;
        String prefix;
        switch (rank) {
            case "owner" -> { teamName = "aa_owner"; prefix = "§5[OWNER] §r"; }
            case "admin" -> { teamName = "aa_admin"; prefix = "§c[ADMIN] §r"; }
            case "mod", "moderator" -> { teamName = "aa_mod"; prefix = "§9[MOD] §r"; }
            case "ascendant", "vip" -> { teamName = "aa_ascendant"; prefix = "§d[ASCENDANT] §r"; }
            case "member", "default" -> { teamName = "aa_member"; prefix = "§7[MEMBER] §r"; }
            default -> { return false; }
        }
        PlayerTeam team = scoreboard.getPlayerTeam(teamName);
        if (team == null) team = scoreboard.addPlayerTeam(teamName);
        team.setPlayerPrefix(Component.literal(prefix));
        team.setNameTagVisibility(Team.Visibility.ALWAYS);
        scoreboard.addPlayerToTeam(player.getScoreboardName(), team);
        return true;
    }
}
