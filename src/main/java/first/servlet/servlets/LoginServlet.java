package first.servlet.servlets;

import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Collectors;

import first.servlet.DAO.UserLoginDao;
import first.servlet.beans.UserBean;
import first.servlet.enums.ResponseErrors;
import first.servlet.exceptions.ExceptionResponse;
import first.servlet.requests.LoginRequest;
import first.servlet.responses.Response;
import first.servlet.utils.Cryptography;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
    private Gson gson;
    private UserLoginDao userLoginDao;
    private ExceptionResponse exResponse;
    private ResponseErrors responseStatus;

    public void init()
    {
        this.gson = new Gson();
        this.userLoginDao = UserLoginDao.Empty();
        this.exResponse = new ExceptionResponse();
        this.responseStatus = ResponseErrors.INTERNAL_SERVER_ERROR;
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
        resp.setContentType("application/json;charset=UTF-8");

        LoginRequest loginRequest;
        UserBean userBean;

        try
        {
            String parameters = req.getReader().lines().collect(Collectors.joining());
            loginRequest = gson.fromJson(parameters, LoginRequest.class);
            this.checkUserAndPassword(loginRequest);

            userBean = userLoginDao.login(loginRequest.getUsername(), loginRequest.getPassword());
            this.throwUserIsNotExists(userBean);

            this.storeUserInSession(req, userBean);
        }
        catch (Exception exception)
        {
            this.exResponse.setMessage(exception.getLocalizedMessage());
            this.exResponse.setStatus(this.responseStatus.getStatus());
            resp.setStatus(this.responseStatus.getStatus());
            gson.toJson(exResponse, resp.getWriter());

            resp.getWriter().write(exResponse.toString());

            return;
        }

        this.buildSuccessResponse(resp, userBean);


        // TODO: Dopisać logikę logowania
        // TODO: Sprawdzić czy admin czy klient - inna metoda obslugi logowania dla obu - do odpoiwedzi dodać ciasteczko, gdzie kluczem jest userIda wartością base64 z loginu
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
        //                req.getRequestDispather("/user").forward(req, resp);
        //            }
        //        }c
        //        else
        //        {
        //            resp.sendRedirect("/loginFailed.html");
        //        }
    }

    private void buildSuccessResponse(HttpServletResponse resp, UserBean userBean)
                    throws IOException
    {
        String userIdBase64 = Cryptography.getBase64FromString(userBean.getName());
        Response response = Response.Default("success", 200);

        gson.toJson(response, resp.getWriter());

        resp.addCookie(new Cookie("userId", userIdBase64));
        resp.getWriter().write(response.toString());
    }

    private void storeUserInSession(HttpServletRequest req, UserBean userBean)
    {
        req.getSession().setAttribute("user_logged", userBean);
        req.getSession().setAttribute("user_login", userBean.getName());
        req.getSession().setAttribute("user_password", userBean.getPassword());
    }

    private void throwUserIsNotExists(UserBean userBean)
    {
        if (userBean == null)
        {
            this.responseStatus = ResponseErrors.BAD_REQUEST;
            throw new IllegalArgumentException("User is not exists.");
        }
    }

    private void checkUserAndPassword(LoginRequest loginRequest)
    {
        if (Objects.isNull(loginRequest.getUsername()) &&
                        Objects.isNull(loginRequest.getPassword()))
        {
            this.responseStatus = ResponseErrors.BAD_REQUEST;
            throw new IllegalArgumentException("Login or password is empty.");
        }
    }
}
