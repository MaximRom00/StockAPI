package by.rom.stockapi.service;

import by.rom.stockapi.exception.EmailIsPresentException;
import by.rom.stockapi.exception.UserNotFound;
import by.rom.stockapi.model.Crypto;
import by.rom.stockapi.model.CryptoAccount;
import by.rom.stockapi.model.Currency;
import by.rom.stockapi.model.User;
import by.rom.stockapi.repository.CryptoAccountRepository;
import by.rom.stockapi.repository.CryptoRepository;
import by.rom.stockapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CryptoRepository cryptoRepository;

    private final CryptoAccountRepository cryptoAccountRepository;

    private final CryptoApiService cryptoApiService;

    public UserService(UserRepository userRepository, CryptoRepository cryptoRepository, CryptoAccountRepository cryptoAccountRepository, CryptoApiService cryptoApiService){
        this.userRepository = userRepository;
        this.cryptoRepository = cryptoRepository;
        this.cryptoAccountRepository = cryptoAccountRepository;
        this.cryptoApiService = cryptoApiService;
    }

    public User saveUser(User user){
        boolean emailIsPresent = userRepository
                .findAll()
                .stream()
                .anyMatch(usrDB -> usrDB.getEmail().equalsIgnoreCase(user.getEmail()));

        if (emailIsPresent){
            throw new EmailIsPresentException("User with such email: " + user.getEmail() + " is present");
        }

        userRepository.save(user);
        return user;
    }

    public String addCryptoAccount(String email, String cryptoName, double amountOfCrypto, String...currency){
        User user = userRepository.findUserByEmail(email);

        if (user == null){
            throw new UserNotFound("There isn't user with such email, you need to save user.");
        }

        Crypto crypto = cryptoRepository.findByName(cryptoName);

        if (crypto == null){
            throw new UserNotFound("Crypto: " + cryptoName + " not found.");
        }

        if (currency[0] != null){
           crypto = cryptoApiService.getCryptoByCurrency(currency[0], cryptoName);
        }
        else currency[0] = Currency.USD.getName();

        saveAccount(user, crypto, amountOfCrypto, currency);

        return "Crypto: " + cryptoName + ". Price: " + amountOfCrypto * crypto.getCurrentPrice();
    }


    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email);

        if (user != null){
            userRepository.delete(user);
        }
        else throw new UserNotFound("User didn't find with email: " + email);
    }

    private void saveAccount( User user, Crypto crypto, double amountOfCrypto, String...currency) {

//        Check account by cryptoName.
            List<CryptoAccount> collect = user.getCryptoAccount()
                    .stream()
                    .filter(account -> account.getCryptoName().equalsIgnoreCase(crypto.getSymbol()))
                    .collect(Collectors.toList());

//        If user hasn't got account with such cryptoName we create it.
            if (collect.isEmpty()){
                cryptoAccountRepository.save(CryptoAccount.builder()
                        .cryptoName(crypto.getSymbol())
                        .valuePrice(amountOfCrypto * crypto.getCurrentPrice())
                        .amountOfCrypto(amountOfCrypto)
                        .user(user)
                        .currency(currency[0])
                        .build());
            }
            else {

//        Check account by currency.
                List<CryptoAccount> collectByCurrency = collect
                        .stream()
                        .filter(account -> account.getCurrency().equalsIgnoreCase(currency[0]))
                        .collect(Collectors.toList());


//        If user has got account with such currency we update it.
                if (!collectByCurrency.isEmpty()){
                    collectByCurrency.stream().peek(account -> {
                                account.setCryptoName(crypto.getSymbol());
                                account.setAmountOfCrypto(account.getAmountOfCrypto() + amountOfCrypto);
                                account.setValuePrice(account.getValuePrice() + (amountOfCrypto * crypto.getCurrentPrice()));
                        })
                        .findFirst()
                        .map(cryptoAccountRepository::save);
                }

//        User hasn't got account with such currency we create it.
                else {
                    cryptoAccountRepository.save(CryptoAccount.builder()
                                    .cryptoName(crypto.getSymbol())
                                    .valuePrice(amountOfCrypto * crypto.getCurrentPrice())
                                    .amountOfCrypto(amountOfCrypto)
                                    .user(user)
                                    .currency(currency[0])
                                    .build());
                }
            }
    }
}
