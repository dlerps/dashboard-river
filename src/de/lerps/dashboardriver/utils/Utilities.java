package de.lerps.dashboardriver.utils;

import org.joda.time.*;
import org.joda.time.format.*;

import java.util.Map;

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

    public static String getPastIndexDate(int daysOfAge)
    {
        daysOfAge = Math.abs(daysOfAge);
        daysOfAge *= -1;

        DateTime lastWeek = DateTime.now().plusDays(daysOfAge);

        return getIndexDate(lastWeek);
    }

    public static String getIsoDate(DateTime dt)
    {
        return _isoFormatter.print(dt);
    }

    public static String getIsoDate(long t)
    {
        return _isoFormatter.print(new DateTime(t));
    }

    public static String getIsoDate()
    {
        return getIsoDate(DateTime.now());
    }

    public static double parseDoubleFromMap(Map<String, Object> map, String key)
    {
        double val = -1.0;

        if(map != null && map.keySet().contains(key))
        {
            try
            {
                //System.out.println(key + " : " + map.get(key));
                val = (double) map.get(key);
            }
            catch(Exception e1)
            {
                System.out.println("Could not parse " + key + " as double from map");
            }
        }

        return val;
    }

    public static int parseIntegerFromMap(Map<String, Object> map, String key)
    {
        int val = -1;

        if(map != null && map.containsKey(key))
        {
            try
            {
                String num = map.get(key).toString();
                val = Double.valueOf(num).intValue();
            }
            catch(Exception e1)
            {
                System.out.println("Could not parse " + key + " as double from map");
            }
        }

        return val;
    }

    public static long parseLongFromMap(Map<String, Object> map, String key)
    {
        long val = -1;

        if(map != null && map.keySet().contains(key))
        {
            try
            {
                String num = map.get(key).toString();
                num = num.replace("E", "E+0");
                
                val = Double.valueOf(num).longValue();
            }
            catch(Exception e1)
            {
                System.out.println("Could not parse " + key + " as long from map");
            }
        }

        return val;
    }

    public static String parseStringFromMap(Map<String, Object> map, String key)
    {
        String val = null;

        if(map != null && map.keySet().contains(key))
        {
            try
            {
                val = map.get(key).toString();
            }
            catch(Exception e1)
            {
                System.out.println("Could not parse " + key + " as String from map");
            }
        }

        return val;
    }

    public static String parseIsoTimeFromMap(Map<String, Object> map, String key)
    {
        String t = null;

        if(map != null && map.keySet().contains(key))
        {
            try
            {
                long numTime = 1000l * parseLongFromMap(map, key);
                t = getIsoDate(numTime);
            }
            catch(Exception e1)
            {
                System.out.println("Could not parse " + key + " as DateTime from map");
            }
        }

        return t;
    }

    private Utilities(){}
}