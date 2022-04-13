package by.rom.stockapi.service;

import by.rom.stockapi.model.dto.CryptoAccountDto;
import by.rom.stockapi.model.dto.CryptoDto;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CsvWriter {
    private final CsvMapper csvMapper = new CsvMapper();

    private final CsvSchema csvSchemaCrypto = csvMapper
            .schemaFor(CryptoDto.class)
            .withHeader()
            .sortedBy("id", "symbol","market_cap_rank", "name",
                    "current_price", "high_24h", "low_24h", "image");

    private final CsvSchema csvSchemaCryptoAccount = csvMapper
            .schemaFor(CryptoAccountDto.class)
            .withHeader()
            .sortedBy("id", "crypto_name", "amount_of_crypto",
                    "value_price", "currency", "user");


    public void writerCrypto(@NotNull String fileName,
                       @NotNull List<CryptoDto> cryptoDto){

        try(Writer writer = new PrintWriter(new FileWriter(fileName))) {
            List<CryptoDto> sortedListByRank = cryptoDto.stream().sorted(Comparator.comparing(CryptoDto::getMarketCapRank)).collect(Collectors.toList());

            csvMapper.writer()
                    .with(csvSchemaCrypto)
                    .writeValues(writer)
                    .writeAll(sortedListByRank);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void writeCryptoAccount(@NotNull String fileName,
                                   @NotNull List<CryptoAccountDto> cryptoAccountDto){

        try(Writer writer = new PrintWriter(new FileWriter(fileName))) {
            List<CryptoAccountDto> sortedListByRank =
                    cryptoAccountDto.stream()
                            .sorted(Comparator.comparing(CryptoAccountDto::getId))
                            .collect(Collectors.toList());

            csvMapper.writer()
                    .with(csvSchemaCryptoAccount)
                    .writeValues(writer)
                    .writeAll(sortedListByRank);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
