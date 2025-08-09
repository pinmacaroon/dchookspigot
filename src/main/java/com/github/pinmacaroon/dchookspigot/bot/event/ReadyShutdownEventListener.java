package com.github.pinmacaroon.dchookspigot.bot.event;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import com.github.pinmacaroon.dchookspigot.bot.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.events.session.ShutdownEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyShutdownEventListener extends ListenerAdapter {

    public ReadyShutdownEventListener(Bot bot){
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Logged in as %s%s!");
    }

    @Override
    public void onShutdown(@NotNull ShutdownEvent event) {

    }
}
