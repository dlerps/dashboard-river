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
            GlobalConfig.habiticaAccounts = HabiticaAccount.fromProperty(config.getProperty("habitica_accounts"));

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
        }
    }
}