package de.lerps.dashboardriver.model.headline;

import java.util.Map;

import de.lerps.dashboardriver.model.headline.HeadlineImage;
import de.lerps.dashboardriver.utils.Utilities;


import java.util.List;
import java.util.ArrayList;

public class Headline
{
    public String title;
    public String link;
    public String category;
    public String text;
    public String source;

    public HeadlineImage image;

    public Headline(String newsCategory)
    {
        super();

        category = newsCategory;
    }

    public static List<Headline> fromApiResponse(Map<String, Object> mapped, int count, String category)
    {
        List<Headline> headlines = new ArrayList<Headline>(count);

        if(mapped != null && mapped.containsKey("value"))
        {
            List<Object> newsValues = (List<Object>) mapped.get("value");
            int i = 0;

            for(Object o : newsValues)
            {
                try
                {
                    headlines.add(extractNewsHeadline((Map<String, Object>) o, category));

                    if(++i >= count)
                    {
                        break;
                    }
                }
                catch(Exception ex0)
                {
                    ex0.printStackTrace();
                    System.out.println("Failed to extract news list");
                }
            }
        }

        return headlines;
    }

    private static Headline extractNewsHeadline(Map<String, Object> newsMap, String category)
    {
        Headline news = new Headline(category);
        news.title = Utilities.parseStringFromMap(newsMap, "name");
        news.text = Utilities.parseStringFromMap(newsMap, "description");
        news.link = Utilities.parseStringFromMap(newsMap, "url");

        if(newsMap.containsKey("image"))
        {
            Map<String, Object> imageMap = (Map<String, Object>) newsMap.get("image");

            if(imageMap.containsKey("thumbnail"))
            {
                Map<String, Object> thumbMap = (Map<String, Object>) imageMap.get("thumbnail");
                news.image = new HeadlineImage();

                news.image.address = Utilities.parseStringFromMap(thumbMap, "contentUrl");
                news.image.height = Utilities.parseIntegerFromMap(thumbMap, "height");
                news.image.width = Utilities.parseIntegerFromMap(thumbMap, "width");

                if(news.image != null)
                {
                    news.image.address = news.image.address.replaceAll("&pid=News", "");                    
                }
            }
        }

        if(newsMap.containsKey("provider"))
        {
            try
            {
                List<Object> providers = (List<Object>) newsMap.get("provider");

                if(providers != null && !providers.isEmpty())
                {
                    Map<String, Object> providerMap = (Map<String, Object>) providers.get(0);
                    news.source = Utilities.parseStringFromMap(providerMap, "name");
                }
            }
            catch(Exception ex2)
            {
                ex2.printStackTrace();
            }
        }

        news.source = news.source != null ? news.source : "n/a";

        return news;
    }
}