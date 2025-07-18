package com.redxcore;

import com.redxcore.commands.ReportCommand;
import com.redxcore.discord.DiscordWebhookSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class RedxCore extends JavaPlugin {

    private static RedxCore instance;
    private DiscordWebhookSender discordWebhookSender;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        FileConfiguration config = getConfig();
        String webhookUrl = config.getString("discord.webhook");

        discordWebhookSender = new DiscordWebhookSender(webhookUrl);

        getCommand("report").setExecutor(new ReportCommand(discordWebhookSender));

        getLogger().info("RedxCore etkinleştirildi!");
    }

    @Override
    public void onDisable() {
        getLogger().info("RedxCore devre dışı bırakıldı.");
    }

    public static RedxCore getInstance() {
        return instance;
    }
}
