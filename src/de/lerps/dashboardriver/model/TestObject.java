package de.lerps.dashboardriver.model;

//import de.lerps.dashboardriver.model.BaseObject;
import java.util.Random;
import org.joda.time.*;

public class TestObject extends BaseObject
{
    private String _name;
    private String _category;
    private String _date;
    private transient int _id;

    public TestObject(String name, String cat)
    {
        super();

        _id = new Random().nextInt();
        _date = DateTime.now().toString();

        _name = name;
        _category = cat;
    }

    @Override
    public String getTypeName()
    {
        return "TestObject";
    }
}