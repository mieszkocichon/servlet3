package first.servlet.servlets;

import com.google.gson.Gson;
import first.servlet.beans.BookBean;
import first.servlet.enums.ResponseErrors;
import first.servlet.exceptions.ExceptionResponse;
import first.servlet.responses.GetDashboardReponse;

import javax.servlet.ServletContext;
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
    public void init()
    {
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
                    throws IOException
    {
        response.setContentType("application/json;charset=UTF-8");

        try
        {
            List<BookBean> books = getBooksFromContext(request.getServletContext());
            GetDashboardReponse res = new GetDashboardReponse(books, ResponseErrors.OK.getStatus());
            gson.toJson(res, response.getWriter());
            response.getWriter().write(response.toString());
        }
        catch (Exception ex)
        {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());

            int status = ResponseErrors.INTERNAL_SERVER_ERROR.getStatus();
            exResponse.setStatus(status);
            response.setStatus(status);

            gson.toJson(exResponse, response.getWriter());

            response.getWriter().write(exResponse.toString());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws IOException
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
