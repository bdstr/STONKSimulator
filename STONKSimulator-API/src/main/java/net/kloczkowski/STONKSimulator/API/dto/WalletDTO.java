package net.kloczkowski.STONKSimulator.API.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kloczkowski.STONKSimulator.API.model.Wallet;

@Getter
@RequiredArgsConstructor
public class WalletDTO {

    private final Long id;
    private final double balance;
    private final Long userId;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.balance = wallet.getBalance();
        this.userId = wallet.getUser().getId();
    }
}
