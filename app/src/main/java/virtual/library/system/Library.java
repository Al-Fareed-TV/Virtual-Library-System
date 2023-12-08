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
        batchUploadFromCSV("app/src/main/resources/Books.csv");
    }

    private boolean isIsbnUnique(String isbn) {
        return isbnSet.add(isbn);
    }

    public List<Book> getListOfBooks() {
        return bookList;
    }

    public void displayAllBooks(List<Book> books) {
        String border = "+------+----------------------+------------------------------+----------------------+----------------------+----------------------+----------------------+";
        String header = "| %-4s | %-20s | %-28s | %-20s | %-20s | %-20s | %-20s |\n";
        String rowFormat = "| %-4d | %-20s | %-28s | %-20s | %-20s | %-20s | %d                  |\n";

        System.out.println(border);
        System.out.printf(header, "Sl No.", "Author", "Title", "ISBN", "Genre", "Published Date", "No of copies");
        System.out.println(border);

        int i = 0;
        for (Book book : books) {
            i++;
            System.out.printf(rowFormat, i, book.getAuthor(), book.getTitle(), book.getIsbn(),
                    book.getGenre(), book.getPublicationDate().toString(), book.getNumberOfCopies());
        }

        System.out.println(border);
    }

    public void displaySelectedBook(Book selectedBook) {
        String border = String.join("", Collections.nCopies(72, "-"));
        System.out.println(border);
        System.out.printf("| %-10s | %-50s |\n", "Attribute", "Value");
        System.out.println(border);
        System.out.printf("| %-10s | %-50s |\n", "Title", selectedBook.getTitle());
        System.out.printf("| %-10s | %-50s |\n", "Author", selectedBook.getAuthor());
        System.out.printf("| %-10s | %-50s |\n", "ISBN", selectedBook.getIsbn());
        System.out.printf("| %-10s | %-50s |\n", "Genre", selectedBook.getGenre());
        System.out.printf("| %-10s | %-50s |\n", "Published Date", selectedBook.getPublicationDate());
        displayAvailability(selectedBook);
        System.out.println(border);
    }

    private void displayAvailability(Book selectedBook) {
        String availability = selectedBook.getNumberOfCopies() > 0 ? "Available Copies: "+selectedBook.getNumberOfCopies() : "Out of Stock";
        System.out.println(availability);
    }

    public void batchUploadFromCSV(String csvFilePath) throws CsvValidationException {
        int booksAdded = 0;
        int booksSkipped = 0;
        try (CSVReader reader = new CSVReader(new FileReader(csvFilePath))) {
            String[] nextRecord;
            while ((nextRecord = reader.readNext()) != null) {
                if (isIsbnUnique(nextRecord[2])) {
                    String title = nextRecord[0];
                    String author = nextRecord[1];
                    String isbn = nextRecord[2];
                    String genre = nextRecord[3];

                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    LocalDate publicationDate = LocalDate.parse(nextRecord[4], dateFormatter);

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
