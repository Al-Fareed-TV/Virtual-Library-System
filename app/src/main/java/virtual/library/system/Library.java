package virtual.library.system;

import java.util.*;

public class Library {
    private Set<String> isbnSet;
    public Library() {
        this.isbnSet = new HashSet<>();
    }

    public boolean isIsbnUnique(String isbn) {
        return isbnSet.add(isbn);
    }
}
