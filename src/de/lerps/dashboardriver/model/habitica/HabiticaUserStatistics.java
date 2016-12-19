package de.lerps.dashboardriver.model.habitica;

import java.util.Map;

import de.lerps.dashboardriver.model.BaseObject;
import de.lerps.dashboardriver.utils.Utilities;

public class HabiticaUserStatistics extends BaseObject
{
    public String userName;
    public String userId;
    public String userClass;

    public int level;

    public double exp;
    public double expNeeded;
    public double health;
    public double healthMax;
    public double mana;
    public double manaMax;
    public double gold;

    @Override
    public String getTypeName()
    {
        return "HabiticaUserStatistics";
    }

    public static HabiticaUserStatistics fromApiResponse(Map<String, Object> responseMap)
    {
        if(responseMap != null && responseMap.containsKey("data"))
        {
            Map<String, Object> userData = (Map<String, Object>) responseMap.get("data");
            HabiticaUserStatistics stats = new HabiticaUserStatistics();

            stats.userId = Utilities.parseStringFromMap(userData, "_id");

            if(userData != null)
            {
                
                if(userData.containsKey("profile"))
                {
                    Map<String, Object> userProfile = (Map<String, Object>) userData.get("profile");
                    stats.userName = Utilities.parseStringFromMap(userProfile, "name");
                }

                if(userData.containsKey("stats"))
                {
                    Map<String, Object> userStats = (Map<String, Object>) userData.get("stats");
                    stats.exp = Utilities.parseDoubleFromMap(userStats, "exp");
                    stats.expNeeded = Utilities.parseIntegerFromMap(userStats, "toNextLevel");
                    stats.health = Utilities.parseDoubleFromMap(userStats, "hp");
                    stats.mana = Utilities.parseDoubleFromMap(userStats, "mp");
                    stats.gold = Utilities.parseDoubleFromMap(userStats, "gp");
                    stats.healthMax = Utilities.parseIntegerFromMap(userStats, "maxHealth");
                    stats.manaMax = Utilities.parseIntegerFromMap(userStats, "maxMP");
                    stats.level = Utilities.parseIntegerFromMap(userStats, "lvl");
                    stats.userClass = Utilities.parseStringFromMap(userStats, "class");
                }
                else
                {
                    // return null if there arn't any stats found
                    return null;
                }
            }

            return stats;
        }

        return null;
    }
}