package first.servlet.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException
    {
        String userPassword =
                        String.valueOf(getServletContext().getAttribute("user_password_session"));

        if (userPassword == null || "null".equals(userPassword))
        {
            resp.sendRedirect("/login.html");
        }
        else
        {
            resp.sendRedirect("/dashboard");
        }
    }
}
