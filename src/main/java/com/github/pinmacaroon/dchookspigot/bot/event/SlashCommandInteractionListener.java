package com.github.pinmacaroon.dchookspigot.bot.event;

import com.github.pinmacaroon.dchookspigot.bot.Bot;
import com.github.pinmacaroon.dchookspigot.bot.commands.*;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.FileUpload;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class SlashCommandInteractionListener extends ListenerAdapter {
    private final Bot BOT;

    public SlashCommandInteractionListener(Bot bot) {
        this.BOT = bot;
    }

    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event) {
        if (event.getGuild() == null) return;
        if (event.getGuild().getIdLong() != this.BOT.getGUILD_ID()) return;
        switch (event.getName()) {
            case "time" : TimeCommand.run(event); break;
            case "mods" : ModsCommand.run(event); break;
            case "list" : ListCommand.run(event); break;
            case "stat" : StatCommand.run(event); break;
            case "about" : AboutCommand.run(event); break;
            default : event.reply(
                    "An internal error occurred! Please send a bug report: <https://pinmacaroon.github.io/hook/links.html>"
                    ).setEphemeral(true)
                    .addFiles(FileUpload.fromData(new File("https://pinmacaroon.github.io/hook/res/works.png")))
                    .queue();
        }
    }
}
