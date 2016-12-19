package de.lerps.dashboardriver;

import java.util.List;

import de.lerps.dashboardriver.utils.HabiticaAccount;

public class GlobalConfig
{
    public static String elasticsearchUrl;
    public static String darkSkyApiKey;

    public static double weatherLongitude;
    public static double weatherLatitude;

    public static List<HabiticaAccount> habiticaAccounts;

    private GlobalConfig(){}
}