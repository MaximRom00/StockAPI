package by.rom.stockapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "crypto")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Crypto {

    @Id
    private String id;

    @Column(name = "symbol")
    private String symbol;

    @Column(name = "name")
    private String name;

    @Column(name = "image")
    private String image;

    @Column(name = "currentPrice")
    private Double currentPrice;

    @Column(name = "marketCapRank")
    private Integer marketCapRank;

    @Column(name = "high24")
    private Double high24;

    @Column(name = "low24")
    private Double low24;
}
