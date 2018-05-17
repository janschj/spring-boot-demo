package org.baeldung.test;

import static org.junit.Assert.assertTrue;

import org.baeldung.MediaAgencyServerApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MediaAgencyServerApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class JwtClaimsVerifierIntegrationTest {

    @Autowired
    private JwtTokenStore tokenStore;

    @Test
    public void whenTokenDontContainIssuer_thenSuccess() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJ1c2VyX25hbWUiOiJ1c2VyQG1lLmNvbSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjU4MDI2NjAsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiYzgwMWRlMTctMWQ1YS00NjQwLWJmOTYtOWU1NzFjMWU4YzAwIiwiY2xpZW50X2lkIjoib25lY2xpZW50In0.EDOG19-QPjSbysgDJuDQYzxil2-ZFedgFHd4be87QgoJbhn_ZbKDMjjOeqEmzXOFouarw6FGHFJydc7G6yMK1oM_lxZWwkytlxCWvCJGgWPANYcsdfshdkwfF_c46AKixf5XDNclq0whkdmIgEI5VMdZIPlS3U6QrvEZFLmo0cybFENF72vrRWpskr34J-DrhqTXMjOQwZH6RiXFo6v3SPFBu6XlXSOEDECQVUkeMcY565DrFza98DnOhbCkYOHD6D9I66kJXwIzoameafw4DR8Trx99tXD77qD0wHykKyVE4tYejSgHtMB0F77xf3URFHg9Zxt5rWsAR6iqTIWUOQ";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
      System.out.println("xxxxxxxxxxxxxxx" );
        assertTrue(auth.isAuthenticated());
    }

    @Test
    @Ignore 
    public void whenTokenContainValidIssuer_thenSuccess() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwOi8vbG9jYWxob3N0OjgwODEiLCJleHAiOjE1MDQ3NDA3NDQsInVzZXJfbmFtZSI6ImpvaG4iLCJhdXRob3JpdGllcyI6WyJST0xFX1VTRVIiXSwianRpIjoiYzYzN2MxY2EtYWM2My00ZGVlLWI2NDItYjJiMTcwNGEzODNiIiwiY2xpZW50X2lkIjoiZm9vQ2xpZW50SWRQYXNzd29yZCIsInNjb3BlIjpbImZvbyIsInJlYWQiLCJ3cml0ZSJdLCJpYXQiOjE1MDQ3MzcxNDR9.G3vVR314v5bKiMJow0wRE0ZOXSakoRLxBSM9_PZeMms";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    @Ignore 
    public void whenTokenContainInvalidIssuer_thenException() {
        final String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoyLCJ1c2VyX25hbWUiOiJ1c2VyQG1lLmNvbSIsInNjb3BlIjpbInJlYWQiLCJ3cml0ZSJdLCJleHAiOjE1MjU4MDI2NjAsImF1dGhvcml0aWVzIjpbIlVTRVIiXSwianRpIjoiYzgwMWRlMTctMWQ1YS00NjQwLWJmOTYtOWU1NzFjMWU4YzAwIiwiY2xpZW50X2lkIjoib25lY2xpZW50In0.EDOG19-QPjSbysgDJuDQYzxil2-ZFedgFHd4be87QgoJbhn_ZbKDMjjOeqEmzXOFouarw6FGHFJydc7G6yMK1oM_lxZWwkytlxCWvCJGgWPANYcsdfshdkwfF_c46AKixf5XDNclq0whkdmIgEI5VMdZIPlS3U6QrvEZFLmo0cybFENF72vrRWpskr34J-DrhqTXMjOQwZH6RiXFo6v3SPFBu6XlXSOEDECQVUkeMcY565DrFza98DnOhbCkYOHD6D9I66kJXwIzoameafw4DR8Trx99tXD77qD0wHykKyVE4tYejSgHtMB0F77xf3URFHg9Zxt5rWsAR6iqTIWUOQ";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    @Ignore 
    public void whenTokenDontContainUsername_thenException() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDQ3NDA3ODEsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJjNjM3YzFjYS1hYzYzLTRkZWUtYjY0Mi1iMmIxNzA0YTM4M2IiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sImlhdCI6MTUwNDczNzE4MX0.SEX15_d49_YOMw1UAPvh9pnPBKnATJUY-wN8r9kSVxA";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }

    @Test(expected = InvalidTokenException.class)
    @Ignore 
    public void whenTokenContainEmptyUsername_thenException() {
        final String tokenValue = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MDQ3NDA5MjEsInVzZXJfbmFtZSI6IiIsImF1dGhvcml0aWVzIjpbIlJPTEVfVVNFUiJdLCJqdGkiOiJjNjM3YzFjYS1hYzYzLTRkZWUtYjY0Mi1iMmIxNzA0YTM4M2IiLCJjbGllbnRfaWQiOiJmb29DbGllbnRJZFBhc3N3b3JkIiwic2NvcGUiOlsiZm9vIiwicmVhZCIsIndyaXRlIl0sImlhdCI6MTUwNDczNzMyMX0.MM1RkBy90rTaDkCGGP1j9mKfSNcoRcHEa8WLC7-zR6A";
        final OAuth2Authentication auth = tokenStore.readAuthentication(tokenValue);
        assertTrue(auth.isAuthenticated());
    }
}