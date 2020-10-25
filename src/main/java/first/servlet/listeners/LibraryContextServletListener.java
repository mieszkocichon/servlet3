package first.servlet.listeners;

import first.servlet.DAO.BooksDao;
import first.servlet.beans.BookBean;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.List;

@WebListener
public class LibraryContextServletListener implements ServletContextListener
{
    @Override
    public void contextInitialized(ServletContextEvent sce)
    {
        ServletContext servletContext = sce.getServletContext();
        BooksDao booksDao = BooksDao.Empty();
        List<BookBean> booksList = booksDao.getAll();
        servletContext.setAttribute("all_books", booksList);
    }
}
