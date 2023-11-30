package virtual.library.system;

import java.time.LocalDate;
import com.opencsv.CSVReader;
import java.io.FileReader;

public class Book {
    private String author;
    private String title;
    private String isbn;
    private String genre;
    private LocalDate publicationDate;
    private int numberOfCopies;

    public Book() {
    }

    public Book(String author, String title, String isbn, String genre, LocalDate publicationDate, int numberOfCopies) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.numberOfCopies = numberOfCopies;
    }

    
}