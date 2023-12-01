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
        System.out.print("Enter the book number for more details");
        int selectedBookIndex = input.nextInt();

        if (selectedBookIndex > 0 && selectedBookIndex < bookList.size()) {
            library.displaySelectedBook(searchedBooks.get(selectedBookIndex-1));
        } else {
            System.out.println("Invalid selection. Please choose a number from the list.");
        }

    }
}
