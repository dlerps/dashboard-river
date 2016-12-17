package de.lerps.dashboardriver.net;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;

import de.lerps.dashboardriver.model.*;
import de.lerps.dashboardriver.utils.Utilities;
import de.lerps.dashboardriver.GlobalConfig;

public class ElasticsearchHttpClient
{
    private String _baseUrl;

    public ElasticsearchHttpClient(String url)
    {
        _baseUrl = url;
    }

    public ElasticsearchHttpClient()
    {
        this(GlobalConfig.elasticsearchUrl);
    }

    public <T extends BaseObject> String postEntry(String index, T payload)
    {
        String url = getUrl(index, payload);

        System.out.println("POST to " + url);
        System.out.println("PAYLOAD: " + payload.toJsonString());
        
        HttpPost postRequest = new HttpPost(url);
        HttpClient httpClient = new DefaultHttpClient();

        StringBuilder response = new StringBuilder();

        StringEntity params = new StringEntity(payload.toJsonString(),"UTF-8");
        //StringEntity params = new StringEntity("{'test':'test'}","UTF-8");
        params.setContentType("application/json");

        postRequest.addHeader("content-type", "application/json");
        postRequest.setEntity(params);

        try
        {
            HttpResponse httpResponse = httpClient.execute(postRequest);

            response.append(httpResponse.getStatusLine().getStatusCode());
            response.append("\n");
            response.append(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
            
            //System.out.println(httpResponse.getStatusLine().getStatusCode());
        }
        catch(Exception ex0)
        {
            ex0.printStackTrace();
            response.append(ex0.getMessage());
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }

        return response.toString();
    }

    private String getUrl(String index, BaseObject obj)
    {
        StringBuilder url = new StringBuilder(_baseUrl)
            .append(Utilities.getIndexDate())
            .append("-")
            .append(index)
            .append("/")
            .append(obj.getTypeName());

        return url.toString();
    }
}