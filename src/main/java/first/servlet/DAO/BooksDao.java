package first.servlet.DAO;

import first.servlet.beans.BookBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BooksDao
{
    private List<BookBean> books;

    public static BooksDao Empty()
    {
        return new BooksDao();
    }

    private BooksDao()
    {
        this.books = Arrays.asList(
                        BookBean.Create("Jaś i Małgosia", "Wilhelm Karl Grimm", 1_950L),
                        BookBean.Create("Grube wióry", "Pacześ Rafał", 2_018L),
                        BookBean.Create("Powrót", "Katarzyna Nosowska", 1_990L),
                        BookBean.Create("Twoja wewnętrzna moc", "Agnieszka Maciąg", 2_010L)
        );
    }

    public void add(BookBean book)
    {
        this.books.add(book);
    }

    public List<BookBean> getAll()
    {
        return new ArrayList<>(this.books);
    }
}
