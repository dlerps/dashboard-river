package de.lerps.dashboardriver.model.habitica;

import java.util.Map;

import de.lerps.dashboardriver.model.BaseObject;

public class HabiticaUserStatistics extends BaseObject
{
    public String userName;
    public String userId;
    public String userClass;

    public int level;
    public int numberOfPets;
    public int numberOfMounts;

    public double exp;
    public double expNeeded;
    public double health;
    public double healthMax;
    public double mana;
    public double manaMax;

    @Override
    public String getTypeName()
    {
        return "HabiticaUserStatistics";
    }

    public HabiticaUserStatistics fromApiResponse(Map<String, Object> userData)
    {
        
    }
}