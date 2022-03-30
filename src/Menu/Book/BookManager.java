package Menu.Book;

import Helper.Helper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BookManager {
    private static final BookManager bookManager = new BookManager();
    private final ArrayList<Book> books = new ArrayList<>();

    private BookManager(){
        if(Helper.isDummyEnabled()){
            books.add(new Book(
                  "dummy1",
                  "Book1",
                  "1234567891",
                  100,
                  new ArrayList<>(Collections.singletonList("author1")),
                  "publisher1",
                  100000
            ));
            books.add(new Book(
                  "dummy2",
                  "Book2",
                  "2234567892",
                  200,
                  new ArrayList<>(Arrays.asList("author2", "author4")),
                  "publisher2",
                  200000
            ));
            books.add(new Book(
                  "dummy3",
                  "Book3",
                  "3234567893",
                  300,
                  new ArrayList<>(Collections.singletonList("author3")),
                  "publisher3",
                  300000
            ));
        }
    }

    public static BookManager getInstance() {
        return bookManager;
    }

    private String generateId() {
        return Helper.generateId("book");
    }

    public int getPrice(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getPrice)
              .orElse(0);
    }

    public void listBook() {
        Helper.printTable(
              new String[]{"Title", "ISBN", "Page", "Authors", "Publisher", "Price"},
              "=",
              books
                    .stream()
                    .map((book) -> new String[]{
                          book.getTitle(),
                          book.getIsbn(),
                          Integer.toString(book.getPage()),
                          book.getAuthors().get(0),
                          book.getPublisher(),
                          Integer.toString(book.getPrice()),
                    }).toArray(String[][]::new),
              new int[]{10, 10, 6, 15, 15, 10},
              "| "
        );
    }

    public void addBook(
          String title,
          String isbn,
          int page,
          List<String> authors,
          String publisher,
          int price
    ) {
        books.add(new Book(
              generateId(),
              title,
              isbn,
              page,
              authors,
              publisher,
              price
        ));
    }
}
