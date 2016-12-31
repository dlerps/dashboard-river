package de.lerps.dashboardriver.net;

import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.*;
import org.apache.http.util.*;

import java.util.HashMap;


import java.util.Map;

public class HttpConnection
{
    public static String postRequest(String url, StringEntity postParams, Map<String, String> headers)
    {
        HttpClient httpClient = new DefaultHttpClient();
        StringBuilder response = new StringBuilder();

        HttpPost postRequest = new HttpPost(url);
        postRequest.setEntity(postParams);
        
        for(String reqHeader : headers.keySet())
        {
            postRequest.addHeader(reqHeader, headers.get(reqHeader));
        }

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

    public static ApiResponse getRequest(String url, Map<String, String> headers)
    {
        ApiResponse response = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpGet getRequest = new HttpGet(url);
        
        for(String reqHeader : headers.keySet())
        {
            getRequest.addHeader(reqHeader, headers.get(reqHeader));
        }

        try
        {
            HttpResponse res = httpClient.execute(getRequest);

            response = new ApiResponse();
            response.statusCode = res.getStatusLine().getStatusCode();
            response.body = EntityUtils.toString(res.getEntity(), "UTF-8");
        }
        catch(Exception ex10)
        {
            ex10.printStackTrace();
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }

        return response;
    }

    public static ApiResponse getRequest(String url)
    {
        return getRequest(url, new HashMap<String, String>(1));
    }

    public static ApiResponse deleteRequest(String url)
    {
        ApiResponse response = null;
        HttpClient httpClient = new DefaultHttpClient();
        HttpDelete getRequest = new HttpDelete(url);

        try
        {
            HttpResponse res = httpClient.execute(getRequest);

            response = new ApiResponse();
            response.statusCode = res.getStatusLine().getStatusCode();
            response.body = EntityUtils.toString(res.getEntity(), "UTF-8");
        }
        catch(Exception ex10)
        {
            ex10.printStackTrace();
        }
        finally
        {
            httpClient.getConnectionManager().shutdown();
        }

        return response;
    }
}