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
                  "1984",
                  "9783843701419",
                  328,
                  new ArrayList<>(Collections.singletonList("George Orwell")),
                  "Secker & Warburg",
                  89000
            ));
            books.add(new Book(
                  "book-9fe27cce-29f1-4778-98c7-d3bdcaf5ef1b",
                  "To Kill a Mockingbird",
                  "9780099549482",
                  309,
                  new ArrayList<>(Collections.singletonList("Harper Lee")),
                  "J. B. Lippincott & Co.",
                  160000
            ));
            books.add(new Book(
                  "book-67e7279c-0a2c-4321-9cca-3eb339dffd08",
                  "Good Omens",
                  "9788448022440",
                  288,
                  new ArrayList<>(Arrays.asList("Terry Pratchett", "Neil Gaiman")),
                  "Gollancz",
                  165000
            ));
            books.add(new Book(
                  generateId(),
                  "The Explorers Guild: Volume One: A Passage to Shambhala",
                  "9781476727417",
                  784,
                  new ArrayList<>(Arrays.asList("Kevin Costner", "Jon Baird", "Rick Ross")),
                  "Simon and Schuster",
                  223000
            ));
        }
    }

    public static BookManager getInstance() {
        return bookManager;
    }

    private String generateId() {
        return Helper.generateId("book");
    }

    public boolean isValidId(String id) {
        return books
              .stream()
              .anyMatch((book -> book.getId().equals(id)));
    }

    public Book getBook(String id) {
        return books
              .stream()
              .filter(book -> book.getId().equals(id))
              .findFirst()
              .orElse(null);
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
