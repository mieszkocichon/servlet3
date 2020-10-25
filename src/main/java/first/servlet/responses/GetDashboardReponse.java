package first.servlet.responses;

import first.servlet.beans.BookBean;

import java.util.List;

public class GetDashboardReponse
{
    private List<BookBean> books;
    private int status;

    private GetDashboardReponse()
    {
    }

    public GetDashboardReponse(List<BookBean> books, int status)
    {
        this.books = books;
        this.status = status;
    }
}
