package virtual.library.system;
import java.util.Date;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
@NonNull
public class Transaction {
    private int transactionId;
    private String isbn;
    private int userId;
    private Date dateBorrowed;
    private Date returnDate;
}
