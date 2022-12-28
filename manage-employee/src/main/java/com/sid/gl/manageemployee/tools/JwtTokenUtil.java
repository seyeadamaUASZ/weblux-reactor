package com.sid.gl.manageemployee.tools;

import com.sid.gl.manageemployee.constants.SecurityConstant;
import com.sid.gl.manageemployee.exceptions.TokenException;
import com.sid.gl.manageemployee.models.Token;
import com.sid.gl.manageemployee.repository.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtTokenUtil {

    @Autowired
    TokenRepository tokenRepository;

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public Date extractIssued(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SecurityConstant.SECRET).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Token generateToken(Map<String, Object> claims, String username) {
        return createToken(claims, username);
    }

    public String refresh(String refreshToken) throws Exception {
        Token oldToken = tokenRepository.findByRefreshToken(refreshToken).orElseThrow(()->new TokenException("unknow token refresh"));
        Map claims = extractAllClaims(refreshToken);
        String accessToken = Jwts.builder().setClaims(claims).setSubject(extractUsername(refreshToken)).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET).compact();
        //oldToken.setAccessToken(accessToken);
        //tokenRepository.save(oldToken);
        return accessToken;
    }

    private Token createToken(Map<String, Object> claims, String subject) {

        Token token = new Token();
        token.setUsername(subject);
        token.setAccessToken(Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.ACCESS_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET).compact());

        token.setRefreshToken(Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstant.REFRESH_TOKEN_EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SecurityConstant.SECRET).compact());

        return token; //tokenRepository.save(token);
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
