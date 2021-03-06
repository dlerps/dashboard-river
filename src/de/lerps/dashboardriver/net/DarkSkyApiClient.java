package de.lerps.dashboardriver.net;

import java.util.Map;

import com.google.gson.*;

import de.lerps.dashboardriver.GlobalConfig;
import de.lerps.dashboardriver.model.weather.WeatherForecast;
import de.lerps.dashboardriver.net.ElasticsearchHttpClient;
import de.lerps.dashboardriver.net.HttpConnection;
import de.lerps.dashboardriver.net.interfaces.IWeatherClient;

import com.github.dvdme.ForecastIOLib.*;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class DarkSkyApiClient implements IWeatherClient
{
    private String _apiKey;
    private String _apiBaseUrl;

    public DarkSkyApiClient()
    {
        _apiKey = GlobalConfig.darkSkyApiKey;
        _apiBaseUrl = "https://api.darksky.net/forecast/";
    }

    @Override
    public WeatherForecast getWeatherForecast(double latitude, double longitude)
    {
        ForecastIO fio = new ForecastIO(_apiKey);
        fio.setUnits(ForecastIO.UNITS_SI);
        fio.setExcludeURL("hourly,minutely");

        String responseBody = null;
        String url = fio.getUrl(Double.toString(latitude), Double.toString(longitude));
        url = url.replaceAll("forecast.io", "darksky.net");

        System.out.println("Requesting Weather Data from " + url);

        try 
        {
            ApiResponse response = HttpConnection.getRequest(url);

            System.out.println(response.statusCode);

            responseBody = response.body;

            ElasticsearchHttpClient.logSuccessfulApiAccess("DarkSky", url);
        } 
        catch (Exception e) 
        {
            ElasticsearchHttpClient.logFailedApiAccess("DarkSky", url, e.getMessage());
            e.printStackTrace();
        }

        if(responseBody != null)
        {
            Gson gs = new GsonBuilder().create();

            try
            {
                Map<String, Object> mapped = gs.fromJson(responseBody, Map.class);
                return WeatherForecast.fromApiResponse(mapped);
            } 
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public WeatherForecast getWeatherForecast()
    {
        return getWeatherForecast(GlobalConfig.weatherLatitude, GlobalConfig.weatherLongitude);
    }

    private String getUrl(double latitude, double longitude)
    {
        StringBuilder url = new StringBuilder(100)
            .append(_apiBaseUrl)
            .append(_apiKey)
            .append("/")
            .append(latitude)
            .append(",")
            .append(longitude);

        return url.toString();
    }
}