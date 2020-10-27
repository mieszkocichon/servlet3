package first.servlet.filters;

import com.google.gson.Gson;
import first.servlet.beans.UserBean;
import first.servlet.enums.ResponseErrors;
import first.servlet.enums.UserState;
import first.servlet.exceptions.ExceptionResponse;
import first.servlet.utils.Cryptography;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

@WebFilter(filterName = "LoginFilter", urlPatterns = {"/dashboard", "/dashboard/*", "/dashboard*"})
public class CookieFilter implements Filter
{
    private final static Class CLASS = HttpServlet.class;
    private final static String CLASS_NAME = CLASS.getName();
    private final static Logger LOGGER = Logger.getLogger(CLASS_NAME);

    @Override
    public void init(FilterConfig filterConfig)
    {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
                    throws IOException
    {
        HttpServletRequest req = (HttpServletRequest) request;
        try
        {
            Object userObj = req.getSession().getAttribute("user_logged");
            if (userObj == null)
            {
                throw new Exception("Unauthorized user");
            }

            UserBean user = (UserBean) userObj;

            LOGGER.info(user.toString());

            if (!checkForUserIdCookie(req.getCookies(), user))
            {
                throw new Exception("No proper cookie");
            }
            if (((UserBean) userObj).getUserState().equals(UserState.USER))
            {
                if ("DELETE".equals(req.getMethod()) || "POST".equals(req.getMethod()))
                {
                    Gson gson = new Gson();
                    ExceptionResponse exResponse = new ExceptionResponse();
                    exResponse.setMessage("Unautorized");
                    exResponse.setStatus(500);

                    gson.toJson(exResponse, response.getWriter());

                    response.getWriter().write(exResponse.toString());

                    return;
                }
            }

            chain.doFilter(request, response);
        }
        catch (Exception ex)
        {
            Gson gson = new Gson();
            response.setContentType("application/json;charset=UTF-8");
            ExceptionResponse exResponse = new ExceptionResponse();
            exResponse.setMessage(ex.getLocalizedMessage());

            ResponseErrors responseError = ResponseErrors.UNAUTORIZED;
            int statusError = responseError.getStatus();

            exResponse.setStatus(statusError);
            ((HttpServletResponse) response).setStatus(statusError);
            gson.toJson(exResponse, response.getWriter());
        }
    }

    private boolean checkForUserIdCookie(Cookie[] cookies, UserBean user)
    {
        for (Cookie cookie : cookies)
        {
            if ("userId".equals(cookie.getName()))
            {
                return Cryptography.base64DecodeToBytes(cookie).equals(user.getName());
            }
        }
        return false;
    }

    @Override
    public void destroy()
    {
    }
}