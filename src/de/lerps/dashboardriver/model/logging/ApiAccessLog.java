package de.lerps.dashboardriver.model.logging;

import de.lerps.dashboardriver.model.BaseObject;

public class ApiAccessLog extends BaseObject
{
    public String apiName;
    public String url;

    public boolean success;

    public String error;

    @Override
    public String getTypeName()
    {
        return "ApiAccessLog";
    }
}