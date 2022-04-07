package by.rom.stockapi.repository;

import by.rom.stockapi.model.Crypto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CryptoRepository extends JpaRepository<Crypto, Long>{

    Crypto findBySymbol(String symbol);
}
