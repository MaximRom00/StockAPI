package by.rom.stockapi.controller;

import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.model.dto.CryptoAccountDto;
import by.rom.stockapi.model.dto.CryptoDto;
import by.rom.stockapi.service.CryptoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final CryptoService cryptoService;

    @Value("${application.fileNameCrypto}")
    private String fileNameCrypto;

    @Value("${application.fileNameCryptoAccount}")
    private String fileNameCryptoAccount;


    public CryptoController(CryptoService cryptoService){
        this.cryptoService = cryptoService;
    }

    //Get crypto list by size
    @GetMapping("/list")
    public List<Crypto> getCryptoList(@RequestParam String size){
        return cryptoService.getCryptoList(Integer.parseInt(size));
    }

    //Get crypto by symbol.
    @GetMapping("")
    public Crypto getCrypto(@RequestParam(required = false, name = "symbol") String symbol){
        return cryptoService.findByName(symbol);
    }

    @GetMapping("/lowestPrice")
    public Crypto getCryptoLowestPrice(){
        return cryptoService.getCryptoLowestPrice();
    }

    @GetMapping("/highestPrice")
    public Crypto getCryptoHighestPrice(){
        return cryptoService.getCryptoHighestPrice();
    }

    @DeleteMapping("/deleteAccount")
    public ResponseEntity<String> deleteUser(@RequestParam String id){
        cryptoService.deleteAccount(id);
        return new ResponseEntity<>("Delete was successful", HttpStatus.OK);
    }

    //Get file report Crypto currency in csv file.
    @GetMapping("/report")
    public List<CryptoDto> getReport(@RequestParam String size){
        return cryptoService.getReportCrypto(size, fileNameCrypto);
    }

    //Get file Crypto Account in csv file and this file will sent to our email.
    @GetMapping("/mail/accountReport")
    public List<CryptoAccountDto> getMailReportAccount(){
        return cryptoService.cryptoAccountSendEmail(fileNameCryptoAccount);
    }
}
