package de.lerps.dashboardriver;

import de.lerps.dashboardriver.net.*;
import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.*;

public class River
{
    public static void main(String[] args)
    {
        init();

        TestObject to = new TestObject("Hello World", "Whatever!");
        System.out.println(to.toJsonString());

        ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
        String res = esClient.postEntry("test0", to);

        System.out.println(res);
    }

    private static void init()
    {
        GlobalConfig.elasticsearchUrl = "http://192.168.44.100:9200/";
    }
}