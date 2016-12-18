package de.lerps.dashboardriver.model.weather;

import de.lerps.dashboardriver.model.BaseObject;
import de.lerps.dashboardriver.utils.Utilities;

import java.util.LinkedList;


import java.util.List;
import java.util.Map;

public class WeatherForecast extends BaseObject
{
    public double locationLongitude;
    public double locationLatitude;
    public double airPressure;
    public double visibility;
    public double humidity;
    public double windSpeed;
    public double precipIntensity;
    public double precipProbability;
    public double temperature;
    public double temperatureFelt;

    public String condition;
    public String precipType;
    public String icon;

    public WeatherCondition today;
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

    public static WeatherForecast fromApiResponse(Map<String, Object> wr)
    {
        WeatherForecast fc = new WeatherForecast();

        if(wr != null)
        {
            fc.locationLongitude = Utilities.parseDoubleFromMap(wr, "longitude");
            fc.locationLatitude = Utilities.parseDoubleFromMap(wr, "latitude");

            // current condition
            if(wr.keySet().contains("currently"))
            {
                Map<String, Object> currently = (Map<String, Object>) wr.get("currently");
                //fc.currently = getConditionFromMap(currently);

                fc.airPressure = Utilities.parseDoubleFromMap(currently, "pressure");
                fc.humidity = Utilities.parseDoubleFromMap(currently, "humidity");
                fc.precipIntensity = Utilities.parseDoubleFromMap(currently, "precipIntensity");
                fc.precipProbability = Utilities.parseDoubleFromMap(currently, "precipProbability");
                fc.temperature = Utilities.parseDoubleFromMap(currently, "temperature");
                fc.temperatureFelt = Utilities.parseDoubleFromMap(currently, "apparentTemperature");
                fc.visibility = Utilities.parseDoubleFromMap(currently, "visibility");
                fc.windSpeed = Utilities.parseDoubleFromMap(currently, "windSpeed");

                fc.condition = Utilities.parseStringFromMap(currently, "summary");
                fc.precipType = Utilities.parseStringFromMap(currently, "precipType");
                fc.icon = Utilities.parseStringFromMap(currently, "icon");
            }
            
            // daily forcasts (today + 5 days)
            if(wr.keySet().contains("daily"))
            {
                Map<String, Object> d = (Map<String, Object>) wr.get("daily");

                if(d != null && d.keySet().contains("data"))
                {
                    List<Map<String, Object>> dailies = (List<Map<String, Object>>) d.get("data");
                    List<WeatherCondition> conditions = new LinkedList<WeatherCondition>();

                    for(int i = 0; i < 6; i++)
                    {
                        try
                        {
                            conditions.add(getConditionFromMap(dailies.get(i)));
                        }
                        catch(Exception e)
                        {
                            conditions.add(null);
                            System.out.println("No daily for day " + i);
                        }
                    }

                    fc.today = conditions.get(0);
                    fc.tomorrow = conditions.get(1);
                    fc.inTwoDays = conditions.get(2);
                    fc.inThreeDays = conditions.get(3);
                    fc.inFourDays = conditions.get(4);
                    fc.inFiveDays = conditions.get(5);
                }

                // forecast for today
            }
        }

        return fc;
    }

    private static WeatherCondition getConditionFromMap(Map<String, Object> conditionMap)
    {
        WeatherCondition c = new WeatherCondition();

        if(conditionMap != null)
        {
            c.condition = Utilities.parseStringFromMap(conditionMap, "summary");
            c.icon = Utilities.parseStringFromMap(conditionMap, "icon");
            c.precipType = Utilities.parseStringFromMap(conditionMap, "precipType");
            c.precipIntensity = Utilities.parseDoubleFromMap(conditionMap, "precipIntensity");
            c.precipIntensityMax = Utilities.parseDoubleFromMap(conditionMap, "precipIntensityMax");
            c.precipProbability = Utilities.parseDoubleFromMap(conditionMap, "precipProbability");
            c.airPressure = Utilities.parseDoubleFromMap(conditionMap, "pressure");
            c.temperatureMin = Utilities.parseDoubleFromMap(conditionMap, "temperatureMin");
            c.temperatureMax = Utilities.parseDoubleFromMap(conditionMap, "temperatureMax");
            c.temperatureMaxTime = Utilities.parseIsoTimeFromMap(conditionMap, "temperatureMaxTime");
            c.temperatureMinTime = Utilities.parseIsoTimeFromMap(conditionMap, "temperatureMinTime");
            c.temperatureFeltMax = Utilities.parseDoubleFromMap(conditionMap, "apparentTemperatureMax");
            c.temperatureFeltMin = Utilities.parseDoubleFromMap(conditionMap, "apparentTemperatureMin");
            c.humidity = Utilities.parseDoubleFromMap(conditionMap, "humidity");
            c.visibility = Utilities.parseDoubleFromMap(conditionMap, "visibility");
            c.windSpeed = Utilities.parseDoubleFromMap(conditionMap, "windSpeed");
            c.time = Utilities.parseIsoTimeFromMap(conditionMap, "time");
        }

        return c;
    }
}