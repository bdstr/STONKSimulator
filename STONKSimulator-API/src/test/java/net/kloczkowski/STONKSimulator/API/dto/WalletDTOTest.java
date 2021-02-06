package net.kloczkowski.STONKSimulator.API.dto;

import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WalletDTOTest {

    @Test
    void walletDTOShouldReturnRepresentationOfWallet() {
        User user = new User();
        user.setId(1L);

        OpenPosition openPosition = new OpenPosition();
        openPosition.setId(1L);

        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(100_000);
        wallet.setUser(user);
        wallet.setOpenPositions(new HashSet<>(Set.of(openPosition)));

        WalletDTO walletDTO = new WalletDTO(wallet);

        assertEquals(wallet.getId(), walletDTO.getId());
        assertEquals(wallet.getBalance(), walletDTO.getBalance());
        assertEquals(wallet.getUser().getId(), walletDTO.getUserId());
    }
}