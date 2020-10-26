package first.servlet.responses;

public class Response
{
    private String message;
    private int status;

    public static Response Default(String message, int status)
    {
        return new Response(message, status);
    }

    public Response()
    {
    }

    public Response(String message, int status)
    {
        this.message = message;
        this.status = status;
    }
}
