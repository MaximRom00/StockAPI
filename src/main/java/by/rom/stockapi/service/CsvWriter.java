package by.rom.stockapi.service;

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
    private final CsvSchema csvSchema = csvMapper
            .schemaFor(CryptoDto.class)
            .withHeader()
            .sortedBy("id", "symbol", "name", "current_price", "currentPrice",
                    "marketCapRank", "high24", "low24");

    public void writer(@NotNull String fileName,
                       @NotNull List<CryptoDto> cryptoDto){

        try(Writer writer = new PrintWriter(new FileWriter(fileName))) {
            List<CryptoDto> sortedListByRank = cryptoDto.stream().sorted(Comparator.comparing(CryptoDto::getMarketCapRank)).collect(Collectors.toList());

            csvMapper.writer()
                    .with(csvSchema)
                    .writeValues(writer)
                    .writeAll(sortedListByRank);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
