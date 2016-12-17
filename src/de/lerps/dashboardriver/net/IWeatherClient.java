package de.lerps.dashboardriver.net;

import de.lerps.dashboardriver.model.weather.WeatherForecast;

public interface IWeatherClient
{
    WeatherForecast getWeatherForecast();
    WeatherForecast getWeatherForecast(double latitude, double longitude);
}