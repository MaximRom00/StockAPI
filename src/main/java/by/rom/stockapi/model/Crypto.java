package by.rom.stockapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "crypto")
public class Crypto {

    @Id
    private String id;

    @Column(name = "symbol")
    @JsonProperty("symbol")
    private String symbol;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "image")
    @JsonProperty("image")
    private String image;

    @Column(name = "currentPrice")
    @JsonProperty("current_price")
    private Double currentPrice;

    @Column(name = "marketCapRank")
    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;

    @Column(name = "high24")
    @JsonProperty("high_24h")
    private Double high24;

    @Column(name = "low24")
    @JsonProperty("low_24h")
    private Double low24;
}
