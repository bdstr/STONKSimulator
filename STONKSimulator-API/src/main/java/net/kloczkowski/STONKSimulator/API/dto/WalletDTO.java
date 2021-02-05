package net.kloczkowski.STONKSimulator.API.dto;

import lombok.Getter;
import net.kloczkowski.STONKSimulator.API.model.Wallet;

@Getter
public class WalletDTO {

    private final Long id;
    private final double balance;
    private final Long userId;

    public WalletDTO(Long id, double balance, Long userId) {
        this.id = id;
        this.balance = balance;
        this.userId = userId;
    }

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.balance = wallet.getBalance();
        this.userId = wallet.getUser().getId();
    }
}
