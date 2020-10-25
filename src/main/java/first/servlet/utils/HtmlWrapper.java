package first.servlet.utils;

public class HtmlWrapper
{
    public static String getHtmlNavbar()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<head>");
        stringBuilder.append("<ul>");
        stringBuilder.append("<li><a href=\"login.html\">Login</a></li>");
        stringBuilder.append("<li><a href=\"welcome\">Welcome</a></li>");
        stringBuilder.append("<li><a href=\"about\">About</a></li>");
        stringBuilder.append("<li><a href=\"admin\">Admin</a></li>");
        stringBuilder.append("<li><a href=\"dashboard\">Dashboard</a></li>");
        stringBuilder.append("<li><a href=\"logout\">Logout</a></li>");
        stringBuilder.append("</ul>");

        return stringBuilder.toString();
    }

    public static String getEmptyPlace()
    {
        return "</br></br></br>";
    }

    public static String getHtmlFooter()
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<footer>");
        stringBuilder.append(" © 2007-2014 xYz.pl");
        stringBuilder.append("</footer>");
        stringBuilder.append("</head>");

        return stringBuilder.toString();
    }
}
