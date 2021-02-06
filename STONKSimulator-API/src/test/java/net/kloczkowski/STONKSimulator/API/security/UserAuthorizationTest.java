package net.kloczkowski.STONKSimulator.API.security;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashMap;
import java.util.Set;

@SpringBootTest
@WithMockUser(username = "user", authorities = {"ROLE_USER"})
class UserAuthorizationTest extends GenericAuthorizationTest {

    @BeforeAll
    static void setAccessibleURIs() {
        HashMap<String, Set<String>> uris = new HashMap<>();
        uris.put("/login", Set.of("POST"));
        uris.put("/register", Set.of("POST"));
        uris.put("/profile", Set.of("GET"));
        uris.put("/wallet", Set.of("GET"));
        uris.put("/wallet/", Set.of("GET", "POST"));
        uris.put("/wallet/buy", Set.of("POST"));
        uris.put("/wallet/sell", Set.of("DELETE"));
        accessibleURIs = uris;
    }
}