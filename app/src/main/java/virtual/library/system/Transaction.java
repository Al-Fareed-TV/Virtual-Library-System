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

        library.displayBooks();

       System.out.print("Enter the index of the book you want to select : ");
       int index = input.nextInt();
       
       Book selectedBook = bookList.get(index - 1);
       System.out.println("Title: " + selectedBook.getTitle());
       System.out.println("Author: " + selectedBook.getAuthor());
       System.out.println("ISBN: " + selectedBook.getIsbn());
       System.out.println("Genre: " + selectedBook.getGenre());
       System.out.println("Published Date: " + selectedBook.getPublicationDate());
       System.out.println("Number of Copies: " + selectedBook.getNumberOfCopies());
        String availability = selectedBook.getNumberOfCopies()<1?"Out Of Stock" : "Avilable copies : "+selectedBook.getNumberOfCopies();
       System.out.println(availability);
    }
}
