package first.servlet.servlets;

import com.google.gson.Gson;
import first.servlet.beans.BookBean;
import first.servlet.exceptions.ExceptionResponse;
import first.servlet.responses.GetDashboardReponse;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/dashboard")
public class Dashboard extends HttpServlet
{
    private Gson gson;

    @Override
    public void init() throws ServletException
    {
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws ServletException, IOException
    {
        response.setContentType("application/json;charset=UTF-8");

        try
        {
            List<BookBean> books = getBooksFromContext(request.getServletContext());
            GetDashboardReponse res = new GetDashboardReponse(books, 200);
            gson.toJson(res, response.getWriter());
            response.getWriter().write(response.toString());
        }
        catch (Exception ex)
        {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);

            gson.toJson(exResponse, response.getWriter());

            response.getWriter().write(exResponse.toString());

            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws ServletException, IOException
    {
        List<BookBean> books = (List<BookBean>) req.getServletContext().getAttribute("all_books");

        String parameters = req.getReader().lines().collect(Collectors.joining());
        BookBean bookBean = gson.fromJson(parameters, BookBean.class);

        books.add(bookBean);
        req.getServletContext().setAttribute("all_books", books);

        GetDashboardReponse res = new GetDashboardReponse(Collections.singletonList(bookBean), 200);
        gson.toJson(res, resp.getWriter());
        resp.getWriter().write(res.toString());
    }

    private List<BookBean> getBooksFromContext(ServletContext servletContext)
    {
        return (List<BookBean>) servletContext.getAttribute("all_books");
    }
}
