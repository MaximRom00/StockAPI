package by.rom.stockapi.exception;

public class UserNotFound  extends RuntimeException{
    public UserNotFound(String message) {
        super(message);
    }
}
