package com.minecedia.core.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DatabaseProvider {

    private static final String HOST = "127.0.0.1";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final int PORT = 27017;


    public static MongoClient MONGO_CLIENT;
    public static MongoDatabase MONGO_DATABASE;

    public static void initialize() {
        MongoClientURI clientURI = new MongoClientURI(DatabaseProvider.calculateURI());
        MONGO_CLIENT = new MongoClient(clientURI);
        MONGO_DATABASE = MONGO_CLIENT.getDatabase("minecedia");
    }

    private static String calculateURI() {
        StringBuilder builder = new StringBuilder("mongodb://");
        if (!USERNAME.isEmpty())
            builder.append(USERNAME);
        if (!PASSWORD.isEmpty())
            builder.append(":").append(PASSWORD);
        if (!USERNAME.isEmpty() || !PASSWORD.isEmpty())
            builder.append("@");
        builder.append(HOST);
        builder.append(":").append(PORT);
        return builder.toString();
    }
}