package by.rom.stockapi.service;

import by.rom.stockapi.exception.CryptoCurrencyNotFound;
import by.rom.stockapi.exception.GetCryptoListException;
import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.repository.CryptoRepository;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CryptoService {

    private final CryptoRepository cryptoRepository;

    private final MailSender mailSender;

    public CryptoService(CryptoRepository cryptoRepository, MailSender mailSender) {
        this.cryptoRepository = cryptoRepository;
        this.mailSender = mailSender;
    }

    public List<Crypto> getCryptoList(int size){
        if (size <= 30){
            return cryptoRepository.findAll()
                    .stream()
                    .filter(crypto -> crypto.getMarketCapRank() <= size).collect(Collectors.toList());
        }
        throw new GetCryptoListException("Size must be less than 30! Try again.");
    }

    public Crypto findByName(String symbol){
        Crypto crypto = cryptoRepository.findBySymbol(symbol);
        if (crypto != null){
            return crypto;
        }
        else throw new CryptoCurrencyNotFound("Cryptocurrency not found: " + symbol);
    }

    //    @Scheduled
    public void cryptoInformation(){

    }
}
