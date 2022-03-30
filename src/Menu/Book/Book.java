package Menu.Book;

import java.util.List;

public class Book {
    private final String id;
    private final String title;
    private final String isbn;
    private final int page;
    private final List<String> authors;
    private final String publisher;
    private int price;

    public Book(
          String id,
          String title,
          String isbn,
          int page,
          List<String> authors,
          String publisher,
          int price
    ) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.page = page;
        this.authors = authors;
        this.publisher = publisher;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPage() {
        return page;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getType() {
        return "Book";
    }
}
