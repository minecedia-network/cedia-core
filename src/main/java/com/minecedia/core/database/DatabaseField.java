package com.minecedia.core.database;

import org.bson.BsonValue;
import org.jetbrains.annotations.NotNull;

public interface DatabaseField<T extends DatabaseObject> {

    @NotNull String field();
    @NotNull BsonValue value(T object);

}