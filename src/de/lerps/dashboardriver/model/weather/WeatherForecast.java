package de.lerps.dashboardriver.model.weather;

import de.lerps.dashboardriver.model.BaseObject;

public class WeatherForecast extends BaseObject
{
    public double locationLongitude;
    public double locationLatitude;
    public double precipIntensity;
    public double precipIntensityMax;
    public double precipProbability;
    public double temperatureMax;
    public double temperatureMin;
    public double temperatureFeltMax;
    public double temperatureFeltMin;
    public double humidity;
    public double windSpeed;
    public double visibility;
    public double airPressure;

    public String condition;
    public String icon;
    public String sunrise;
    public String sunset;
    public String temperatureMinTime;
    public String temperatureMaxTime;
    public String precipType;

    public WeatherCondition tomorrow;
    public WeatherCondition inTwoDays;
    public WeatherCondition inThreeDays;
    public WeatherCondition inFourDays;
    public WeatherCondition inFiveDays;

    @Override
    public String getTypeName()
    {
        return "WeatherForecast";
    }
}