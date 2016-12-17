package de.lerps.dashboardriver.model;

import com.google.gson.*;

public abstract class BaseObject
{
    private transient Gson _jsonBuilder;

    public BaseObject()
    {
        _jsonBuilder =  new GsonBuilder().create();
    }

    public final String toJsonString()
    {
        return _jsonBuilder.toJson(this);
    }

    public abstract String getTypeName();
}