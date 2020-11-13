package first.servlet.servlets;

import com.google.gson.Gson;
import first.servlet.beans.BookBean;
import first.servlet.responses.GetDashboardReponse;
import first.servlet.utils.UrlUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/dashboard/*")
public class DashboardDetails extends HttpServlet
{
    private Gson gson;

    @Override
    public void init()
    {
        this.gson = new Gson();
    }

    // 10
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
                    throws IOException
    {
        resp.setContentType("application/json;charset=UTF-8");

        List<BookBean> books = (List<BookBean>) req.getServletContext().getAttribute("all_books");

        String idParameter = UrlUtils.getLastUrlParameter(req);
        List<BookBean> book = books.stream().filter(e -> e.getId().equals(idParameter))
                        .collect(Collectors.toList());
        books = books.stream().filter(e -> !e.getId().equals(idParameter)).collect(Collectors.toList());
        req.getServletContext().setAttribute("all_books", books);

        GetDashboardReponse res = new GetDashboardReponse(book, 200);
        gson.toJson(res, resp.getWriter());
        resp.getWriter().write(res.toString());
    }
}
