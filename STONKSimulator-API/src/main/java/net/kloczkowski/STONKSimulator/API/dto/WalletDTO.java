package net.kloczkowski.STONKSimulator.API.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import net.kloczkowski.STONKSimulator.API.model.Wallet;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public class WalletDTO {

    private final Long id;
    private final double balance;
    private final Long userId;

    @JsonIgnoreProperties("wallet")
    private final Set<OpenPosition> openPositions;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.balance = wallet.getBalance();
        this.userId = wallet.getUser().getId();
        this.openPositions = wallet.getOpenPositions();
    }
}
