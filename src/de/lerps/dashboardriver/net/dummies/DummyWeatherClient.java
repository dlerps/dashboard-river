package de.lerps.dashboardriver.net.dummies;

import org.joda.time.*;

import de.lerps.dashboardriver.model.weather.WeatherCondition;
import de.lerps.dashboardriver.model.weather.WeatherForecast;
import de.lerps.dashboardriver.net.interfaces.IWeatherClient;
import de.lerps.dashboardriver.utils.Utilities;

public class DummyWeatherClient implements IWeatherClient
{
    @Override
    public WeatherForecast getWeatherForecast(double x, double y)
    {
        WeatherForecast forecast = new WeatherForecast();
        // forecast.locationLatitude = x;
        // forecast.locationLongitude = y;
        // forecast.airPressure = 1002;
        // forecast.condition = "Rain";
        // forecast.humidity = 78.1;
        // forecast.icon = "rain";
        // forecast.precipIntensity = 2.4;
        // forecast.precipIntensityMax = 3.1;
        // forecast.precipProbability = 52;
        // forecast.precipType = "Rain";
        // forecast.sunrise = Utilities.getIsoDate(DateTime.now().plusHours(-2));
        // forecast.sunset = Utilities.getIsoDate(DateTime.now().plusHours(2));
        // forecast.temperatureFeltMax = 4;
        // forecast.temperatureFeltMin = -2;
        // forecast.temperatureMax = 3.1;
        // forecast.temperatureMin = -2;
        // forecast.temperatureMaxTime = Utilities.getIsoDate(DateTime.now().plusHours(1));
        // forecast.temperatureMinTime = Utilities.getIsoDate(DateTime.now().plusHours(-3));
        // forecast.visibility = 5;
        // forecast.windSpeed = 4.3;
        
        // WeatherCondition cond = new WeatherCondition();
        // cond.airPressure = 1024;
        // cond.condition = "Cold and rainy";
        // cond.icon = "rain";
        // cond.humidity = 72.2;
        // cond.nearestStormDistance = 1.2;
        // cond.precipIntensity = 1.3;
        // cond.precipIntensityError = 0.2;
        // cond.precipProbability = 76.2;
        // cond.precipType = "Rain";
        // cond.temperature = 2.1;
        // cond.temperatureFelt = -1.9;
        // cond.time = Utilities.getIsoDate();
        // cond.visibility = 8;
        // cond.windSpeed = 9;

        // forecast.tomorrow = cond;
        // forecast.inTwoDays = cond;
        // forecast.inThreeDays = cond;
        // forecast.inFourDays = cond;
        // forecast.inFiveDays = cond;

        return forecast;
    }   

    @Override
    public WeatherForecast getWeatherForecast()
    {
        return getWeatherForecast(1, 2);
    }
}