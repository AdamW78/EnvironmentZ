package net.environmentz.init;

import net.environmentz.access.PlayerEnvAccess;
import net.environmentz.network.EnvironmentServerPacket;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;
import java.util.Iterator;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;

public class CommandInit {

    public static void init() {
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            dispatcher.register((CommandManager.literal("info").requires((serverCommandSource) -> {
                return serverCommandSource.hasPermissionLevel(3);
            })).then(CommandManager.literal("affection").then(CommandManager.argument("targets", EntityArgumentType.players()).executes((commandContext) -> {
                return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 0);
            }))).then(CommandManager.literal("resistance").then(CommandManager.argument("targets", EntityArgumentType.players()).executes((commandContext) -> {
                return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 1);
            }))).then(CommandManager.literal("protection").then(CommandManager.argument("targets", EntityArgumentType.players()).executes((commandContext) -> {
                return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 2);
            }))).then(CommandManager.literal("temperature").then(CommandManager.argument("targets", EntityArgumentType.players()).executes((commandContext) -> {
                return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 3);
            }))));
        });

        // CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
        // dispatcher.register((CommandManager.literal("environment").requires((serverCommandSource) -> {
        // return serverCommandSource.hasPermissionLevel(3);
        // })).then(CommandManager.literal("affection")
        // .then(CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.argument("affection", BoolArgumentType.bool()).executes((commandContext) -> {
        // return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "hot", BoolArgumentType.getBool(commandContext, "affection"),
        // 0);
        // })))).then(CommandManager.literal("resistance").then(
        // CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.argument("resistance", IntegerArgumentType.integer()).executes((commandContext) -> {
        // return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 1);
        // }))))
        // .then(CommandManager.literal("protection").then(
        // CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.argument("protection", IntegerArgumentType.integer()).executes((commandContext) -> {
        // return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 2);
        // }))))
        // .then(CommandManager.literal("temperature").then(
        // CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.argument("temperature", IntegerArgumentType.integer()).executes((commandContext) -> {
        // return executeInfo(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), 3);
        // })))));
        // });

        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated, environment) -> {
            dispatcher.register((CommandManager.literal("environment").requires((serverCommandSource) -> {
                return serverCommandSource.hasPermissionLevel(3);
            })).then(CommandManager.literal("affection").then(CommandManager.argument("targets", EntityArgumentType.players())
                    .then(CommandManager.literal("hot").then(CommandManager.argument("affection", BoolArgumentType.bool()).executes((commandContext) -> {
                        return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "hot", BoolArgumentType.getBool(commandContext, "affection"),
                                0);
                    }))).then(CommandManager.literal("cold").then(CommandManager.argument("affection", BoolArgumentType.bool()).executes((commandContext) -> {
                        return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "cold", BoolArgumentType.getBool(commandContext, "affection"),
                                0);
                    }))))).then(CommandManager.literal("resistance").then(CommandManager.argument("targets", EntityArgumentType.players())
                            .then(CommandManager.literal("hot").then(CommandManager.argument("resistance", IntegerArgumentType.integer()).executes((commandContext) -> {
                                return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "hot",
                                        IntegerArgumentType.getInteger(commandContext, "resistance"), 1);
                            }))).then(CommandManager.literal("cold").then(CommandManager.argument("resistance", IntegerArgumentType.integer()).executes((commandContext) -> {
                                return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "cold",
                                        IntegerArgumentType.getInteger(commandContext, "resistance"), 1);
                            })))))
                    .then(CommandManager.literal("protection").then(CommandManager.argument("targets", EntityArgumentType.players())
                            .then(CommandManager.literal("hot").then(CommandManager.argument("protection", IntegerArgumentType.integer()).executes((commandContext) -> {
                                return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "hot",
                                        IntegerArgumentType.getInteger(commandContext, "protection"), 2);
                            }))).then(CommandManager.literal("cold").then(CommandManager.argument("protection", IntegerArgumentType.integer()).executes((commandContext) -> {
                                return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "cold",
                                        IntegerArgumentType.getInteger(commandContext, "protection"), 2);
                            })))))
                    .then(CommandManager.literal("temperature").then(
                            CommandManager.argument("targets", EntityArgumentType.players()).then(CommandManager.argument("temperature", IntegerArgumentType.integer()).executes((commandContext) -> {
                                return executeEnvCommand(commandContext.getSource(), EntityArgumentType.getPlayers(commandContext, "targets"), "",
                                        IntegerArgumentType.getInteger(commandContext, "temperature"), 3);
                            })))));
        });
    }

    // 0: affection; 1: resistance; 2: protection; 3: temperature
    private static int executeEnvCommand(ServerCommandSource source, Collection<ServerPlayerEntity> targets, String environment, Object object, int mode) {
        Iterator<ServerPlayerEntity> var3 = targets.iterator();

        while (var3.hasNext()) {
            ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) var3.next();
            if (mode == 0) {
                if (environment.equals("hot"))
                    ((PlayerEnvAccess) serverPlayerEntity).setHotEnvAffected((boolean) object);
                else if (environment.equals("cold"))
                    ((PlayerEnvAccess) serverPlayerEntity).setColdEnvAffected((boolean) object);
                EnvironmentServerPacket.writeS2CSyncEnvPacket(serverPlayerEntity, ((PlayerEnvAccess) serverPlayerEntity).isHotEnvAffected(),
                        ((PlayerEnvAccess) serverPlayerEntity).isColdEnvAffected());
                source.sendFeedback(Text.translatable("commands.environment.affection_changed", serverPlayerEntity.getDisplayName()), true);
            } else if (mode == 1) {
                if (environment.equals("hot"))
                    ((PlayerEnvAccess) serverPlayerEntity).setPlayerHeatResistance((int) object);
                else if (environment.equals("cold"))
                    ((PlayerEnvAccess) serverPlayerEntity).setPlayerColdResistance((int) object);
                source.sendFeedback(Text.translatable("commands.environment.resistance_changed", serverPlayerEntity.getDisplayName()), true);
            } else if (mode == 2) {
                if (environment.equals("hot"))
                    ((PlayerEnvAccess) serverPlayerEntity).setPlayerHeatProtectionAmount((int) object);
                else if (environment.equals("cold"))
                    ((PlayerEnvAccess) serverPlayerEntity).setPlayerColdProtectionAmount((int) object);
                source.sendFeedback(Text.translatable("commands.environment.protection_changed", serverPlayerEntity.getDisplayName()), true);
            } else if (mode == 3) {
                ((PlayerEnvAccess) serverPlayerEntity).setPlayerTemperature((int) object);
                source.sendFeedback(Text.translatable("commands.environment.temperature_changed", serverPlayerEntity.getDisplayName()), true);
                EnvironmentServerPacket.writeS2CTemperaturePacket(serverPlayerEntity, ((PlayerEnvAccess) serverPlayerEntity).getPlayerTemperature(),
                        ((PlayerEnvAccess) serverPlayerEntity).getPlayerWetIntensityValue());
            }
        }

        return targets.size();
    }

    // 0: affection; 1: resistance; 2: protection; 3: temperature
    private static int executeInfo(ServerCommandSource source, Collection<ServerPlayerEntity> targets, int info) {
        Iterator<ServerPlayerEntity> var3 = targets.iterator();
        // loop over players
        while (var3.hasNext()) {
            ServerPlayerEntity serverPlayerEntity = var3.next();
            if (info == 0)
                source.sendFeedback(Text.translatable("commands.environment.affection", serverPlayerEntity.getDisplayName(), ((PlayerEnvAccess) serverPlayerEntity).isHotEnvAffected(),
                        ((PlayerEnvAccess) serverPlayerEntity).isColdEnvAffected()), true);
            else if (info == 1)
                source.sendFeedback(Text.translatable("commands.environment.resistance", serverPlayerEntity.getDisplayName(), ((PlayerEnvAccess) serverPlayerEntity).getPlayerHeatResistance(),
                        ((PlayerEnvAccess) serverPlayerEntity).getPlayerColdResistance()), true);
            else if (info == 2)
                source.sendFeedback(Text.translatable("commands.environment.protection", serverPlayerEntity.getDisplayName(), ((PlayerEnvAccess) serverPlayerEntity).getPlayerHeatProtectionAmount(),
                        ((PlayerEnvAccess) serverPlayerEntity).getPlayerColdProtectionAmount()), true);
            else if (info == 3)
                source.sendFeedback(Text.translatable("commands.environment.temperature", serverPlayerEntity.getDisplayName(), ((PlayerEnvAccess) serverPlayerEntity).getPlayerTemperature()), true);

        }
        return 1;
    }

}