package virtual.library.system;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Transaction {
    private int transactionId;
    private int bookId;
    private int patronId;
    private Date dateBorrowed;
    private Date returnDate;
}
