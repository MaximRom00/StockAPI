package by.rom.stockapi.controller;

import by.rom.stockapi.converter.CryptoConverter;
import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.model.dto.CryptoDto;
import by.rom.stockapi.service.CryptoService;
import by.rom.stockapi.service.CsvWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    private final CsvWriter csvWriter;

    @Value("${application.fileName}")
    private String fileName;

    private final CryptoConverter cryptoConverter;

    public CryptoController(CryptoService cryptoService, CsvWriter csvWriter, CryptoConverter cryptoConverter){
        this.cryptoService = cryptoService;
        this.csvWriter = csvWriter;
        this.cryptoConverter = cryptoConverter;
    }

    //Get crypto list by size(size)
    @GetMapping("/list")
    public List<Crypto> getCryptoList(@RequestParam String size){

        return cryptoService.getCryptoList(Integer.parseInt(size));
    }

    //Get crypto by symbol.
    @GetMapping("")
    public Crypto getCrypto(@RequestParam(required = false, name = "symbol") String symbol){
        return cryptoService.findByName(symbol);
    }

    //Get file report in csv file.
    @GetMapping("/report")
    public List<CryptoDto> getReport(@RequestParam String size){
        List<CryptoDto> reportList = cryptoService.getCryptoList(Integer.parseInt(size))
                .stream()
                .map(cryptoConverter::toDto)
                .collect(Collectors.toList());
        csvWriter.writer(fileName, reportList);
        return reportList;
    }
}
