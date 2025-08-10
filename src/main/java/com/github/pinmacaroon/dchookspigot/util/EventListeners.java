package com.github.pinmacaroon.dchookspigot.util;

import com.github.pinmacaroon.dchookspigot.Dchookspigot;
import com.github.pinmacaroon.dchookspigot.bot.Bot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.utils.MarkdownSanitizer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.world.WorldLoadEvent;

import java.lang.reflect.Array;
import java.net.URI;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class EventListeners implements Listener {

    private boolean hasReadyMessageBeenSent = false;

    private static void setupStatChannel(Bot bot, String id, String name, int retry){
        if (Dchookspigot.CONFIG.getBoolean("functions.status_channels."+id+".enabled")){
            Guild guild = bot.getJDA().getGuildById(Dchookspigot.guild_id);
            if(guild == null){
                System.out.println("guild == null");
                return;
            }
            VoiceChannel channel = guild.getVoiceChannelById(
                    Dchookspigot.CONFIG.getLong("functions.status_channels."+id+".channel")
            );
            if (channel == null) {
                System.out.println("channel == null");
                return;
            }
            System.out.println("channel");
            // TODO fix the damn rate limit thingamabop
            channel.getManager().setName(name).queue(x -> {}, throwable -> {});
        }
    }

    private static void setupStatChannel(Bot bot, String id, String name){
        setupStatChannel(bot, id, name, 0);
    }

    public static void onInit() {
        setupStatChannel(Dchookspigot.BOT, "player_count", "Players: 0/" + Dchookspigot.getPlugin(
                Dchookspigot.class).getServer().getMaxPlayers()
        );
        setupStatChannel(Dchookspigot.BOT, "server_status", "Status: Online");
        if (!Dchookspigot.CONFIG.getBoolean("messages.server.starting.allowed")) return;
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Dchookspigot.CONFIG.getString("messages.server.starting.message", "") + "**");
        request_body.put("username", "server");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Unformat.sanitize(MarkdownSanitizer.escape(event.getJoinMessage())) + "**");
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        setupStatChannel(Dchookspigot.BOT, "player_count",
                "Players: " +
                        Dchookspigot.getPlugin(Dchookspigot.class).getServer().getOnlinePlayers().size() +
                        "/" + Dchookspigot.getPlugin(Dchookspigot.class).getServer().getMaxPlayers()
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Unformat.sanitize(MarkdownSanitizer.escape(event.getQuitMessage())) + "**");
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        setupStatChannel(Dchookspigot.BOT, "player_count",
                "Players: " +
                        (Dchookspigot.getPlugin(Dchookspigot.class).getServer().getOnlinePlayers().size()-1) +
                        "/" + Dchookspigot.getPlugin(Dchookspigot.class).getServer().getMaxPlayers()
        );
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Unformat.sanitize(MarkdownSanitizer.escape(event.getDeathMessage())) + "**");
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onAdvancementDone(PlayerAdvancementDoneEvent event) {
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "** %s has done the advancement [%s]!**".formatted(
                MarkdownSanitizer.escape(event.getPlayer().getName()),
                event.getAdvancement().getDisplay().getTitle()
        ));
        request_body.put("username", "game");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if(event.getMessage().strip().endsWith("//") && Dchookspigot.CONFIG.getBoolean("functions.allow_ooc_messages"))
            return;
        HashMap<String, String> request_body = new HashMap<>();

        if(WaypointParser.isWaypoint(event.getMessage())){
            List<Object> list = WaypointParser.constructWaypointFromString(event.getMessage());
            request_body.put("content", MessageFormat.format(
                    "*Shared a waypoint called **{0} ({1})** at `x={2} y={3} z={4}` from {5}!*",
                    list.get(0),
                    list.get(1),
                    Array.get(list.get(2), 0),
                    Array.get(list.get(2), 1),
                    Array.get(list.get(2), 2),
                    list.get(3)
            ));
        } else request_body.put("content", MarkdownSanitizer.escape(event.getMessage()));
        request_body.put("username", event.getPlayer().getName());
        request_body.put("avatar_url", "https://crafthead.net/helm/" + event.getPlayer().getUniqueId());

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    @EventHandler
    public void onWorldLoad(WorldLoadEvent event) {
        if(this.hasReadyMessageBeenSent) return;
        if (!(Dchookspigot.getPlugin(Dchookspigot.class).getServer().getWorlds().lastIndexOf(event.getWorld())
                == Dchookspigot.getPlugin(Dchookspigot.class).getServer().getWorlds().size()-1)) return;
        if (!Dchookspigot.CONFIG.getBoolean("messages.server.started.allowed")) return;
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Dchookspigot.CONFIG.getString("messages.server.started.message", "") + "**");
        request_body.put("username", "server");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
        if (Dchookspigot.CONFIG.getBoolean("functions.promotions.enabled")) PromotionProvider.sendAutomaticPromotion(
                URI.create(Dchookspigot.CONFIG.getString("webhook.url", ""))
        );
        this.hasReadyMessageBeenSent = true;
    }

    @EventHandler
    public void onPluginDisable(PluginDisableEvent event) {
        if (!Dchookspigot.CONFIG.getBoolean("messages.server.stopping.allowed")) return;
        if (!event.getPlugin().getClass().equals(Dchookspigot.class)) return;
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Dchookspigot.CONFIG.getString("messages.server.stopping.message", "") + "**");
        request_body.put("username", "server");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static void onStop() {
        if (!Dchookspigot.CONFIG.getBoolean("messages.server.stopped.allowed")) return;
        HashMap<String, String> request_body = new HashMap<>();
        request_body.put("content", "**" + Dchookspigot.CONFIG.getString("messages.server.stopped.message", "") + "**");
        request_body.put("username", "server");

        HttpRequest post = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(Dchookspigot.GSON.toJson(request_body)))
                .uri(URI.create(Dchookspigot.CONFIG.getString("webhook.url", "")))
                .header("Content-Type", "application/json")
                .build();

        try {
            Dchookspigot.HTTPCLIENT.sendAsync(post, HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
