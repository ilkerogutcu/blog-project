package com.blog.project.config;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    /**
     * Serializable hata vermemesi için
     */
    private static final long serialVersionUID = -2550185165626007488L;

    /**
     * Token sonlanma süresi
     */
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    /**
     * Secret key belirliyoruz
     */
    @Value("${jwt.secret}")
    private String secret;

    /**
     * @param token
     * @return userName(string)
     * TOKEN IÇINDEN USERNAME ALIYORUZ
     */

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //jwt token içinden token sonlanma zamanını alıyoruz
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    /**
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return claimsResolver.apply(claims)
     * <p>
     * TOKEN IÇINDEN CLAIM ÇEKIYORUZ
     */
    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    /**
     * @param token
     * @return claims
     * TOKENDAN HERHANGI BIR BILGI ALMAK IÇIN SECRETKEY'E IHTIYACIMIZ OLACAK.
     */
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    /**
     * @param token
     * @return true or false
     * TOKEN SONLANMIŞ MI KONTROLÜ
     */
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * @param userDetails
     * @return Token
     * TOKEN OLUŞTURUYORUZ
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * @param claims
     * @param subject
     * @return Jwts
     * TOKEN OLUŞTURUYORUZ
     * "SETSUBJECT" ILE TOKEN IÇINE KONUYU SET EDIYORUZ
     * "SETISSUEDAT" ILE TOKEN OLUŞTURMA TARIHINI SET EDIYORUZ
     * "SETEXPIRATION" ILE TOKEN BITIŞ SÜRESINI SET EDIYORUZ
     * "SIGNWITH" ILE TOKEN IMZA TÜRÜNÜ BELIRLIYORUZ
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().
                setClaims(claims).
                setSubject(subject).
                setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    /**
     * @param token
     * @param userDetails
     * @return true or false
     * TOKEN DOĞRULAMA IŞLEMI
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}