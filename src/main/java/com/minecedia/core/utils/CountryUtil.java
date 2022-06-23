package com.minecedia.core.utils;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CountryUtil {

    public static @NotNull String findCountryCode(Player player) {
        Objects.requireNonNull(player, "Player cannot be null!");

        InetSocketAddress socketAddress = player.getAddress();
        if (socketAddress == null) return "TR";

        InetAddress inetAddress = socketAddress.getAddress();
        if (inetAddress == null) return "TR";

        String ip = inetAddress.getHostAddress();
        if (ip == null || ip.equals("127.0.0.1")) return "TR";

        return findCountryCode(ip);
    }

    public static @NotNull String findCountryCode(String ip) {
        Objects.requireNonNull(ip, "IP cannot be null!");

        try (InputStream inputStream = new URL("https://ipinfo.io/" + ip + "/json").openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();

            int cp;

            while ((cp = bufferedReader.read()) != -1) {
                sb.append((char) cp);
            }

            return new JSONObject(sb.toString()).getString("country");
        } catch (IOException e) {
            return "TR";
        }

    }

}