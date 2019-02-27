package library;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class BookManager {

    private static AtomicLong next_id = new AtomicLong();
    private static HashMap<Long, Book> library = new HashMap<Long, Book>();
    private static BookManager instance = null;

    public static BookManager getInstance() {
        if (instance == null) {
            instance = new BookManager();
        }
        return instance;
    }

    public long getLibrarySize() {
        return library.size();
    }

    public void addBookToMap(Book book) {
        long id = next_id.incrementAndGet();
        book.setId(id);
        library.put(id, book);
    }

    public boolean addBook(String title, String author, Date releaseDate, double price) {
        Book newBook = new Book(title, author, releaseDate, price);
        addBookToMap(newBook);
        return true;
    }


    public Book getBookById(long id) {
        return library.get(id);
    }

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

    public boolean deleteBook(long id) {
        library.remove(id);
        return true;
    }

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