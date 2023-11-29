package virtual.library.system;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String email;
}
