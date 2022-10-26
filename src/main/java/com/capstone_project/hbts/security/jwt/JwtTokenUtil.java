package com.capstone_project.hbts.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenUtil {

    private static final long JWT_TOKEN_VALIDITY_TIME = 24 * 60 * 60 * 5; // 5 days

    @Value("${jwt.secret}")
    private String secretKey;

    // retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        // call method reference getSubject of Claims interface
        return getClaimFromToken(token, Claims::getSubject);
    }

    // retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        // call method reference getExpiration of Claims interface
        return getClaimFromToken(token, Claims::getExpiration);
    }

    // retrieve userId from jwt token
    public String getUserIdFromToken(String token) {
        // call method reference getId of Claims interface
        return getClaimFromToken(token, Claims::getId);
    }

    // get all Claims from token (body, subject, id, etc...)
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    // for retrieving any information from token we will need the secret key matching
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    // check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    // generate token for user
    public String generateToken(String userId, UserDetails userDetails) {
        return doGenerateToken(userId, userDetails.getUsername());
    }

    private String doGenerateToken(String userId, String subject) {
        return Jwts.builder().setSubject(subject).setId(userId).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_TIME * 1000))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    // validate token: check if username from jwt and username from db is equal
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
