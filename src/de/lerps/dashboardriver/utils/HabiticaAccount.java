package de.lerps.dashboardriver.utils;

import java.util.List;
import java.util.LinkedList;

public class HabiticaAccount
{
    public String displayName;
    public String userId;
    public String apiKey;

    public static List<HabiticaAccount> fromProperty(String prop)
    {
        List<HabiticaAccount> accounts = new LinkedList<HabiticaAccount>();

        if(prop != null)
        {
            // accounts separated by comma
            String[] accProp = prop.split(",");

            for(String acc : accProp)
            {
                // details of account separated by colon
                String[] accDetails = acc.split(":");

                if(accDetails.length == 3)
                {
                    HabiticaAccount habiticaAcc = new HabiticaAccount();
                    habiticaAcc.displayName = accDetails[0];
                    habiticaAcc.apiKey = accDetails[1];
                    habiticaAcc.userId = accDetails[2];

                    accounts.add(habiticaAcc);
                }
                else
                {
                    System.out.println("Wrong number of arguments for habitica account");
                }
            }
        }

        return accounts;
    }
}