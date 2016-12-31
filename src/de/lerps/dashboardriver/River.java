package de.lerps.dashboardriver;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;

import java.io.InputStream;

import de.lerps.dashboardriver.net.interfaces.*;
import de.lerps.dashboardriver.net.*;
import de.lerps.dashboardriver.net.dummies.DummyWeatherClient;
import de.lerps.dashboardriver.utils.*;
import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.*;
import de.lerps.dashboardriver.model.habitica.HabiticaUserStatistics;
import de.lerps.dashboardriver.model.headline.Newspaper;
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
        else if(GlobalConfig.elasticsearchUrl != null)
        {
            List<String> arguments = Arrays.asList(args);

            if(arguments.contains("test"))
            {
                TestObject to = new TestObject("Good night world", "Whatever...");

                ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                res.add(esClient.postEntry("test0", to));
            }

            if(arguments.contains("weather"))
            {
                if(GlobalConfig.darkSkyApiKey != null)
                {
                    IWeatherClient weatherClient = new DarkSkyApiClient();
                    WeatherForecast forecast = weatherClient.getWeatherForecast();

                    if(forecast != null)
                    {
                        ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                        res.add(esClient.postEntry("weather", forecast));
                    }
                }
                else
                {
                    res.add("Missing Weather API Key in global configuration file");
                }
            }

            if(arguments.contains("headlines"))
            {
                if(GlobalConfig.bingNewsApiKey != null)
                {
                    IHeadlineClient newsClient = new BingNewsApiClient(GlobalConfig.bingNewsApiKey, GlobalConfig.newsPerCategory);
                    Newspaper news = newsClient.getNewspaper();

                    //System.out.println(news.toJsonString());

                    if(news != null)
                    {
                        ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                        res.add(esClient.postEntry("headlines", news));
                    }
                }
                else
                {
                    res.add("Missing Bing News API Key in global configuration file");
                }
            }

            if(arguments.contains("habitica"))
            {
                if(GlobalConfig.habiticaAccounts != null)
                {
                    for(HabiticaAccount acc : GlobalConfig.habiticaAccounts)
                    {
                        IHabiticaClient habiticaApi = new HabiticaApiClient(acc.userId, acc.apiKey, acc.displayName);
                        HabiticaUserStatistics stats = habiticaApi.getUserStatistics();

                        if(stats != null)
                        {
                            ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                            res.add(esClient.postEntry("habitica-" + acc.displayName.toLowerCase(), stats));
                        }
                    }
                }
                else
                {
                    res.add("Missing Habitica User Profile information in global configuration file");
                }
            }

            if(arguments.contains("cleanup"))
            {
                if(GlobalConfig.cleanupAgeDays > 0)
                {
                    ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
                    res.add(esClient.deleteOldEntries(GlobalConfig.cleanupAgeDays));
                }
                else
                {
                    res.add("Invalid cleanup settings in global configuration file");
                }
            }
        }
        else
        {
            res.add("No Elasticsearch Url provided in global configuration file");
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