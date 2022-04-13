package by.rom.stockapi.repository;

import by.rom.stockapi.model.CryptoAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CryptoAccountRepository extends JpaRepository<CryptoAccount, Long> {
}
