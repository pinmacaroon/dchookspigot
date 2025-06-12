package com.github.pinmacaroon.dchookspigot.bot.commands;

import com.github.pinmacaroon.dchook.Hook;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;

public class ListCommand {
    public static void run(SlashCommandInteractionEvent event) {
        StringBuilder list = new StringBuilder();
        list.append("""
                There are currently **%d**/%d players online:\s""".formatted(
                Hook.getGameServer().getPlayerManager().getCurrentPlayerCount(),
                Hook.getGameServer().getPlayerManager().getMaxPlayerCount()
        ));
        Hook.getGameServer().getPlayerManager().getPlayerList().forEach(
                serverPlayerEntity -> list.append("`").append(serverPlayerEntity.getName().getString()).append("` ")
        );
        event.reply(list.toString()).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean))
                .queue();
    }
}
