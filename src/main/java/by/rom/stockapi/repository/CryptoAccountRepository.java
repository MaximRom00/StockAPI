package by.rom.stockapi.repository;

import by.rom.stockapi.model.CryptoAccount;
import by.rom.stockapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoAccountRepository extends JpaRepository<CryptoAccount, Long> {

    CryptoAccount findByUser(User user);
}
