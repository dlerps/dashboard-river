package de.lerps.dashboardriver;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import java.io.InputStream;

import de.lerps.dashboardriver.net.*;
import de.lerps.dashboardriver.net.dummies.DummyWeatherClient;
import de.lerps.dashboardriver.utils.ConfigLoader;
import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.*;
import de.lerps.dashboardriver.model.weather.WeatherForecast;

public class River
{
    public static void main(String[] args)
    {
        ConfigLoader loader = new ConfigLoader();
        loader.init();

        List<String> res = new ArrayList<String>(2);

        if(args == null || args.length == 0)
        {
            res.add("Insufficient arguments");
        }
        else
        {
            List<String> arguments = Arrays.asList(args);

            if(arguments.contains("test"))
            {
                TestObject to = new TestObject("Hello World", "Whatever!");

                ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                res.add(esClient.postEntry("test0", to));
            }
            if(arguments.contains("weather"))
            {
                IWeatherClient weatherClient = new DummyWeatherClient();
                WeatherForecast forecast = weatherClient.getWeatherForecast();

                ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                res.add(esClient.postEntry("dummy-weather", forecast));
            }
        }
        
        outputResponses(res);
    }

    private static void outputResponses(List<String> responses)
    {
        for(String res : responses)
        {
            System.out.println(res);
        }
    }
}