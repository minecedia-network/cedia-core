package com.minecedia.core.user.database;

import com.minecedia.core.CediaCore;
import com.minecedia.core.user.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.BsonDocument;
import org.jetbrains.annotations.NotNull;

public class UserDatabase {

    private static MongoCollection<BsonDocument> COLLECTION;

    private final @NotNull CediaCore core;
    private final User user;

    public UserDatabase(@NotNull CediaCore core, @NotNull User user) {
        this.user = user;
        this.core = core;
    }

    public User getUser() {
        return this.user;
    }

    public void insert() {
        COLLECTION.insertOne(this.user.toBsonDocument());
    }

    public void update() {
        BsonDocument document = new BsonDocument("$set", this.user.toBsonDocument());
        COLLECTION.updateOne(this.user.toQueryDocument(), document);
    }

    public void delete() {
        COLLECTION.deleteOne(this.user.toQueryDocument());
    }

    public static void initialize(CediaCore core) {
        COLLECTION = core.databaseProvider().database().getCollection("users", BsonDocument.class);
        COLLECTION.createIndex(Indexes.ascending("uid"));
    }

    public static MongoCollection<BsonDocument> collection() {
        return COLLECTION;
    }

}