package first.servlet.servlets;

import first.servlet.beans.BookBean;
import first.servlet.utils.HtmlWrapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException
    {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        List<BookBean> books = (List<BookBean>) getServletContext().getAttribute("all_books");

        String queryString = req.getQueryString();
        if (Objects.nonNull(queryString) && queryString.contains("usun"))
        {
            String id = queryString.substring(5);
            books = books.stream().filter(book -> !id.equals(book.getId())).collect(Collectors.toList());
            getServletContext().setAttribute("all_books", books);
        }

        PrintWriter printWriter = resp.getWriter();
        printWriter.print(HtmlWrapper.getHtmlNavbar());
        printWriter.print(HtmlWrapper.getEmptyPlace());

        printWriter.print("Dodaj nową książkę: ");
        printWriter.print("<form action=\"admin?add\" method=\"post\">");
        printWriter.print("nazwa:<input type=\"text\" name=\"name\"/><br/>");
        printWriter.print("autor:<input type=\"text\" name=\"author\"/><br/>");
        printWriter.print("rok:<input type=\"text\" name=\"year\"/><br/>");
        printWriter.print("<input type=\"submit\" value=\"wyslij\"/>");
        printWriter.println("</form>");

        printWriter.print("Lista książek: </br>");

        books.stream().forEach(book -> {
            String button = "<a href='admin?usun=" + book.getId() + "'>usun</a>";
            printWriter.print(String.format("Nazwa: %s, autor: %s, rok: %s. %s", book.getName(), book.getAuthor(), book.getYear(), button));
            printWriter.print("</br>");
        });

        printWriter.print(HtmlWrapper.getEmptyPlace());
        printWriter.print(HtmlWrapper.getHtmlFooter());
        printWriter.flush();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException
    {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");

        if (req.getQueryString().equals("add")) {
            List<BookBean> books = (List<BookBean>) getServletContext().getAttribute("all_books");

            String name = req.getParameter("name");
            String author = req.getParameter("author");
            String year = req.getParameter("year");

            books.add(BookBean.Create(name, author, Long.parseLong(year)));
        }
        resp.sendRedirect("/admin");
    }
}
