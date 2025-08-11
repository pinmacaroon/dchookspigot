package com.github.pinmacaroon.dchookspigot;

import com.github.pinmacaroon.dchookspigot.bot.Bot;
import com.github.pinmacaroon.dchookspigot.util.EventListeners;
import com.github.pinmacaroon.dchookspigot.util.VersionChecker;
import com.github.zafarkhaja.semver.Version;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Random;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public final class Dchookspigot extends JavaPlugin {
    public static final Random RANDOM = new Random(Instant.now().getEpochSecond());
    public static final Pattern WEBHOOK_URL_PATTERN = Pattern.compile(
            "^https:\\/\\/(ptb\\.|canary\\.)?discord\\.com\\/api\\/webhooks\\/\\d+\\/.+$"
    );
    public static final Version VERSION = new Version.Builder()
            .setMajorVersion(1)
            .setMinorVersion(0)
            .setPatchVersion(2)
            .setBuildMetadata("spigot", "1", "8", "8")
            //.setPreReleaseVersion("alpha", "2")
            .build();
    private static Bot BOT;
    public static final HttpClient HTTPCLIENT = HttpClient.newHttpClient();
    public static Logger LOGGER;
    public static FileConfiguration CONFIG;
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private void createConfig() {
        try {
            if (!this.getDataFolder().exists()) {
                this.getDataFolder().mkdirs();
            }
            File file = new File(this.getDataFolder(), "config.yml");
            if (!file.exists()) {
                this.getLogger().info("Config.yml not found, creating!");

                this.getConfig().options().copyDefaults(true);

                this.saveDefaultConfig();
            } else {
                this.getLogger().info("Config.yml found, loading!");
            }
        } catch (Exception e) {
            //TODO replace shitstain error handling with normal error handling
            e.printStackTrace();
        }
    }

    @Override
    public void onEnable() {
        this.createConfig();
        LOGGER = this.getLogger();
        CONFIG = this.getConfig();
        if (!this.getConfig().getBoolean("functions.mod_enabled")) {
            this.getLogger().info("hook mod was explicitly told to not operate!");
            return;
        }

        if(!WEBHOOK_URL_PATTERN.matcher(this.getConfig().getString("webhook.url", "")).find()){
            this.getLogger().info("webhook url was not a valid discord api endpoint, thus the mod cant operate!");
            return;
        }

        if(this.getConfig().getBoolean("functions.bot.enabled")){
            try {
                BOT = new Bot(this.getConfig().getString("functions.bot.token", ""));
            } catch (Exception e){
                this.getLogger().info("couldn't initialise bot, two way chat disabled");
                this.getLogger().info("%s:%S".formatted(e.getClass().getName(), e.getMessage()));
                return;
            }
        }

        try {
            HttpRequest get_webhook = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(this.getConfig().getString("webhook.url", "")))
                    .build();

            HttpResponse<String> response = HTTPCLIENT.send(get_webhook, HttpResponse.BodyHandlers.ofString());
            int status = response.statusCode();
            JsonObject body = GSON.fromJson(response.body(), JsonObject.class);
            if(status != 200){
                this.getLogger().info(
                        "the webhook was not found or couldn't reach discord servers! discord said: '%s'".formatted(
                        body.get("message").getAsString()
                ));
                return;
            }
            Thread bot_rutime_thread = new Thread(() -> {
                while (BOT == null) {
                    Thread.onSpinWait();
                }
                BOT.setGUILD_ID(body.get("guild_id").getAsLong());
                BOT.setCHANNEL_ID(body.get("channel_id").getAsLong());
            });
            bot_rutime_thread.start();
        } catch (Exception e) {
            this.getLogger().info("%s:%S".formatted(e.getClass().getName(), e.getMessage()));
            throw new RuntimeException(e);
        }

        if(this.getConfig().getBoolean("functions.update")) VersionChecker.checkVersion();

        this.getServer().getPluginManager().registerEvents(new EventListeners(), this);

        this.getLogger().info("all checks succeeded, starting webhook managing! version: " + VERSION);
        if(this.getConfig().getBoolean("functions.promotions.enabled")){
            this.getLogger().info(
                    "promotions were disabled by config. please consider turning them back on to support the mod!");
        }

        EventListeners.onInit();
    }

    @Override
    public void onDisable() {
        BOT.stop();
        EventListeners.onStop();
        //HTTPCLIENT.shutdown();
        this.saveConfig();
    }
}
