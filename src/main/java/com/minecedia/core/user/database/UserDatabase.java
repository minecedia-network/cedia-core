package com.minecedia.core.user.database;

import com.minecedia.core.database.DatabaseProvider;
import com.minecedia.core.user.User;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Indexes;
import org.bson.BsonDocument;

public class UserDatabase {

    public static MongoCollection<BsonDocument> COLLECTION;

    public static void initialize() {
        COLLECTION = DatabaseProvider.MONGO_DATABASE.getCollection("users", BsonDocument.class);
        COLLECTION.createIndex(Indexes.ascending("uid"));
    }


    private final User user;

    public UserDatabase(User user) {
        this.user = user;
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
}