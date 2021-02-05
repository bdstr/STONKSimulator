package net.kloczkowski.STONKSimulator.API.security;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;

import java.util.HashMap;
import java.util.Set;

@SpringBootTest
@WithAnonymousUser
class UnknownAuthorizationTest extends GenericAuthorizationTest {

    @BeforeAll
    static void setAccessibleURIs() {
        HashMap<String, Set<String>> uris = new HashMap<>();
        uris.put("/login", Set.of("POST"));
        uris.put("/register", Set.of("POST"));
        accessibleURIs = uris;
    }
}