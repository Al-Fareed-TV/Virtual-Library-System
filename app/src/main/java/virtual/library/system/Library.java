package virtual.library.system;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    public Library() throws CsvValidationException {
        this.isbnSet = new HashSet<>();
        batchUploadFromCSV("/Users/testvagrant/Documents/Virtual-Library-System/app/src/main/resources/Books.csv");
    }

    private boolean isIsbnUnique(String isbn) {
        return isbnSet.add(isbn);
    }

    public List<Book> getListOfBooks() {
        return bookList;
    }

    public void displayAllBooks() {
        System.out.printf("%-5s %-20s %-20s %-20s %-20s %-20s %-20s\n", "Sl No.", "Title", "Author", "ISBN", "Genre",
                "Published Date", "No of copies");
        System.out.println(
                "---------------------------------------------------------------------------------------------------------------");
        int i = 0;
        for (Book book : bookList) {
            i++;
            System.out.printf("%d %-20s %-20s %-20s %-20s %s %d\n", i, book.getTitle(), book.getAuthor(),
                    book.getIsbn(), book.getGenre(), book.getPublicationDate().toString(), book.getNumberOfCopies());
        }
    }

    public void displaySelectedBook(Book selectedBook) {
        System.out.println("Title: " + selectedBook.getTitle());
        System.out.println("Author: " + selectedBook.getAuthor());
        System.out.println("ISBN: " + selectedBook.getIsbn());
        System.out.println("Genre: " + selectedBook.getGenre());
        System.out.println("Published Date: " + selectedBook.getPublicationDate());
        displayAvailabilityOfBookSelectedBook(selectedBook);
    }

    private void displayAvailabilityOfBookSelectedBook(Book selectedBook) {
        String availability = selectedBook.getNumberOfCopies() < 1 ? "Out Of Stock"
                : "Avilable copies : " + selectedBook.getNumberOfCopies();
        System.out.println(availability);

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

                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate publicationDate = LocalDate.parse(nextRecord[4], dateFormatter);

                    int numberOfCopies = Integer.parseInt(nextRecord[5]);
                    System.out.println("Content from csv file : " + author + " " + title + " " + isbn + " " + genre
                            + " " + publicationDate + " " + numberOfCopies);

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
