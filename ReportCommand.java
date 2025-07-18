package com.redxcore.commands;

import com.redxcore.discord.DiscordWebhookSender;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReportCommand implements CommandExecutor {

    private final DiscordWebhookSender webhookSender;

    public ReportCommand(DiscordWebhookSender webhookSender) {
        this.webhookSender = webhookSender;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Bu komut sadece oyuncular tarafından kullanılabilir.");
            return true;
        }

        if (args.length < 2) {
            sender.sendMessage("§cKullanım: /report <oyuncu> <sebep>");
            return true;
        }

        Player reporter = (Player) sender;
        String targetName = args[0];
        String reason = String.join(" ", java.util.Arrays.copyOfRange(args, 1, args.length));

        Player target = Bukkit.getPlayerExact(targetName);

        if (target == null) {
            reporter.sendMessage("§cOyuncu bulunamadı: " + targetName);
            return true;
        }

        webhookSender.sendReport(reporter.getName(), targetName, reason);
        reporter.sendMessage("§aRaporunuz başarıyla iletildi!");

        return true;
    }
}
