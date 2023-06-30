package com.ocal.medheadsecurity.config;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.security.Key;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.ocal.medheadsecurity.MedheadsecurityApplication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

@SpringBootTest(classes = MedheadsecurityApplication.class)
@ExtendWith(SpringExtension.class)
public class JWTGeneratorTest {

    @Autowired
    private JWTGenerator jwtGenerator;

    @MockBean
    private Authentication authentication;


    @Test
    public void testGenerateToken() {
        when(authentication.getName()).thenReturn("UtilisateurTest");
        String token = jwtGenerator.generateToken(authentication);

        assertNotNull(token,"Aucub token n'est généré par la fonction");
        assertFalse(token.isEmpty(),"Le token ne doit pas être une chaine vide");
        
        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(jwtGenerator.getKey())
                .build()
                .parseClaimsJws(token);

        assertEquals("UtilisateurTest", claimsJws.getBody().getSubject(),"Les Noms ne Correspondent pas");
        assertTrue(claimsJws.getBody().getExpiration().after(new Date()),"La Date d'expiration est antérieure");
    }
    
    @Test
    public void testGetUsernameFromJWT() {
        // Génération des données
        String username = "UtilisateurTest";
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null);
        String token = jwtGenerator.generateToken(authentication);
        String extractedUsername = jwtGenerator.getUsernameFromJWT(token);

        // Vérficaition des données 
        assertEquals(username, extractedUsername,"L'extraction doit retourner des noms identiques");
        assertNotEquals("DefinitivementpasUnUtilisateur",extractedUsername,"L'extraction doit retourner faux");
    }
    
    @Test
    public void testValidateToken() {
    	// Définitions
        Authentication authentication = new UsernamePasswordAuthenticationToken("TestUser", null);
        String validToken = jwtGenerator.generateToken(authentication);
        String invalidToken = "InvalidToken";

        // Test
        assertTrue(jwtGenerator.validateToken(validToken),"Le Token doit être valide");
        try {
            jwtGenerator.validateToken(invalidToken);
            assertTrue(false,"Le Token doit être non valide");
        } catch (AuthenticationCredentialsNotFoundException ex) {
        }

    }
}
