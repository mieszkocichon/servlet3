package first.servlet.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Logger;

public class HelloServlet extends HttpServlet
{
    private final static Class CLASS = HttpServlet.class;
    private final static String CLASS_NAME = CLASS.getName();
    private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException
    {
        LOGGER.info(String.format("%s: %s", CLASS_NAME, "doGet"));

        String name = req.getParameter("name");
        PrintWriter printWriter = resp.getWriter();
        printWriter.write(String.format("Hello: %s", name));
    }
}
