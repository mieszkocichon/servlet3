package first.servlet.exceptions;

import first.servlet.responses.Response;

public class ExceptionResponse extends Response
{
    private String localizedMessage;
    private int statusCode;

    public void setMessage(String localizedMessage)
    {
        this.localizedMessage = localizedMessage;
    }

    public void setStatus(int statusCode)
    {
        this.statusCode = statusCode;
    }
}
