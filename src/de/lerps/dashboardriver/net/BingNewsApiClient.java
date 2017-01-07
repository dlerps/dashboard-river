package de.lerps.dashboardriver.net;

import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.headline.Headline;
import de.lerps.dashboardriver.model.headline.Newspaper;
import de.lerps.dashboardriver.net.interfaces.IHeadlineClient;

import java.util.HashMap;
import java.util.Map;
import java.util.Collections;
import java.util.List;

import com.google.gson.*;

public class BingNewsApiClient implements IHeadlineClient
{
    private String _apiBaseUrl;
    private String _apiKey;

    private int _newsPerCategory;

    public BingNewsApiClient(String apiKey, int perCategory)
    {
        if(apiKey == null)
        {
            throw new IllegalArgumentException("Missing API Key");
        }
        else 
        {
            _apiKey = apiKey;
            _newsPerCategory = perCategory > 0 ? perCategory : 5;

            _apiBaseUrl = "https://api.cognitive.microsoft.com/bing/v5.0/news/";
        }
    }

    public Newspaper getNewspaper()
    {
        Newspaper paper = new Newspaper();
        paper.headlines = getWarriorsHeadlines();
        paper.headlines.addAll(getHSVHeadlines());
        paper.headlines.addAll(getGermanHeadlines());
        paper.headlines.addAll(getClippersHeadlines());
        paper.headlines.addAll(getWorldHeadlines());
        //paper.headlines.addAll(getMajorHeadlines());

        Collections.shuffle(paper.headlines);

        return paper;
    }

    private List<Headline> getMajorHeadlines()
    {
        StringBuilder requestUrl = new StringBuilder(_apiBaseUrl)
            .append("trendingtopics?");

        return getHeadlines(requestUrl, GlobalConfig.newsPerCategory, "Trending Topics");
    }

    private List<Headline> getWorldHeadlines()
    {
        StringBuilder requestUrl = new StringBuilder(_apiBaseUrl)
            .append("search?Category=World&mkt=en-au&");

        return getHeadlines(requestUrl, GlobalConfig.newsPerCategory, "World News");
    }

    private List<Headline> getWarriorsHeadlines()
    {
        return getSportHeadlines("golden state warriors", "en-us");
    }

    private List<Headline> getHSVHeadlines()
    {
        return getSportHeadlines("hsv", "de-de");        
    }

    private List<Headline> getClippersHeadlines()
    {
        return getSportHeadlines("clippers", "en-us");        
    }

    private List<Headline> getGermanHeadlines()
    {
        StringBuilder searchUrl = new StringBuilder(_apiBaseUrl)
            .append("search?safeSearch=Off&offset=0")
            .append("&mkt=de-de&freshness=Day&");

        return getHeadlines(searchUrl, GlobalConfig.newsPerCategory, "German");
    }

    private List<Headline> getSportHeadlines(String team, String culture)
    {
        StringBuilder searchUrl = new StringBuilder(_apiBaseUrl)
            .append("search?safeSearch=Off&offset=0&q=")
            .append(team)
            .append("&mkt=")
            .append(culture)
            .append("&freshness=Day&Category=Sports&");

        return getHeadlines(searchUrl, GlobalConfig.newsPerSportsCategory, "Sports");
    }

    private List<Headline> getHeadlines(StringBuilder urlPrototype, String category, int numberOfResults, Map<String, String> headers)
    {
        String responseBody = null;
        String url = urlPrototype
            .append("count=")
            .append(numberOfResults)
            .toString();

        headers.put("Ocp-Apim-Subscription-Key", _apiKey);

        url = url.replaceAll(" ", "%20");

        System.out.println("Getting Bing News from " + url);

        try 
        {
            ApiResponse response = HttpConnection.getRequest(url, headers);

            System.out.println(response.statusCode);

            responseBody = response.body;
            
            ElasticsearchHttpClient.logSuccessfulApiAccess("BingNews", url);
        } 
        catch (Exception e) 
        {
            ElasticsearchHttpClient.logFailedApiAccess("BingNews", url, e.getMessage());
            e.printStackTrace();
        }

        //System.out.println(responseBody);

        if(responseBody != null)
        {
            Gson gs = new GsonBuilder().create();

            try
            {
                Map<String, Object> mapped = gs.fromJson(responseBody, Map.class);
                return Headline.fromApiResponse(mapped, _newsPerCategory, category);
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

     private List<Headline> getHeadlines(StringBuilder url, int numberOfResults, String category)
     {
         return getHeadlines(url, category, numberOfResults, new HashMap<String, String>(1));
     }
}