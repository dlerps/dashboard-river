package de.lerps.dashboardriver.net;

import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;

import java.util.HashMap;
import java.util.Map;

import de.lerps.dashboardriver.model.*;
import de.lerps.dashboardriver.net.ApiResponse;
import de.lerps.dashboardriver.net.HttpConnection;
import de.lerps.dashboardriver.utils.Utilities;
import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.logging.ApiAccessLog;

public class ElasticsearchHttpClient
{
    private static final String ACCESS_LOG_NAME = "api-access-log";

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

        System.out.println("POST to Elasticsearch API: " + url);
        System.out.println("PAYLOAD: " + payload.toJsonString());

        Map<String, String> headers = new HashMap<String, String>(1);
        headers.put("content-type", "application/json");

        StringEntity params = new StringEntity(payload.toJsonString(),"UTF-8");
        params.setContentType("application/json");

        return HttpConnection.postRequest(url, params, headers);
    }

    public String deleteOldEntries(int daysOfAge)
    {
        String deleteUrl = new StringBuilder(_baseUrl)
            .append(Utilities.getPastIndexDate(daysOfAge))
            .append("-")
            .append("*")
            .toString();

        ApiResponse response = HttpConnection.deleteRequest(deleteUrl);

        return new StringBuilder("DELETE Request to ")
            .append(deleteUrl)
            .append(" returned:\n")
            .append(response.statusCode)
            .append("\n")
            .append(response.body)
            .toString();
    }
    
    public static void logSuccessfulApiAccess(String api, String apiUrl)
    {
        ApiAccessLog apiAccess = new ApiAccessLog();
        apiAccess.apiName = api;
        apiAccess.url = apiUrl;
        apiAccess.success = true;

        ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
        esClient.postEntry(ACCESS_LOG_NAME, apiAccess);
    }

    public static void logFailedApiAccess(String api, String apiUrl, String error)
    {
        ApiAccessLog apiAccess = new ApiAccessLog();
        apiAccess.apiName = api;
        apiAccess.url = apiUrl;
        apiAccess.success = false;
        apiAccess.error = error;

        ElasticsearchHttpClient esClient = new ElasticsearchHttpClient();
        esClient.postEntry(ACCESS_LOG_NAME, apiAccess);
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