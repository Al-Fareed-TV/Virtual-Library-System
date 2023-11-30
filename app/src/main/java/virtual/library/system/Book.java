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

    public void batchUploadFromCSV(String csvFilePath)throws Exception {
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                // Assuming the CSV format is: author, title, isbn, genre, publicationDate, numberOfCopies
                String author = nextRecord[0];
                String title = nextRecord[1];
                String isbn = nextRecord[2];
                String genre = nextRecord[3];
                LocalDate publicationDate = LocalDate.parse(nextRecord[4]);
                int numberOfCopies = Integer.parseInt(nextRecord[5]);

                // Create and save the book
                Book newBook = new Book(author, title, isbn, genre, publicationDate, numberOfCopies);
                // Save the newBook to your library
            }
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
}