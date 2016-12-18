package de.lerps.dashboardriver.utils;

import java.util.Properties;
import java.io.InputStream;

import de.lerps.dashboardriver.GlobalConfig;

public class ConfigLoader
{
    public void init()
    {
        Properties config = new Properties();
        String propFileName = "config.properties";
        InputStream in = null;

        try
        {
            in = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (in != null) 
            {
                config.load(in);
            } 
            else 
            {
                System.out.println("Config File not found");
            }

            GlobalConfig.elasticsearchUrl = config.getProperty("elasticsearch_url");
            GlobalConfig.darkSkyApiKey = config.getProperty("dark_sky_api_key");
            String latitude = config.getProperty("weather_latitude");
            String longitude = config.getProperty("weather_longitude");

            if(latitude != null && longitude != null)
            {
                GlobalConfig.weatherLongitude = Double.parseDouble(longitude);
                GlobalConfig.weatherLatitude = Double.parseDouble(latitude);
            }
            else
            {
                GlobalConfig.weatherLongitude = 0.0;
                GlobalConfig.weatherLatitude = 0.0;
            }

            //System.out.println("Loaded: " + GlobalConfig.elasticsearchUrl);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                in.close();
            }
            catch(Exception ex0){}

            //defualt values
            //GlobalConfig.elasticsearchUrl = GlobalConfig.elasticsearchUrl != null ? GlobalConfig.elasticsearchUrl : "http://192.168.44.100:9200/";
            //GlobalConfig.darkSkyApiKey = GlobalConfig.darkSkyApiKey != null ? GlobalConfig.darkSkyApiKey : "";
        }
    }
}