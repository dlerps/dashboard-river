package de.lerps.dashboardriver.model;

import com.google.gson.*;

import de.lerps.dashboardriver.utils.Utilities;

public abstract class BaseObject
{
    private transient Gson _jsonBuilder;
    
    private String timestamp;

    public BaseObject()
    {
        _jsonBuilder =  new GsonBuilder().create();
        timestamp = Utilities.getIsoDate();
    }

    public final String toJsonString()
    {
        return _jsonBuilder.toJson(this);
    }

    public abstract String getTypeName();
}