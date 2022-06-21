package com.minecedia.core.database;

import com.hakan.core.utils.yaml.HYaml;
import com.minecedia.core.CediaCore;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DatabaseProvider {

    public static MongoClient MONGO_CLIENT;
    public static MongoDatabase DEFAULT_DATABASE;

    public static void initialize(CediaCore plugin) {
        HYaml yaml = HYaml.create(plugin, "settings.yml", "settings.yml");
        MongoClientURI clientURI = new MongoClientURI(DatabaseProvider.calculateURI(yaml));
        MONGO_CLIENT = new MongoClient(clientURI);
        DEFAULT_DATABASE = DatabaseProvider.connect(yaml.getString("database.name"));
    }

    public static MongoDatabase connect(String databaseName) {
        return MONGO_CLIENT.getDatabase(databaseName);
    }

    private static String calculateURI(HYaml yaml) {
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