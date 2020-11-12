package first.servlet.DAO;

import first.servlet.beans.UserBean;
import first.servlet.enums.UserState;

import java.util.AbstractMap;
import java.util.Map;
import java.util.Objects;

public class UserLoginDao
{
    private final Map<String, UserBean> stringUserBeanHashMap;

    public static UserLoginDao Empty()
    {
        return new UserLoginDao();
    }

    private UserLoginDao()
    {
        this.stringUserBeanHashMap = Map.ofEntries(
                        new AbstractMap.SimpleImmutableEntry<>("user",
                                        UserBean.USER("0", "user", "user")),
                        new AbstractMap.SimpleImmutableEntry<>("admin",
                                        UserBean.CREATE("1", "admin", "admin", UserState.ADMIN))
        );
    }

    public UserState checkUserRole(String name)
    {
        UserBean user = getUserBean(name);
        if (Objects.nonNull(user))
        {
            return user.getUserState();
        }
        return null;
    }

    public UserBean login(String name, String userPassword)
    {
        UserBean user = getUserBean(name);
        if (Objects.nonNull(user))
        {
            return userPassword.equals(user.getPassword()) ? user : null;
        }
        return null;
    }

    private UserBean getUserBean(String name)
    {
        return this.stringUserBeanHashMap.getOrDefault(name, null);
    }
}
