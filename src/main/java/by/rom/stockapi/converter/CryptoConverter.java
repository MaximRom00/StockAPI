package by.rom.stockapi.converter;

import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.model.dto.CryptoDto;
import org.springframework.stereotype.Component;


@Component
public class CryptoConverter {

    public CryptoDto toDto(Crypto crypto){
        return CryptoDto.builder()
                .id(crypto.getId())
                .name(crypto.getName())
                .currentPrice(crypto.getCurrentPrice())
                .high24(crypto.getHigh24())
                .low24(crypto.getLow24())
                .marketCapRank(crypto.getMarketCapRank())
                .symbol(crypto.getSymbol())
                .image(crypto.getImage())
                .build();
    }

    public Crypto fromDto(CryptoDto cryptoDto){
        return Crypto.builder()
                .id(cryptoDto.getId())
                .name(cryptoDto.getName())
                .image(cryptoDto.getImage())
                .currentPrice(cryptoDto.getCurrentPrice())
                .high24(cryptoDto.getHigh24())
                .low24(cryptoDto.getLow24())
                .marketCapRank(cryptoDto.getMarketCapRank())
                .symbol(cryptoDto.getSymbol())
                .build();
    }


}
