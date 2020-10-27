package first.servlet.utils;

import javax.servlet.http.HttpServletRequest;

public class UrlUtils
{
    private UrlUtils()
    {
    }

    public static String getLastUrlParameter(HttpServletRequest req)
    {
        String url = req.getRequestURL().toString();
        String[] urlSplits = url.split("/");

        return urlSplits[urlSplits.length - 1];
    }
}
