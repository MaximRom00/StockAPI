package by.rom.stockapi.exception;

public class CryptoCurrencyNotFound  extends RuntimeException{
    public CryptoCurrencyNotFound(String message) {
        super(message);
    }
}
