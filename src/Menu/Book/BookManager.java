package Menu.Book;

import Helper.Helper;

import java.util.*;
import java.util.stream.Stream;

public class BookManager {
    private static final BookManager bookManager = new BookManager();
    private final ArrayList<Book> books = new ArrayList<>();

    private BookManager(){
        if(Helper.isDummyEnabled()){
            books.add(new Book(
                  "book-2a77cd44-4e85-4018-a21c-8c352f071d56",
                  "Book1",
                  "1234567891",
                  100,
                  new ArrayList<>(Collections.singletonList("author1")),
                  "publisher1",
                  100000
            ));
            books.add(new Book(
                  "book-9fe27cce-29f1-4778-98c7-d3bdcaf5ef1b",
                  "Book2",
                  "2234567892",
                  200,
                  new ArrayList<>(Arrays.asList("author2", "author4")),
                  "publisher2",
                  200000
            ));
            books.add(new Book(
                  "book-67e7279c-0a2c-4321-9cca-3eb339dffd08",
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

    public boolean isValidBookId(String id) {
        return books
              .stream()
              .anyMatch((book -> book.getId().equals(id)));
    }
    
    public String getTitle(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getTitle)
              .orElse("404 Not Found");
    }
    
     public String getIsbn(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getIsbn)
              .orElse("404 Not Found");
    }
     
    public int getPage(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getPrice)
              .orElse(0);
    }
    
    public List<String> getAuthors(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getAuthors)
              .orElse(null);
    }
    
    public String getPublisher(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getPublisher)
              .orElse("404 Not Found");
    }
    
    public int getPrice(String bookId) {
        return books
              .stream()
              .filter((book) -> book.getId().equals(bookId))
              .findFirst()
              .map(Book::getPrice)
              .orElse(0);
    }
    
    

    public Stream<Book> listBook() {
        return books.stream();
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

    public void deleteBook(String id) throws Exception {
        Optional<Book> book = books
              .stream()
              .filter((b) -> b.getId().equals(id))
              .findFirst();
        if(!book.isPresent()){
            throw new Exception("Invalid book id!");
        }
        books.remove(book.orElse(null));
    }

    public void editBook(String id, int price) throws Exception {
        Optional<Book> book = books
              .stream()
              .filter((b) -> b.getId().equals(id))
              .findFirst();
        if(!book.isPresent()){
            throw new Exception("Invalid book id!");
        }
        book.orElse(null).setPrice(price);
    }
}
