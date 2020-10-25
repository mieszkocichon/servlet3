package first.servlet.servlets;

import first.servlet.utils.HtmlWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/about")
public class AboutServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException
    {
        resp.setContentType("text/html");

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(HtmlWrapper.getHtmlNavbar());
        printWriter.print(HtmlWrapper.getEmptyPlace());
        printWriter.print("Liblioteka Trzy Korony. Zachęcamy wszystkich do czytania =)");
        printWriter.print(HtmlWrapper.getEmptyPlace());
        printWriter.print(HtmlWrapper.getHtmlFooter());
    }
}
