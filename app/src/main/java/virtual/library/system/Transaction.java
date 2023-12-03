package virtual.library.system;

import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    public static void main(String[] args){
        Scanner input = new Scanner(System.in);
        Library library = null;
        try {
            library = new Library();
        } catch (CsvValidationException e) {
            System.out.println("Failed to load library due to CSV formatting error.\nExiting..!");
            System.exit(0);
        }
        List<Book> bookList = library.getListOfBooks();

        library.displayAllBooks(bookList);
        System.out.println("Enter Book's Title/Author/ISBN :");
        String criteria = input.nextLine();
        List<Book> searchedBooks = library.searchBooks(criteria);
        library.displayAllBooks(searchedBooks);
        System.out.print("Enter the book number for more details: ");
        int bookIndex = input.nextInt() - 1; 
        if (bookIndex >= 0 && bookIndex < searchedBooks.size()) {
            Book selectedBook = searchedBooks.get(bookIndex);
            library.displaySelectedBook(selectedBook);
        } else {
            System.out.println("Invalid selection. Please enter a valid book number.");
        }

        System.out.println("Please enter the ISBN of the book : ");
        String isbn = input.nextLine();
        System.out.print("Title of the entered ISBN is : ");
        List<Book> bookByISBN = library.searchBooks(criteria);
        library.displayAllBooks(bookByISBN);
        System.out.println("Confirm the Title of the book (y/n): ");
        String confirmTitle = input.next();
        if(confirmTitle.equalsIgnoreCase("y")){
            System.out.println("Proceed(y)/Cancel(n)? : ");
            char proceeed = input.next().charAt(0);
            if(proceeed == 'y'){
                System.out.println("Book issued...");
            }else{
                System.out.println("Canceled transaction");
                input.close();
                return;
            }
        }
        else if(!confirmTitle.equalsIgnoreCase("n")){
            System.out.println("Invalid option..");
        }
        input.close();
    }
}
