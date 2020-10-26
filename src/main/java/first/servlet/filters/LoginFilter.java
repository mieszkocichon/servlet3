//package first.servlet.filters;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//
//@WebFilter("/*")
//public class LoginFilter implements Filter
//{
//    private FilterConfig filterConfig;
//    private HttpServletRequest httpRequest;
//
//    private static final String[] loginRequiredURLs = {
//                    "/adminlogin", "/admin", "/dashboard", "/user"
//    };
//
//    @Override
//    public void init(FilterConfig filterConfig) {
//        this.filterConfig = filterConfig;
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//                    throws IOException, ServletException
//    {
//        httpRequest = (HttpServletRequest) request;
//
//        String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
//
//        if (path.startsWith("/admin"))
//        {
//            if (!isAdmin()) {
//                String loginPage = "/dashboard";
//                RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
//                dispatcher.forward(request, response);
//
//                return;
//            }
//        }
//
//        ServletContext servletContext = filterConfig.getServletContext();
//
//        boolean isLoggedIn = (servletContext != null &&
//                        servletContext.getAttribute("user_email_session") != null &&
//                        servletContext.getAttribute("user_email_session") != "null");
//
//        String loginURI = httpRequest.getContextPath() + "/login";
//        boolean isLoginRequest = httpRequest.getRequestURI().equals(loginURI);
//        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login");
//
//        if (isLoggedIn && (isLoginRequest || isLoginPage))
//        {
//            httpRequest.getRequestDispatcher("/").forward(request, response);
//        }
//        else if (!isLoggedIn && isLoginRequired())
//        {
//            String loginPage = "/login";
//            RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
//            dispatcher.forward(request, response);
//        }
//        else
//        {
//            chain.doFilter(request, response);
//        }
//    }
//
//    private boolean isLoginRequired()
//    {
//        String requestURL = httpRequest.getRequestURL().toString();
//
//        for (String loginRequiredURL : loginRequiredURLs)
//        {
//            if (requestURL.contains(loginRequiredURL))
//            {
//                return true;
//            }
//        }
//
//        return false;
//    }
//
//    public boolean isAdmin()
//    {
//        ServletContext servletContext = filterConfig.getServletContext();
//
//        return (servletContext != null
//                        && servletContext.getAttribute("user_email_session") != null
//        && "admin".equals(servletContext.getAttribute("user_email_session")));
//    }
//}
