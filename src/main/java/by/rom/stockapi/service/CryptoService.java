package by.rom.stockapi.service;

import by.rom.stockapi.converter.CryptoAccountConverter;
import by.rom.stockapi.converter.CryptoConverter;
import by.rom.stockapi.exception.CryptoAccountNotFound;
import by.rom.stockapi.exception.CryptoCurrencyNotFound;
import by.rom.stockapi.exception.GetCryptoListException;
import by.rom.stockapi.exception.UserNotFound;
import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.model.CryptoAccount;
import by.rom.stockapi.model.User;
import by.rom.stockapi.model.dto.CryptoAccountDto;
import by.rom.stockapi.model.dto.CryptoDto;
import by.rom.stockapi.repository.CryptoAccountRepository;
import by.rom.stockapi.repository.CryptoRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final MailService mailService;

    private final CryptoRepository cryptoRepository;

    private final CryptoAccountRepository cryptoAccountRepository;

    private final CsvWriter csvWriter;

    private final CryptoAccountConverter cryptoAccountConverter;

    private final CryptoConverter cryptoConverter;

    public CryptoService(CryptoRepository cryptoRepository, MailService mailService, CryptoAccountConverter cryptoAccountConverter,
                         CryptoAccountRepository cryptoAccountRepository, CsvWriter csvWriter, CryptoConverter cryptoConverter) {
        this.cryptoRepository = cryptoRepository;
        this.mailService = mailService;
        this.cryptoAccountConverter = cryptoAccountConverter;
        this.cryptoAccountRepository = cryptoAccountRepository;
        this.csvWriter = csvWriter;
        this.cryptoConverter = cryptoConverter;
    }

    public List<Crypto> getCryptoList(int size){
        if (size <= 50){
            return cryptoRepository.findAll()
                    .stream()
                    .filter(crypto -> crypto.getMarketCapRank() <= size).collect(Collectors.toList());
        }
        throw new GetCryptoListException("Size must be less than 50! Try again.");
    }

    public Crypto findByName(String symbol){
        Crypto crypto = cryptoRepository.findBySymbol(symbol);
        if (crypto != null){
            return crypto;
        }
        else throw new CryptoCurrencyNotFound("Cryptocurrency not found: " + symbol);
    }

    public List<CryptoDto> getReportCrypto(int size, String fileNameCrypto){

        List<CryptoDto> reportList = getCryptoList(size)
                .stream()
                .map(cryptoConverter::toDto)
                .collect(Collectors.toList());

        System.out.println(reportList);
        csvWriter.writerCrypto(fileNameCrypto, reportList);

        return reportList;
    }

    public List<CryptoAccountDto> cryptoAccountSendEmail(String fileName){
        List<CryptoAccountDto> reportList = cryptoAccountRepository.findAll()
                .stream()
                .map(cryptoAccountConverter::toDto)
                .collect(Collectors.toList());

        csvWriter.writeCryptoAccount(fileName, reportList);

        mailService.sendMessage("csvfile@gmail.com",
                "Csv file with all accounts.",
                "This email subject", "" +
                        "cryptoAccount.csv");
        return reportList;
    }

    public void deleteAccount(long id) {
        Optional<CryptoAccount> cryptoAccount = cryptoAccountRepository.findById(id);

        if (cryptoAccount.isPresent()){
            cryptoAccountRepository.deleteById(id);
        }
        else throw new CryptoAccountNotFound("Account didn't find with id: " + id);
    }

    public Crypto getCryptoLowestPrice() {
        return cryptoRepository.findAll()
                .stream().min(Comparator.comparing(Crypto::getCurrentPrice))
                .get();
    }

    public Crypto getCryptoHighestPrice() {
        return cryptoRepository.findAll()
                .stream().max(Comparator.comparing(Crypto::getCurrentPrice))
                .get();
    }
}
