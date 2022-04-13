package by.rom.stockapi.service;

import by.rom.stockapi.converter.CryptoConverter;
import by.rom.stockapi.exception.CryptoCurrencyNotFound;
import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.model.dto.CryptoDto;
import by.rom.stockapi.repository.CryptoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@EnableScheduling
public class CryptoApiService {

    private final RestTemplate restTemplate;

    private final CryptoRepository cryptoRepository;

    private final CryptoConverter cryptoConverter;

    private static final String CURRENCY_CODE = "usd";

    private static final String AMOUNT_OF_CURRENCY = "50";

    @Value("${application.cryptoCurrencyUrlTemplate}")
    private String URL;

    public CryptoApiService(CryptoRepository cryptoRepository, RestTemplate restTemplate, CryptoConverter cryptoConverter) {
        this.cryptoRepository = cryptoRepository;
        this.restTemplate = restTemplate;
        this.cryptoConverter = cryptoConverter;
    }

//    @Scheduled(cron = "*/30 * * * * *")
    @Scheduled(fixedRate = 60000)
    public void saveCryptoCurrency(){
        List<CryptoDto> cryptosDto = getList();

        List<Crypto> cryptos = cryptosDto
                .stream()
                .map(cryptoConverter::fromDto)
                .collect(Collectors.toList());

        cryptoRepository.saveAll(cryptos);
    }


    public List<CryptoDto> getList(){
        ResponseEntity<List<CryptoDto>> response =
                restTemplate.exchange(URL + "vs_currency=" + CURRENCY_CODE + "&per_page=" + AMOUNT_OF_CURRENCY
                        , HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        return response.getBody();
    }

public Crypto getCryptoByCurrency(String currency, String cryptoName) {

        ResponseEntity<List<CryptoDto>> response =
                restTemplate.exchange(URL + "vs_currency=" + currency + "&ids=" + cryptoName
                        , HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        Optional<Crypto> cryptos = Objects.requireNonNull(response.getBody())
                .stream()
                .map(cryptoConverter::fromDto)
                .findFirst();

        if (cryptos.isPresent()){
            return cryptos.get();
        }
        else throw new CryptoCurrencyNotFound("Cryptocurrency not found: " + cryptoName);
    }
}
