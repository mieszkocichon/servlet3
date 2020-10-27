package first.servlet.enums;

public enum ResponseErrors
{
    BAD_REQUEST(400),
    UNAUTORIZED(401),
    INTERNAL_SERVER_ERROR(500);

    private int status;

    ResponseErrors(int status)
    {
        this.status = status;
    }

    public int getStatus()
    {
        return status;
    }
}
