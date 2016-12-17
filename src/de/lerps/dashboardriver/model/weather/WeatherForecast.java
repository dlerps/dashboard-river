package de.lerps.dashboardriver.model.weather;

import de.lerps.dashboardriver.model.BaseObject;

public class WeatherForecast extends BaseObject
{
    public String locationLongitude;
    public String locationLatitude;

    public WeatherCondition currently;
    public WeatherCondition tomorrow;
    public WeatherCondition inTwoDays;
    public WeatherCondition inThreeDays;
    public WeatherCondition inFourDays;
    public WeatherCondition inFiveDays;

    @Override
    public string getTypeName()
    {
        return "WeatherForecast";
    }
}