package com.asifiqbalsekh.demo.CountryCurrencyAPI.service;

import com.asifiqbalsekh.demo.CountryCurrencyAPI.dto.LoginResponse;
import com.asifiqbalsekh.demo.CountryCurrencyAPI.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private final String SECRET_KEY;

    public JWTService() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk = keyGen.generateKey();
            SECRET_KEY = Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public LoginResponse generateToken(User loggedUser) {
        Map<String, Object> myClaims = new HashMap<>();
        myClaims.put("fullname", loggedUser.getFullName());
        var token= Jwts
                    .builder()
                    .claims(myClaims)
                    .subject(loggedUser.getUsername())
                    .issuedAt(new Date(System.currentTimeMillis()))
                    .expiration(new Date(System.currentTimeMillis()+1000*60*60*24))
                    .signWith(getSecretKey())
                    .compact();

        return new LoginResponse("Login Successful",token);
    }

    private SecretKey getSecretKey() {
        byte[]keyByte= Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);

    }

    // Validating Token
    public boolean validateToken(String token, UserDetails userDetails) {
        final Claims claims = extractAllClaim(token);
        final String userName = claims.getSubject();
        final String fullName = claims.get("fullname", String.class);


        return userName.equals(userDetails.getUsername()) && fullName.equals(((User) userDetails).getFullName())
                && !isTokenExpired(claims);
    }

    private boolean isTokenExpired(Claims claims){
        return claims.getExpiration().before(new Date());
    }

    //New extraction Method
    private Claims extractAllClaim(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String extractUserName(String token) {
        final Claims claims = extractAllClaim(token);
        return claims.getSubject();
    }
}