package lt.codeacademy.blogproject.exceptions;

public class UserNotFoundException extends RuntimeException{

    private final String username;

    public UserNotFoundException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
