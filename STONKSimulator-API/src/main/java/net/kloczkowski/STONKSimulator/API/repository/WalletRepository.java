package net.kloczkowski.STONKSimulator.API.repository;

import net.kloczkowski.STONKSimulator.API.model.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser_Username(String username);
}
