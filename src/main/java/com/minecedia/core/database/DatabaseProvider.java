package com.minecedia.core.database;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;

public class DatabaseProvider {

    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";
    private static final String HOST = "localhost";
    private static final int PORT = 3306;


    public static MongoClient MONGO_CLIENT;
    public static MongoDatabase MONGO_DATABASE;

    public static void initialize() {
        MongoClientURI clientURI = new MongoClientURI("mongodb://" + USERNAME + ":" + PASSWORD + "@" + HOST + ":" + PORT);
        MONGO_CLIENT = new MongoClient(clientURI);
        MONGO_DATABASE = MONGO_CLIENT.getDatabase("minecedia");
    }
}