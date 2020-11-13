package first.servlet.beans;

import java.util.concurrent.ThreadLocalRandom;

public class BookBean
{
    private String id;
    private String name;
    private String author;
    private long year;

    public static BookBean Empty()
    {
        return new BookBean();
    }

    public static BookBean Create(String name, String author, long year)
    {
        // 12
        return new BookBean(ThreadLocalRandom.current().nextInt(20) + 1 + "", name, author, year);
    }

    public BookBean()
    {
    }

    private BookBean(String id, String name, String author, long year)
    {
        this.id = id;
        this.name = name;
        this.author = author;
        this.year = year;
    }

    public String getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public String getAuthor()
    {
        return author;
    }

    public long getYear()
    {
        return year;
    }
}
