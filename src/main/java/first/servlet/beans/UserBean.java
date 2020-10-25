package first.servlet.beans;

import first.servlet.enums.UserState;

public class UserBean
{
    private String id;
    private String name;
    private String password;
    private UserState userState;

    public static UserBean USER(String id, String name, String password)
    {
        return new UserBean(id, name, password, UserState.USER);
    }

    public static UserBean CREATE(String id, String name, String password, UserState userState)
    {
        return new UserBean(id, name, password, userState);
    }

    private UserBean()
    {
    }

    public UserBean(String id, String name, String password, UserState userState)
    {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userState = userState;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getPassword()
    {
        return password;
    }

    public UserState getUserState()
    {
        return userState;
    }
}
