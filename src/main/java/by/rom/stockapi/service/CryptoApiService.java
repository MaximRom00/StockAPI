package by.rom.stockapi.service;

import by.rom.stockapi.model.Crypto;
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

@Service
@EnableScheduling
public class CryptoApiService {

    private final RestTemplate restTemplate;

    private final CryptoRepository cryptoRepository;

    private static final String CURRENCY_CODE = "usd";

    private static final String AMOUNT_OF_CURRENCY = "30";

    @Value("${application.cryptoCurrencyUrlTemplate}")
    private String URL;

    public CryptoApiService(CryptoRepository cryptoRepository, RestTemplate restTemplate) {
        this.cryptoRepository = cryptoRepository;
        this.restTemplate = restTemplate;
    }

//    @Scheduled(cron = "*/30 * * * * *")
    @Scheduled(fixedRate = 60000)
    public void saveCurrency(){
        List<Crypto> cryptos = getList();
        cryptoRepository.saveAll(cryptos);
    }


    public List<Crypto> getList(){
        ResponseEntity<List<Crypto>> response =
                restTemplate.exchange(URL + "vs_currency=" + CURRENCY_CODE + "&per_page=" + AMOUNT_OF_CURRENCY
                        , HttpMethod.GET, null, new ParameterizedTypeReference<>() {
                        });

        return response.getBody();
    }
}
