package by.rom.stockapi.exception;

public class EmailIsPresentException extends RuntimeException{
    public EmailIsPresentException(String message) {
        super(message);
    }
}