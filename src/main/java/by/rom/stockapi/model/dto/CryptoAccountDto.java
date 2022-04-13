package by.rom.stockapi.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoAccountDto {

    private Long id;

    private String cryptoName;

    private Double valuePrice;

    private Double amountOfCrypto;

    private String currency;

    private String userEmail;
}
