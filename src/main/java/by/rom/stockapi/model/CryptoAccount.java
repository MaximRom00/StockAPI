package by.rom.stockapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Table(name = "crypto_account")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CryptoAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String cryptoName;

    @NotNull
    private Double valuePrice;

    @NotNull
    private Double amountOfCrypto;

    @NotNull
    private String currency = Currency.USD.getName();

    @ManyToOne
    private User user;

}
