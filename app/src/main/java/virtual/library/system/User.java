package virtual.library.system;
import java.util.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

@Getter
@NonNull
@AllArgsConstructor
public class User {
    private int userId;
    private String userName;
    private String email;
    List<User> userList;

    public void addUser(User user){
        userList.add(user);
    }
    public List<User> getUsersList(){
        return userList;
    }
}
