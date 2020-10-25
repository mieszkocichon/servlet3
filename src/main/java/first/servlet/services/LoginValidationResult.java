package first.servlet.services;

public class LoginValidationResult
{
    private boolean status;

    public static LoginValidationResult Status(boolean status)
    {
        return new LoginValidationResult(status);
    }

    public static LoginValidationResult Create(boolean status)
    {
        return new LoginValidationResult(status);
    }

    private  LoginValidationResult()
    {
    }

    public LoginValidationResult(boolean status)
    {
        this.status = status;
    }

    public boolean getStatus()
    {
        return status;
    }
}
