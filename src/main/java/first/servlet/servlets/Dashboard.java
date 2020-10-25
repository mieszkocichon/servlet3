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
import java.util.List;

/**
 * GET - pobranie wszystkich książek
 * POST - dodanie nowej książki
 * DELETE - (/dashboard/id) - usuniecie ksiazki o podanym id (wraz z zwróceniem elementu usunietego)
 */
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
        // TODO: remove sop
        System.out.println("In DashboardServlet GET");
        response.setContentType("application/json;charset=UTF-8");

        try
        {
            List<BookBean> books = getBooksFromContext(request.getServletContext());
            GetDashboardReponse res = new GetDashboardReponse(books, 200);
            gson.toJson(res, response.getWriter());
        }
        catch (Exception ex)
        {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            response.setStatus(500);

            gson.toJson(exResponse, response.getWriter());
        }

        // TODO:
        System.out.println("Out DashboardServlet GET");
    }

    private List<BookBean> getBooksFromContext(ServletContext servletContext)
    {
        // TODO:
        return null;
    }
}
