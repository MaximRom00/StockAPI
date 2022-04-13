package by.rom.stockapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Currency {
    USD("USD"),
    EUR("EUR"),
    GBP("GBP"),
    CNY("CNY"),
    PLN("PLN"),
    RUB("RUB");

    private final String name;
}
