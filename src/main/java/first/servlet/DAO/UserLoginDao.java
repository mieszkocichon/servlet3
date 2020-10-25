package first.servlet.DAO;

import first.servlet.beans.UserBean;
import first.servlet.services.LoginValidationResult;

public class UserLoginDao
{
    public static UserLoginDao Empty()
    {
        return new UserLoginDao();
    }

    private UserLoginDao()
    {
    }

    public LoginValidationResult login(String userEmail, String userPassword)
    {
        if ("".equals(userEmail) || "".equals(userPassword))
        {
            return LoginValidationResult.Status(false);
        }

        if ("admin@admin.pl".equals(userEmail) || "admin".equals(userEmail) && "admin".equals(userPassword))
        {
            return LoginValidationResult.Create(true);
        }

        return LoginValidationResult.Status(false);
    }

    public UserBean getUserDetails(String userEmail, String userPassword)
    {
        // TODO:
        return null;
    }
}
