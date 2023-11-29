package virtual.library.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Book {
   private String author;
   private String title;
   private String isbn;
   private int availableBooks;
}
