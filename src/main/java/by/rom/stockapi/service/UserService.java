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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final CryptoRepository cryptoRepository;

    private final CryptoAccountRepository cryptoAccountRepository;

    public UserService(UserRepository userRepository, CryptoRepository cryptoRepository, CryptoAccountRepository cryptoAccountRepository){
        this.userRepository = userRepository;
        this.cryptoRepository = cryptoRepository;
        this.cryptoAccountRepository = cryptoAccountRepository;
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

    public String addCryptoAccount(String email, String cryptoShort, String amount){
        User user = userRepository.findUserByEmail(email);

        if (user == null){
            throw new UserNotFound("There isn't user with such email, you need to save user.");
        }

        Crypto crypto = cryptoRepository.findBySymbol(cryptoShort);

        if (crypto == null){
            throw new UserNotFound("Crypto: " + cryptoShort + " not found.");
        }

        double amountOfCrypto = Double.parseDouble(amount);

        CryptoAccount cryptoAccount = cryptoAccountRepository.findByUser(user);

        if (cryptoAccount == null){
            cryptoAccount = CryptoAccount
                .builder()
                .cryptoName(cryptoShort)
                .valuePrice(amountOfCrypto * crypto.getCurrentPrice())
                .amountOfCrypto(amountOfCrypto)
                .user(user)
                .build();
        }
        else {
            Optional<CryptoAccount> account = user.getCryptoAccount()
                    .stream()
                    .filter(us -> us.getCryptoName().equalsIgnoreCase(cryptoShort))
                    .findFirst();

            if (account.isEmpty()){
                cryptoAccount.setCryptoName(cryptoShort);
                cryptoAccount.setValuePrice(amountOfCrypto * crypto.getCurrentPrice());
                cryptoAccount.setAmountOfCrypto(amountOfCrypto);
            }
            else {
                cryptoAccount.setAmountOfCrypto(amountOfCrypto);
                cryptoAccount.setValuePrice(amountOfCrypto * crypto.getCurrentPrice());
            }
        }
        cryptoAccountRepository.save(cryptoAccount);
//        userRepository.save(user);
        return "Crypto: " + cryptoShort + ". Price: " + amountOfCrypto * crypto.getCurrentPrice();
    }

    public void deleteUser(String email) {
        User user = userRepository.findUserByEmail(email);

        if (user != null){
            userRepository.delete(user);
        }
        else throw new UserNotFound("User didn't find with email: " + email);

    }
}
