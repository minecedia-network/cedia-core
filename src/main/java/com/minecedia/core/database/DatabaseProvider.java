package com.minecedia.core.database;

import com.hakan.core.utils.yaml.HYaml;
import com.minecedia.core.CediaCore;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class DatabaseProvider {

    private final @NotNull JavaPlugin plugin;

    private @NotNull MongoClient mongoClient;
    private @NotNull MongoDatabase database;

    public DatabaseProvider(@NotNull JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize() {
        HYaml yaml = HYaml.create(plugin, "settings.yml", "settings.yml");
        MongoClientURI clientURI = new MongoClientURI(calculateURI(yaml));

        this.mongoClient = new MongoClient(clientURI);
        this.database = connect(yaml.getString("database.name"));
    }

    public @NotNull MongoDatabase connect(String databaseName) {
        return mongoClient.getDatabase(databaseName);
    }

    private @NotNull String calculateURI(HYaml yaml) {
        String host = yaml.getString("database.host");
        String username = yaml.getString("database.username");
        String password = yaml.getString("database.password");
        int port = yaml.getInt("database.port");

        StringBuilder builder = new StringBuilder("mongodb://");

        if (!username.isEmpty())
            builder.append(username);
        if (!password.isEmpty())
            builder.append(":").append(password);
        if (!username.isEmpty() || !password.isEmpty())
            builder.append("@");

        builder.append(host);
        builder.append(":").append(port);

        return builder.toString();
    }
}