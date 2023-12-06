package virtual.library.system;

import java.time.LocalDate;


public class Book {
    private String author;
    private String title;
    private String isbn;
    private String genre;
    private LocalDate publicationDate;
    private int numberOfCopies;
    private boolean inStock;

    public Book() {
    }

    public Book(String author, String title, String isbn, String genre, LocalDate publicationDate, int numberOfCopies) {
        this.author = author;
        this.title = title;
        this.isbn = isbn;
        this.genre = genre;
        this.publicationDate = publicationDate;
        this.numberOfCopies = numberOfCopies;
        this.inStock = true;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getGenre() {
        return genre;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public int getNumberOfCopies() {
        return numberOfCopies;
    }
    
    public void setInStock(boolean status){
        this.inStock = status;
    }
    public void decrementCountOfBooks() {
        if (this.numberOfCopies > 0) {
            this.numberOfCopies--;
        }
        updateStockStatus(); 
    }

    public boolean isInStock(){
        return numberOfCopies > 0;
    }
    private void updateStockStatus() {
        this.inStock = (this.numberOfCopies > 0);
        if (!this.inStock) {
            System.out.println("Out of stock"); 
        }
    }
    
    
}