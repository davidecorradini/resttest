package library;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BookManager {

    private static BookManager instance = null; // Instance to manage the singleton
    private static AtomicLong next_id = new AtomicLong(); // Atomic value for the next id when adding new book
    private static HashMap<Long, Book> library = new HashMap<Long, Book>(); // Book collection

    // Gets the instance if available, or creates a new one
    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    // Gets the library size
    public long getLibrarySize() {
        return library.size();
    }

    // Adds a book to the HashMap
    public void addBookToMap(Book book) {
        long id = next_id.incrementAndGet();
        book.setId(id);
        library.put(id, book);
    }

    // Creates the book instance and adds it to the HashMap
    public Book addBook(String title, String author, Date releaseDate, double price) {
        Book newBook = new Book(title, author, releaseDate, price);
        addBookToMap(newBook);
        return newBook;
    }

    // Gets the book by ID, returns null if book not available
    public Book getBookById(long id) {
        return library.get(id);
    }

    // Returns a list of books with title containing the query string
    public List<Book> searchBooksByTitle(String query) {
        List<Book> result = new ArrayList<Book>();
        Iterator it = library.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            Book book = (Book) pair.getValue();
            if (book.getTitle().toLowerCase().contains(query.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    // Deletes a book by ID, returns false if book is not in the HashMap
    public boolean deleteBook(long id) {
        if (library.remove(id) != null) {
            return true;
        }
        return false;
    }

    // Updates the book with given ID, with the new data passed (not all data is mandatory)
    public boolean updateBook(long id, String title, String author, Date releaseDate, double price) {
        Book book = getBookById(id);
        if (book == null) {
            return false;
        }
        if (title != "") {
            book.setTitle(title);
        }
        if (author != "") {
            book.setAuthor(author);
        }
        if (releaseDate != null) {
            book.setReleaseDate(releaseDate);
        }
        if (price != 0) {
            book.setPrice(price);
        }
        return true;
    }
}