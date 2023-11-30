package virtual.library.system;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.io.IOException;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

public class Library {
    Scanner input = new Scanner(System.in);
    private List<Book> bookList = new ArrayList<>();
    List<Book> searchedBooks = new ArrayList<>();
    private Set<String> isbnSet;

    public Library()throws CsvValidationException {
        this.isbnSet = new HashSet<>();
        batchUploadFromCSV("/Users/testvagrant/Documents/Virtual-Library-System/app/src/main/resources/Books.csv");
        // displayBooks();
    }

    private boolean isIsbnUnique(String isbn) {
        return isbnSet.add(isbn);
    }
    public List<Book> getListOfBooks(){
        return bookList;
    }
    public void displayBooks(){
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n", "Title", "Author", "ISBN", "Genre", "Published Date", "No of copies");
        for (Book book : bookList) {
            System.out.printf("%-20s %-20s %-20s %-20s %s %d\n", book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.getPublicationDate().toString(), book.getNumberOfCopies());
        }
    }

    public void batchUploadFromCSV(String csvFilePath) throws CsvValidationException {
        int booksAdded = 0;
        int booksSkipped = 0;
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                if (isIsbnUnique(nextRecord[2])) {
                    String author = nextRecord[0];
                    String title = nextRecord[1];
                    String isbn = nextRecord[2];
                    String genre = nextRecord[3];
                    LocalDate publicationDate = LocalDate.parse(nextRecord[4]);
                    int numberOfCopies = Integer.parseInt(nextRecord[5]);

                    bookList.add(new Book(author, title, isbn, genre, publicationDate, numberOfCopies));
                    booksAdded++;
                } else {
                    booksSkipped++;
                }
            }
            booksUploadedAndSkipped(booksAdded, booksSkipped);
        } catch (IOException e) {
            System.out.println("Error reading CSV file: " + e.getLocalizedMessage());
        }
    }

    private void booksUploadedAndSkipped(int booksAdded, int booksSkipped) {
        System.out.println("Number of Books Added : " + booksAdded + "\nNumber of books skipped : " + booksSkipped);
    }

    public List<Book> searchBooks(String criteria) {
        searchedBooks = bookList.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(criteria) ||
                        book.getAuthor().equalsIgnoreCase(criteria) ||
                        book.getIsbn().equalsIgnoreCase(criteria))
                .collect(Collectors.toList());
        return searchedBooks;
    }

    public List<Book> filterBooksByGenre(String genre) {
        return searchedBooks.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    public List<Book> filterBooksByPublicationDate(LocalDate date) {
        return searchedBooks.stream()
                .filter(book -> book.getPublicationDate().equals(date))
                .collect(Collectors.toList());
    }

}
