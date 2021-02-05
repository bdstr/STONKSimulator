package net.kloczkowski.STONKSimulator.API.controller;

import net.kloczkowski.STONKSimulator.API.model.User;
import net.kloczkowski.STONKSimulator.API.model.Wallet;
import net.kloczkowski.STONKSimulator.API.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserController.class)
@WithMockUser(username = "user", authorities = {"ROLE_ADMIN"})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;

    @Captor
    ArgumentCaptor<String> argumentCaptor;

    @Test
    public void registerShouldCallServiceRegisterMethodWithParams() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("password");
        user.setAuthorities("ROLE_USER");
        Wallet wallet = new Wallet();
        wallet.setId(1L);
        user.setWallet(wallet);

        when(userService.register("user", "password")).thenReturn(user);

        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                {
                    "username":"user",
                    "password":"password"
                }""")
        ).andExpect(status().isCreated());

        verify(userService).register(argumentCaptor.capture(), argumentCaptor.capture());

        assertEquals("user", argumentCaptor.getAllValues().get(0));
        assertEquals("password", argumentCaptor.getAllValues().get(1));
    }

    @Test
    public void getUserProfileShouldReturnUserWithGivenUsername() throws Exception {
        String uri = "/profile";
        Wallet wallet = new Wallet();
        wallet.setId(1L);

        User user = new User();
        user.setId(1L);
        user.setUsername("user");
        user.setPassword("password");
        user.setAuthorities("ROLE_ADMIN");
        user.setWallet(wallet);

        when(userService.getByUsername("user")).thenReturn(user);

        mockMvc.perform(get(uri))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(user.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value(user.getUsername()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.authorities").value(user.getAuthorities()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.walletId").value(user.getWallet().getId()));

        verify(userService).getByUsername("user");
    }
}