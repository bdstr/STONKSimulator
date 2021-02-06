package net.kloczkowski.STONKSimulator.API.controller;

import net.kloczkowski.STONKSimulator.API.model.OpenPosition;
import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.service.WalletService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = WalletController.class)
@WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
class WalletControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    WalletService walletService;

    @Test
    public void getWalletShouldReturnWalletOfCurrentUser() throws Exception {
        String uri = "/wallet";

        User user = new User();
        user.setId(1L);
        user.setUsername("user");


        Wallet wallet = new Wallet();
        wallet.setId(1L);
        wallet.setBalance(100_000);
        wallet.setUser(user);

        OpenPosition openPosition = new OpenPosition();
        openPosition.setId(1L);
        openPosition.setWallet(wallet);


        when(walletService.getWalletByUser("user")).thenReturn(wallet);
        when(walletService.getOpenPositionsByUser("user")).thenReturn(new HashSet<>(Set.of(openPosition)));

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.wallet.id").value(wallet.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.wallet.balance").value(wallet.getBalance()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.wallet.userId").value(wallet.getUser().getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.openPositions[0].id").value(openPosition.getId()));

        verify(walletService).getWalletByUser("user");
    }
}