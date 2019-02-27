package library;

import org.junit.*;

import java.util.Date;
import java.util.List;

public class BookManagerTest {

    @Before
    public void init() {
        Book book1 = new Book("The Perks of Being a Wallflower", "Stephen Chbosky", new Date(), 15.99);
        Book book2 = new Book("Me Talk Pretty One Day" , "David Sedaris", new Date(), 9.99);
        BookManager.getInstance().addBookToMap(book1);
        BookManager.getInstance().addBookToMap(book2);
    }

    @Test
    public void addBookToMapTest() {
        long sizeBefore = BookManager.getInstance().getLibrarySize();
        Book newBook = new Book("1984", "George Orwell", new Date(), 8.99);
        BookManager.getInstance().addBookToMap(newBook);
        assert(BookManager.getInstance().getLibrarySize() == (sizeBefore + 1));
}

    @Test
    public void addBookTest() {
        long sizeBefore = BookManager.getInstance().getLibrarySize();
        BookManager.getInstance().addBook("1984", "George Orwell", new Date(), 8.99);
        assert(BookManager.getInstance().getLibrarySize() == (sizeBefore + 1));
    }

    @Test
    public void getBookByIdTest() {
        Book b = BookManager.getInstance().getBookById(2);
        assert(b != null);
        assert(b.getId() == 2);
        Book b2 = BookManager.getInstance().getBookById(74638);
        assert(b2 == null);
        Book b3 = BookManager.getInstance().getBookById(-33);
        assert(b3 == null);
    }

    @Test
    public void searchBooksByTitleTest() {
        List<Book> result = BookManager.getInstance().searchBooksByTitle("wallflower");
        assert(result.size() == 1);
    }

    @Test
    public void deleteBookTest() {
        assert(BookManager.getInstance().getBookById(1) != null);
        BookManager.getInstance().deleteBook(1);
        assert(BookManager.getInstance().getBookById(1) == null);
    }

    @Test
    public void updateBookTest() {
        Date d = new Date();
        BookManager.getInstance().updateBook(1, "Nuovo titolo", "", null, 0);
        BookManager.getInstance().updateBook(1, "", "Nuovo autore", null, 0);
        BookManager.getInstance().updateBook(1, "", "", d, 0);
        BookManager.getInstance().updateBook(1, "", "", null, 99.99);
        assert(BookManager.getInstance().getBookById(1).getTitle().equals("Nuovo titolo"));
        assert(BookManager.getInstance().getBookById(1).getAuthor().equals("Nuovo autore"));
        assert(BookManager.getInstance().getBookById(1).getReleaseDate() == d);
        assert(BookManager.getInstance().getBookById(1).getPrice() == 99.99);

    }
}