package com.redxcore.discord;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordWebhookSender {

    private final String webhookUrl;

    public DiscordWebhookSender(String webhookUrl) {
        this.webhookUrl = webhookUrl;
    }

    public void sendReport(String reporter, String target, String reason) {
        try {
            URL url = new URL(webhookUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            String jsonPayload = "{"
                    + "\"embeds\":[{"
                    + "\"title\":\"🚨 Yeni Rapor Alındı!\","
                    + "\"color\":14423100,"
                    + "\"fields\":["
                    + "{\"name\":\"👤 Raporlayan\",\"value\":\"`" + reporter + "`\",\"inline\":true},"
                    + "{\"name\":\"🎯 Hedef\",\"value\":\"`" + target + "`\",\"inline\":true},"
                    + "{\"name\":\"📝 Sebep\",\"value\":\"" + reason + "\"}"
                    + "],"
                    + "\"footer\":{\"text\":\"RedxCore | Rapor Sistemi\",\"icon_url\":\"https://i.imgur.com/RedxIcon.png\"},"
                    + "\"timestamp\":\"" + java.time.OffsetDateTime.now().toString() + "\""
                    + "}]"
                    + "}";

            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            connection.getInputStream().close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
