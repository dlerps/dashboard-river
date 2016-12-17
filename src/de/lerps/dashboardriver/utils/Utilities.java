package de.lerps.dashboardriver.utils;

import org.joda.time.*;
import org.joda.time.format.*;

public class Utilities
{
    private static DateTimeFormatter _isoFormatter = ISODateTimeFormat.dateTime();
    private static DateTimeFormatter _indexDateFormatter = DateTimeFormat.forPattern("yyyyMMdd");
    

    public static String getIndexDate(DateTime dt)
    {
        return _indexDateFormatter.print(dt);
    }

    public static String getIndexDate()
    {
        return getIndexDate(DateTime.now());
    }

    public static String getIsoDate(DateTime dt)
    {
        return _isoFormatter.print(dt);
    }

    public static String getIsoDate()
    {
        return getIsoDate(DateTime.now());
    }

    private Utilities(){}
}