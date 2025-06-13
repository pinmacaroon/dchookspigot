package com.github.pinmacaroon.dchookspigot.bot.event;

import com.github.pinmacaroon.dchookspigot.bot.Bot;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class ReadyEventListener extends ListenerAdapter {

    public ReadyEventListener(Bot bot){
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Logged in as %s%s!");
    }
}
