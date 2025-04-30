package com.example.backend.services;


import com.example.backend.models.Users;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service

public class JwtService {

  private static String MY_SECRET_KEY="thisismysecretkey123SDFKJH89836739298987697398239845HGSKJDBSVSBVKSBVC456789";
    public String getToken(Users user){

        return getToken(new HashMap<>(),user);
    }

    private String getToken(Map<String, Object> objectObjectHashMap, Users user) {
        return Jwts.builder()
                .setClaims(Map.of("name",user.getUsername()))
                .setClaims(objectObjectHashMap)
                .setSubject(user.getUsername())
                  .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1800000))
                .signWith(getKey(),  SignatureAlgorithm.HS256)
                .compact();
    }
    private Key getKey(){
        byte[] keyByte = Decoders.BASE64.decode(MY_SECRET_KEY);
        return Keys.hmacShaKeyFor(keyByte);

    }


    public String getUserNameFromToken(String token) {
    return findClaim(token,Claims::getSubject);
    }
    private Date getExpirationToken(String token){
        return findClaim(token,Claims::getExpiration);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username= getUserNameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token)) ;
    }
    private boolean isTokenExpired(String token){
        return getExpirationToken(token).before(new Date());
    }

    //a investigar la clase Claims que hace

    /**
     * diferencia entre metodo
     * paseClaimsJws
     *  y
     *  parseClaimsJwt ??
     *
     */
    private Claims getClaims(String token){
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public  <T> T findClaim(String token , Function<Claims,T> claimsResolver){
        final Claims claims = getClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Users user){

        return getToken(new HashMap<>(),user);
    }
}
