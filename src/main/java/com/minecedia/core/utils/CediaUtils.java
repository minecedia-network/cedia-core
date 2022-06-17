package com.minecedia.core.utils;

import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CediaUtils {

    public static String getCountryCodeByPlayer(Player player) {
        InetSocketAddress socketAddress = player.getAddress();
        if (socketAddress == null) return "TR";

        InetAddress inetAddress = socketAddress.getAddress();
        if (inetAddress == null) return "TR";

        String ip = inetAddress.getHostAddress();
        if (ip == null) return "TR";
        else if (ip.equals("127.0.0.1")) return "TR";

        return CediaUtils.getCountryCodeByIP(ip);
    }

    public static String getCountryCodeByIP(String ip) {
        try (InputStream inputStream = new URL("https://ipinfo.io/" + ip + "/json").openStream()) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));

            int cp;
            StringBuilder sb = new StringBuilder();
            while ((cp = bufferedReader.read()) != -1)
                sb.append((char) cp);
            JSONObject json = new JSONObject(sb.toString());
            return json.getString("country");
        } catch (IOException e) {
            return "TR";
        }
    }
}