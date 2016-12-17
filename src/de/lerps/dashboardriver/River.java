package de.lerps.dashboardriver;

import java.util.Properties;
import java.io.InputStream;

import de.lerps.dashboardriver.net.*;
import de.lerps.dashboardriver.utils.ConfigLoader;
import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.*;

public class River
{
    public static void main(String[] args)
    {
        ConfigLoader loader = new ConfigLoader();
        loader.init();

        TestObject to = new TestObject("Hello World", "Whatever!");
        System.out.println(to.toJsonString());

        ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
        String res = esClient.postEntry("test0", to);

        System.out.println(res);
    }
}