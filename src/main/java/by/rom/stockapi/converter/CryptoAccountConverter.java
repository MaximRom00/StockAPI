package by.rom.stockapi.converter;


import by.rom.stockapi.model.CryptoAccount;
import by.rom.stockapi.model.dto.CryptoAccountDto;
import org.springframework.stereotype.Component;

@Component
public class CryptoAccountConverter {

    public CryptoAccountDto toDto(CryptoAccount cryptoAccount){
        return CryptoAccountDto.builder()
                .id(cryptoAccount.getId())
                .cryptoName(cryptoAccount.getCryptoName())
                .amountOfCrypto(cryptoAccount.getAmountOfCrypto())
                .currency(cryptoAccount.getCurrency())
                .userEmail(cryptoAccount.getUser().getEmail())
                .valuePrice(cryptoAccount.getValuePrice())
                .build();
    }
}
