package library;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
public class LibraryController {

    @RequestMapping(value = "/books", method = RequestMethod.GET)
    public List<Book> searchBooksByTitle(@RequestParam(value="query", defaultValue = "") String query) {
        return BookManager.getInstance().searchBooksByTitle(query);
    }

    @RequestMapping(value = "/book", method = RequestMethod.GET)
    public Book getBook(@RequestParam(value = "id") long id) {
        if (id > 0 && id < Long.MAX_VALUE) {
            return BookManager.getInstance().getBookById(id);
        }
        return null;
    }

    @RequestMapping(value = "/book", method = RequestMethod.PUT)
    public long addBook(@RequestParam(value = "title") String title, @RequestParam(value = "author") String author,
                           @RequestParam(value = "releaseDate") String releaseDateString,
                           @RequestParam(value = "price") double price){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date releaseDate = sdf.parse(releaseDateString);
            Book newBook = BookManager.getInstance().addBook(title, author, releaseDate, price);
            return newBook.getId();
        } catch (ParseException e) {
            return 0;
        }

    }

    @RequestMapping(value = "/book", method = RequestMethod.DELETE)
    public boolean deleteBook(@RequestParam(value = "id") long id) {
        if (id > 0 && id < Long.MAX_VALUE) {
            return BookManager.getInstance().deleteBook(id);
        }
        return false;
    }

    @RequestMapping(value = "/book", method = RequestMethod.PATCH)
    public boolean updateBook(@RequestParam(value = "id") long id,
                              @RequestParam(value = "title", required = false, defaultValue = "") String title,
                              @RequestParam(value = "author", required = false, defaultValue = "") String author,
                              @RequestParam(value = "release_date", required = false, defaultValue = "") String releaseDateString,
                              @RequestParam(value = "price", required = false, defaultValue = "0") double price) {

        if (releaseDateString.equals("")) {
            return BookManager.getInstance().updateBook(id, title, author, null, price);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            try {
                Date releaseDate = sdf.parse(releaseDateString);
                BookManager.getInstance().updateBook(id, title, author, releaseDate, price);
                return true;
            } catch (ParseException e) {
                return false;
            }
        }
    }
}
