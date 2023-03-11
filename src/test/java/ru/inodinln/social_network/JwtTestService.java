package ru.inodinln.social_network;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTestService {
    @Value("${jwt.token.secret}")
    private String SECRET_KEY;


    public String getValidToken(String eMail){
        return generateValidToken(new HashMap<>(), eMail);
    }

    public String getExpiredToken(String eMail){
        String token = generateExpiredToken(new HashMap<>(), eMail);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return token;
    }

    private String generateValidToken(Map<String, Object> extraClaims, String eMail){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(eMail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 3600 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private String generateExpiredToken(Map<String, Object> extraClaims, String eMail){
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(eMail)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
