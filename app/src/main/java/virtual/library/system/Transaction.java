package virtual.library.system;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    public static void main(String[] args) throws CsvValidationException {
        Library library = new Library(); 

        Scanner input = new Scanner(System.in);
        List<Book> bookList = library.getListOfBooks();

        System.out.println("Enter a Book's Title/Author/ISBN");
        String criteria = input.nextLine();
        List<Book> filteredBooks = library.searchBooks(criteria);

        System.out.println("Do you want to filter by Genre? (y/n)");
        if (input.next().equalsIgnoreCase("y")) {
            System.out.println("Enter the Genre : ");
            String genre = input.next();
            filteredBooks = library.filterBooksByGenre(genre);
        }

        System.out.println("Do you want to filter by Publication date? (y/n)");
        if (input.next().equalsIgnoreCase("y")) {
            System.out.println("Enter the Publication date (YYYY-MM-DD) : ");
            LocalDate publicationDate = LocalDate.parse(input.next());
            filteredBooks = library.filterBooksByPublicationDate(publicationDate);
        }

        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n", "Title", "Author", "ISBN", "Genre", "Published Date", "No of copies");
        for (Book book : filteredBooks) {
            System.out.printf("%-20s %-20s %-20s %-20s %s %d\n", book.getTitle(), book.getAuthor(), book.getIsbn(), book.getGenre(), book.getPublicationDate().toString(), book.getNumberOfCopies());
        }
    }
}
