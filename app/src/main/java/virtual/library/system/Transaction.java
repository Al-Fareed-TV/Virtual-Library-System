package virtual.library.system;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import com.opencsv.exceptions.CsvValidationException;

public class Transaction {
    public static void main(String[] args) throws CsvValidationException {
        Scanner input = new Scanner(System.in);
        Library library = new Library(); 
        List<Book> bookList = library.getListOfBooks();

        library.displayAllBooks();

       System.out.print("Seelect a book from list : ");
       int index = input.nextInt();
       
       library.displaySelectedBook(bookList.get(index-1));
       
    }
}




