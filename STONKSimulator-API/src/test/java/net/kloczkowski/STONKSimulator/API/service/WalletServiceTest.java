package net.kloczkowski.STONKSimulator.API.service;

import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.repository.OpenPositionRepository;
import net.kloczkowski.STONKSimulator.API.repository.WalletRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class WalletServiceTest {

    @Mock
    WalletRepository walletRepository;

    @Mock
    OpenPositionRepository openPositionRepository;

    @InjectMocks
    WalletService walletService;


    @Test
    public void getByUserShouldReturnUsersWallet() {
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(100_000);
        User user = new User();
        user.setUsername("user");
        wallet.setUser(user);

        when(walletRepository.findByUser_Username("user")).thenReturn(Optional.of(wallet));

        var result = walletService.getWalletByUser("user");

        verify(walletRepository).findByUser_Username("user");

        assertEquals(wallet.getId(), result.getId());
        assertEquals(wallet.getBalance(), result.getBalance());
    }

    @Test
    public void placeSellStockOrderShouldOpenNewPositionAndSubtractMoneyFromWallet() {
//        TODO
    }
}