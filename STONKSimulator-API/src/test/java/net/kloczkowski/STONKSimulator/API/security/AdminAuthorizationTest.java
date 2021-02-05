package net.kloczkowski.STONKSimulator.API.security;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.HashMap;
import java.util.Set;

@SpringBootTest
@WithMockUser(username = "admin", authorities = {"ROLE_ADMIN"})
class AdminAuthorizationTest extends GenericAuthorizationTest {

    @BeforeAll
    static void setAccessibleURIs() {
        HashMap<String, Set<String>> uris = new HashMap<>();
        uris.put("/login", Set.of("POST"));
        uris.put("/register", Set.of("POST"));
        uris.put("/profile", Set.of("GET"));
        uris.put("/wallet", Set.of("GET", "PUT"));
        uris.put("/wallet/", Set.of("GET", "POST"));
        accessibleURIs = uris;
    }
}