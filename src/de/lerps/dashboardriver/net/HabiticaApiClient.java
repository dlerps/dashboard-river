package de.lerps.dashboardriver.net;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import de.lerps.dashboardriver.model.habitica.HabiticaUserStatistics;
import de.lerps.dashboardriver.net.HttpConnection;
import de.lerps.dashboardriver.net.interfaces.IHabiticaClient;

import com.google.gson.*;

public class HabiticaApiClient implements IHabiticaClient
{
    private String _apiKey;
    private String _apiBaseUrl;
    private String _userId;
    private String _displayName;

    public HabiticaApiClient(String userId, String apiKey, String displayName) throws IllegalArgumentException
    {
        if(apiKey == null)
        {
            throw new IllegalArgumentException("No API key provided");
        }

        if(userId == null)
        {
            throw new IllegalArgumentException("No user id provided");
        }

        _apiBaseUrl = "https://habitica.com/api/v3/";

        _apiKey = apiKey;
        _userId = userId;
        _displayName = displayName;
    }

    @Override
    public HabiticaUserStatistics getUserStatistics()
    {
        String url = _apiBaseUrl + "user";
        String responseBody = null;

        Map<String, String> headers = new HashMap<String, String>(2);
        headers.put("x-api-user", _userId);
        headers.put("x-api-key", _apiKey);

        System.out.println("Requesting " + _displayName + " Habitica Data from " + url);

        try 
        {
            ApiResponse response = HttpConnection.getRequest(url, headers);

            System.out.println(response.statusCode);

            responseBody = response.body;
            
            ElasticsearchHttpClient.logSuccessfulApiAccess("Habitica", url);
        } 
        catch (Exception e) 
        {
            ElasticsearchHttpClient.logFailedApiAccess("Habitica", url, e.getMessage());
            e.printStackTrace();
        }

        //System.out.println(responseBody);

        if(responseBody != null)
        {
            Gson gs = new GsonBuilder().create();

            try
            {
                Map<String, Object> mapped = gs.fromJson(responseBody, Map.class);
                HabiticaUserStatistics stats = HabiticaUserStatistics.fromApiResponse(mapped);

                return stats;
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }
}