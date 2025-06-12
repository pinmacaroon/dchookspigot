package com.github.pinmacaroon.dchookspigot.bot.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModEnvironment;

import java.util.concurrent.atomic.AtomicInteger;

public class ModsCommand {
    public static void run(SlashCommandInteractionEvent event) {
        StringBuilder mod_list = new StringBuilder();
        AtomicInteger mods_count = new AtomicInteger();
        FabricLoader.getInstance().getAllMods().forEach(modContainer -> {
            if (modContainer.getMetadata().getEnvironment() == ModEnvironment.UNIVERSAL
                    && !modContainer.getMetadata().getType().equals("builtin")
                    && !modContainer.getMetadata().getId().startsWith("fabric")
                    && !modContainer.getMetadata().getId().equals("mixinextras")
                    && modContainer.getContainingMod().isEmpty()) {
                mod_list.append(modContainer.getMetadata().getName()).append(' ')
                        .append(modContainer.getMetadata().getVersion().getFriendlyString()).append('\n');
                mods_count.getAndIncrement();
            }
        });
        String response;
        if (mods_count.get() == 0) {
            response = "The server currently has no required mods, you can join with a vanilla client!";
        } else {
            response = "The server currently has " + mods_count.get() + " required mods:\n" + mod_list;
        }
        if (response.length() > 2000) {
            response = response.substring(0, 1995) + "[...]";
        }

        event.reply(response).setEphemeral(event.getOption("ephemeral", false, OptionMapping::getAsBoolean)).queue();
    }
}
