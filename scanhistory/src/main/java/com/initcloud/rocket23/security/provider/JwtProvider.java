package com.initcloud.rocket23.security.provider;

import com.initcloud.rocket23.common.enums.ResponseCode;
import com.initcloud.rocket23.common.exception.ApiAuthException;
import com.initcloud.rocket23.common.exception.ApiException;
import com.initcloud.rocket23.security.config.SecurityProperties;
import com.initcloud.rocket23.security.dto.Token;
import com.initcloud.rocket23.security.dto.UsernameToken;
import com.initcloud.rocket23.security.service.CustomUserDetailsService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.openssl.PEMKeyPair;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.PrivateKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider implements TokenProvider {

    private final CustomUserDetailsService userDetailsService;
    private final SecurityProperties properties;

    public Token create(String username, String key) {
        Date now = new Date();
        String accessToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRED_TIME_ACCESS))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + EXPIRED_TIME_REFRESH))
                .signWith(SignatureAlgorithm.HS256, key)
                .compact();

        return new UsernameToken(username, accessToken, refreshToken);
    }

    public Token refreshAccessToken(String refreshToken, String key) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(refreshToken)
                    .getBody();
            String username = claims.getSubject();

            // Generate a new AccessToken with the same username
            Date now = new Date();
            String newAccessToken = Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(now)
                    .setExpiration(new Date(now.getTime() + EXPIRED_TIME_ACCESS))
                    .signWith(SignatureAlgorithm.HS256, key)
                    .compact();

            return new UsernameToken(username, newAccessToken, refreshToken);
        } catch (JwtException e) {
            // Handle exception (e.g., token validation failure)
            return null;
        }
    }

    public String getUsername(String token, String key) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    @Override
    public boolean validate(String token, String key) {
        try {
            Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);

            return true;
        } catch (SecurityException e) {
            log.error(ResponseCode.INVALID_TOKEN_SIGNATURE.getMessage(), e.getMessage());
            throw new ApiAuthException(ResponseCode.INVALID_TOKEN_SIGNATURE);
        } catch (MalformedJwtException e) {
            log.error(ResponseCode.INVALID_TOKEN.getMessage(), e.getMessage());
            throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.error(ResponseCode.TOKEN_EXPIRED.getMessage(), e.getMessage());
            throw new ApiAuthException(ResponseCode.TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.error(ResponseCode.UNSUPPORTED_TOKEN.getMessage(), e.getMessage());
            throw new ApiAuthException(ResponseCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.error(ResponseCode.EMPTY_TOKEN_CLAIMS.getMessage(), e.getMessage());
            throw new ApiAuthException(ResponseCode.EMPTY_TOKEN_CLAIMS);
        } catch (Exception e) {
            log.error(ResponseCode.INVALID_TOKEN.getMessage(), e.getMessage());
            throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
        }
    }

    public String resolve(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null)
            throw new ApiAuthException(ResponseCode.NULL_TOKEN);

        if (authorization.startsWith("Bearer "))
            return authorization.substring(7);

        if (authorization.startsWith("token "))
            return authorization.substring(6);

        throw new ApiAuthException(ResponseCode.INVALID_TOKEN);
    }

    public String getUsername() {
        Object principal = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
        
        UserDetails userDetails = (UserDetails) principal;
        return userDetails.getUsername();
    }

    public Authentication getAuthentication(String token, String key) {

        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUsername(token, key));

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String createJwtToken() {
        try {
            // Load and parse private key
            PrivateKey privateKey = loadPrivateKey();

            // Create JWT token
            Instant now = Instant.now();
            Date expirationTime = Date.from(now.plus(Duration.ofMinutes(10)));
            JwtBuilder jwtBuilder = Jwts.builder()
                    .setIssuer(properties.getGithubAppId())
                    .setIssuedAt(Date.from(now))
                    .setExpiration(expirationTime)
                    .signWith(SignatureAlgorithm.HS256, privateKey);

            return jwtBuilder.compact();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ApiException(ResponseCode.DATA_MISSING);
        }
    }

    private PrivateKey loadPrivateKey() throws Exception {
        String privateKey = readKeyString();
        Reader pemReader = new StringReader(privateKey);

        PEMParser pemParser = new PEMParser(pemReader);
        PEMKeyPair object = (PEMKeyPair) pemParser.readObject();
        pemReader.close();

        JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
        return converter.getKeyPair(object).getPrivate();
    }

    private String readKeyString() {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(properties.getGithubPrivKeyPath()));
            return new String(encoded);
        } catch (Exception e) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
    }

    public boolean validateTokenExceptExpiration(String jwtToken){
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(properties.getSecret()).parseClaimsJws(jwtToken);
            Date expirationDate = claims.getBody().getExpiration();
            Date currentDate = new Date();

            // 토큰 만료 여부 확인
            return expirationDate.before(currentDate);
        } catch(ExpiredJwtException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUserDetailsFromToken(String jwtToken, String secretKey) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(jwtToken);
            return claims.getBody().getSubject();

        } catch (Exception e) {
            throw new ApiAuthException(ResponseCode.INVALID_CREDENTIALS);
        }
    }
}
