package net.kloczkowski.STONKSimulator.API.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@Transactional
abstract class GenericAuthorizationTest {

    static HashMap<String, Set<String>> accessibleURIs;

    @Autowired
    MockMvc mockMvc;

    @Test
    void roleHaveAccessToPostLogin() throws Exception {
        String uri = "/login";
        String verb = "POST";
        String content = """
                {
                    "username": "admin",
                    "password": "admin"
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void roleHaveAccessToPostRegister() throws Exception {
        String uri = "/register";
        String verb = "POST";
        String content = """
                {
                    "username": "user1",
                    "password": "password"
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isCreated());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void roleHaveAccessToGetProfile() throws Exception {
        String uri = "/profile";
        String verb = "GET";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void roleHaveAccessToGetWallet() throws Exception {
        String uri = "/wallet";
        String verb = "GET";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(get(uri))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void roleHaveAccessToBuyStock() throws Exception {
        String uri = "/wallet/buy";
        String verb = "POST";
        String content = """
                {
                    "stock_symbol":"AAPL",
                    "volume":1
                }""";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isOk());
        } else {
            mockMvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(content))
                    .andExpect(status().isForbidden());
        }
    }

    @Test
    void roleHaveAccessToSellStock() throws Exception {
        String uri = "/wallet/sell";
        String verb = "GET";

        if (accessibleURIs.containsKey(uri) && accessibleURIs.get(uri).contains(verb)) {
            mockMvc.perform(get(uri + "/1"))
                    .andExpect(status().is5xxServerError());
        } else {
            mockMvc.perform(get(uri + "/1"))
                    .andExpect(status().isForbidden());
        }
    }
}