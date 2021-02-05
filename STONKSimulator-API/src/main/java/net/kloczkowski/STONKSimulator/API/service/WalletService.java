package net.kloczkowski.STONKSimulator.API.service;

import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.repository.WalletRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet getByUser(String username) {
        return walletRepository.findByUser_Username(username).orElseThrow(EntityNotFoundException::new);
    }
}
