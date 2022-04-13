package by.rom.stockapi.exception;

public class CryptoAccountNotFound extends RuntimeException{
    public CryptoAccountNotFound(String message) {
        super(message);
    }
}