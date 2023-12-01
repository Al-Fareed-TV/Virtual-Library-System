package virtual.library.system;

import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    public static void main(String[] args) throws CsvValidationException {
        Scanner input = new Scanner(System.in);
        Library library = new Library();
        List<Book> bookList = library.getListOfBooks();

        library.displayAllBooks();

        System.out.print("Please enter the number next to a book to view its details: ");
        int selectedBookIndex = input.nextInt() - 1;

        if (selectedBookIndex >= 0 && selectedBookIndex < bookList.size()) {
            library.displaySelectedBook(bookList.get(selectedBookIndex));
        } else {
            System.out.println("Invalid selection. Please choose a number from the list.");
        }

    }
}
