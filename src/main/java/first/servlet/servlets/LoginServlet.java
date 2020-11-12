package first.servlet.servlets;

import com.google.gson.Gson;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import first.servlet.DAO.UserLoginDao;
import first.servlet.beans.UserBean;
import first.servlet.enums.ResponseErrors;
import first.servlet.enums.UserState;
import first.servlet.exceptions.ExceptionResponse;
import first.servlet.requests.LoginRequest;
import first.servlet.responses.Response;
import first.servlet.utils.Cryptography;

@WebServlet("/login")
public class LoginServlet extends HttpServlet
{
    private final static Class CLASS = HttpServlet.class;
    private final static String CLASS_NAME = CLASS.getName();
    private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);

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
                    throws IOException
    {
        resp.sendRedirect("/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
                    throws IOException
    {
        resp.setContentType("application/json;charset=UTF-8");

        LoginRequest loginRequest;
        UserBean userBean;

        try
        {
            String parameters = req.getReader().lines().collect(Collectors.joining());
            // 1. Parse request to LoginRequest
            loginRequest = gson.fromJson(parameters, LoginRequest.class);

            UserState userRole = userLoginDao.checkUserRole(loginRequest.getUsername());
            if (Objects.nonNull(userRole))
            {
                throw new IllegalArgumentException("User doesn't exist.");
            }

            this.checkUserAndPassword(loginRequest);
            // 2. Login user
            userBean = userRole.equals(UserState.ADMIN) ?
                            this.loginAdmin(loginRequest) :
                            this.loginUser(loginRequest);

            this.throwUserIsNotExists(userBean);

            // 3. Store user in http session
            this.storeUserInSession(req, userBean);
        }
        catch (Exception exception)
        {
            // 7. Bad credentials
            this.exResponse.setMessage(exception.getLocalizedMessage());
            this.exResponse.setStatus(this.responseStatus.getStatus());
            resp.setStatus(this.responseStatus.getStatus());

            // 8. Parse response
            gson.toJson(exResponse, resp.getWriter());

            // 9. Send to client
            resp.getWriter().write(exResponse.toString());

            return;
        }

        this.buildSuccessResponse(resp, userBean);
    }

    private UserBean login(LoginRequest loginRequest)
    {
        return userLoginDao.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    private UserBean loginUser(LoginRequest loginRequest)
    {
        LOGGER.info(String.format("Login login: %s", loginRequest.getUsername()));
        return login(loginRequest);
    }

    private UserBean loginAdmin(LoginRequest loginRequest)
    {
        LOGGER.info(String.format("Admin login: %s", loginRequest.getUsername()));
        return login(loginRequest);
    }

    private void buildSuccessResponse(HttpServletResponse resp, UserBean userBean)
                    throws IOException
    {
        // 4. Code login to base64
        String userIdBase64 = Cryptography.base64EncodeToString(userBean.getName());
        Response response = Response.Default("success", 200);

        // 7. Parse response to JSON format
        gson.toJson(response, resp.getWriter());

        // 5. Store user login - cookie
        resp.addCookie(new Cookie("userId", userIdBase64));

        // 6. Send to client
        resp.getWriter().write(response.toString());
    }

    private void storeUserInSession(HttpServletRequest req, UserBean userBean)
    {
        HttpSession session = req.getSession();
        session.setAttribute("user_logged", userBean);
        session.setAttribute("user_name", userBean.getName());
        session.setAttribute("user_password", userBean.getPassword());
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
