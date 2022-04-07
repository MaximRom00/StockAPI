package by.rom.stockapi.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CryptoDto {
    private String id;

    private String symbol;

    private String name;

    @JsonProperty("current_price")
    private Double currentPrice;

    @JsonProperty("market_cap_rank")
    private Integer marketCapRank;

    @JsonProperty("high_24h")
    private Double high24;

    @JsonProperty("low_24h")
    private Double low24;
}
