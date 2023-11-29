package virtual.library.system;

import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
@AllArgsConstructor
public class Book {
    private String author;
    private String title;
    private String isbn;
    private String genre;
    private Date pubDate;
    private int availableBooks;
    private List<Book> books;

    public void addBooks(Book book) {
        books.add(book);
    }

    public List<Book> getListOfBooks() {
        return books;
    }

    public boolean isBookAvailable(Book book) {
        return book.availableBooks > 0;
    }

    public void withdrawBook(Book book) {
        book.availableBooks--;
    }

    public void depositBook(Book book) {
        book.availableBooks++;
    }

    public void displayAllBooks() {
        System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %-20s", "Title", "Author", "ISBN", "Genre",
                "Published Date", "Availability"));
        for (Book book : books) {
            System.out.println(String.format("%-20s %-20s %-20s %-20s %-20s %-20d", book.title, book.author, book.isbn,
                    book.genre, book.pubDate, book.availableBooks));
        }
    }

    public void searchBook(String bookTitle) {
        for (Book book : books) {
            String avaialability = isBookAvailable(book) ? "is " : "is not" ;
            if (book.title == bookTitle) {
                System.out.println("The book " + avaialability +" available");
            }
        }
    }
}
