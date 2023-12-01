package virtual.library.system;

import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    public static void main(String[] args) throws CsvValidationException {
        Scanner input = new Scanner(System.in);
        Library library = new Library();
        List<Book> bookList = library.getListOfBooks();

        library.displayAllBooks(bookList);
        System.out.println("Enter Book's Title/Author/ISBN :");
        String criteria = input.nextLine();
        List<Book> searchedBooks = library.searchBooks(criteria);
        library.displayAllBooks(searchedBooks);
        System.out.print("Enter the book number for more details: ");
        int bookIndex = input.nextInt() - 1; // Adjusting index to match list indexing
        if (bookIndex >= 0 && bookIndex < searchedBooks.size()) {
            Book selectedBook = searchedBooks.get(bookIndex);
            library.displaySelectedBook(selectedBook);
        } else {
            System.out.println("Invalid selection. Please enter a valid book number.");
        }

    }
}
