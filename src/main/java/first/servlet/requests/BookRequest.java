package first.servlet.requests;

public class BookRequest
{
    private String name;
    private String author;
    private long year;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public long getYear()
    {
        return year;
    }

    public void setYear(long year)
    {
        this.year = year;
    }
}
