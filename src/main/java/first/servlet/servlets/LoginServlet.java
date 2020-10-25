package first.servlet.servlets;

import com.google.gson.Gson;
import first.servlet.exceptions.ExceptionResponse;
import first.servlet.requests.LoginRequest;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
    private Gson gson;

    public void init()
    {
        this.gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
                    throws IOException, ServletException
    {
        resp.sendRedirect("/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws IOException, ServletException
    {
        // TODO: Delete
        // resp json
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");


//        resp.addCookie(new Cookie("userId", userIdBase64));

        // TODO: Sprawdzić czy dobre dane przychodzą, jeśli nie to exception
        // TODO: Wyciągnąć do zewnętrznej metody
        try
        {
            LoginRequest loginRequest = gson.fromJson(req.getReader().lines().collect(Collectors.joining()), LoginRequest.class);
            System.out.println("");
        }
        catch (Exception ex)
        {
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());
            exResponse.setStatus(500);
            resp.setStatus(500);
            gson.toJson(exResponse, resp.getWriter());
        }



        // TODO: Dopisać logikę logowania i podzielić na mniejsze mtody
        // TODO: Sparsować dane w formacie json otrzymane w requeście
        // TODO: Sprawdzić czy admin czy klient - inna metoda obslugi logowania dla obu - stworzyc obiekt uzytkownika - zapisać obiekt w HttpSession - zakodować login za pomocą Base64 - do odpoiwedzi dodać ciasteczko, gdzie kluczem jest userIda wartością base64 z loginu
        // TODO: Opowiedź sparsować na format json i wysłać do klienta
        // TODO: eśli poszło coś nie tak, to obiekt odpowiedz ma zawierać pola z wiadomością informującą o błędnym haśle wraz ze statusem 400 (bad request) - moze enum - sparsować i zwrócić do klienta
//        resp.setContentType("text/html");
//
//        String userEmail = req.getParameter("email");
//        String userPassword = req.getParameter("password");
//
//        UserLoginBean userLoginBean = userLoginDao.getUserDetails(userEmail, userPassword);
//        if (userLoginBean != null)
//        {
//            if (UserState.ADMIN.equals(userLoginBean.getUserState()))
//            {
//                req.getRequestDispatcher("/adminlogin").forward(req, resp);
//            }
//            else
//            {
//                req.getRequestDispatcher("/user").forward(req, resp);
//            }
//        }
//        else
//        {
//            resp.sendRedirect("/loginFailed.html");
//        }
    }
}
