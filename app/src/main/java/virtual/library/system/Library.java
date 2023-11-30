package virtual.library.system;

import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;

import com.opencsv.CSVReader;

public class Library {
    private Set<String> isbnSet;

    public Library() {
        this.isbnSet = new HashSet<>();
    }

    private boolean isIsbnUnique(String isbn) {
        return isbnSet.add(isbn);
    }

    public void batchUploadFromCSV(String csvFilePath) throws Exception {
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

                    Book newBook = new Book(author, title, isbn, genre, publicationDate, numberOfCopies);
                    booksAdded++;
                }else{
                    booksSkipped++;
                }
            }
            booksUploadedAndSkipped(booksAdded, booksSkipped);
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }
    private void booksUploadedAndSkipped(int booksAdded, int booksSkipped){
        System.out.println("Number of Books Added : " + booksAdded+"\nNumber of books skipped : " + booksSkipped);

    }
}
