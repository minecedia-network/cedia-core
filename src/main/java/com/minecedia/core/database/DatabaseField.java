package com.minecedia.core.database;

import org.bson.BsonValue;

public interface DatabaseField<T extends DatabaseObject> {

    String getField();

    BsonValue getValue(T object);
}