package de.lerps.dashboardriver.model.headline;

import de.lerps.dashboardriver.model.BaseObject;
import java.util.List;

public class Newspaper extends BaseObject
{
    public List<Headline> headlines;

    @Override
    public String getTypeName()
    {
        return "Newspaper";
    }
}