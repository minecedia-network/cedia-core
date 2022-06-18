package com.minecedia.core.database;

import org.bson.BsonDocument;

public interface DatabaseObject {

    BsonDocument toBsonDocument();
}