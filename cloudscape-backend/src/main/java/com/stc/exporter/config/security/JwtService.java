package com.stc.exporter.config.security;


import com.stc.exporter.app.user.dao.TokenDao;
import com.stc.exporter.app.user.dao.UserRegistrationDao;
import com.stc.exporter.config.secret.Secret;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.boot.actuate.web.exchanges.Include.AUTHORIZATION_HEADER;

@Service
@RequiredArgsConstructor
public class JwtService {

	Key key = Keys.hmacShaKeyFor(Secret.JWT_SECRET_KEY.getBytes(StandardCharsets.UTF_8));
//    SecretKey key = Jwts.SIG.HS256.key().build(); // 지속적으로 변화하는 secret key

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    final CustomUserDetailsService customUserDetailsService;

    final TokenDao tokenDao;

    final UserRegistrationDao userRegistrationDao;

    public int extractUserIdx(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userIdx", int.class);
    }

    public Date extractIssuedAt(String token) {
        return extractClaim(token, Claims::getIssuedAt);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        // 가정: getSignKey() 메서드는 서명 검증에 사용할 키를 반환합니다.
        try {
            return Jwts.parser()
                    .verifyWith((SecretKey) key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            logger.error("Token expired: ", e);
            throw new RuntimeException(e);
        }  catch (JwtException e) {
            logger.error("Token error: ", e);
            throw new RuntimeException(e);
        }
    }

    // HttpServletRequest에서 Authorization Header를 통해 access token을 추출하는 메서드입니다.
    public String getAccessToken(HttpServletRequest httpServletRequest) {
        String bearerToken = httpServletRequest.getHeader(String.valueOf(AUTHORIZATION_HEADER));
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return "Failed to access jwt token";
    }

    // HttpServletRequest에서 Authorization Header를 통해 access token을 추출하는 메서드입니다.
    public String getRefreshToken(HttpServletRequest request) {
        String refreshToken = request.getHeader("Authorization-refresh");
        if (StringUtils.hasText(refreshToken)) {
            return refreshToken;
        }
        return "Failed to access refresh token";
    }

    public int getUserIdx() {
        return 1;
    }


    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, int userIdx) {
        try {
            final int userIdxFromToken = extractUserIdx(token);
            return (userIdxFromToken == userIdx && !isTokenExpired(token));
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false; // 위의 예외 발생 시 false 반환
    }


    public String generateToken(int userIdx) {
        return createToken(userIdx);

    }



    private String createToken(int userIdx) {

        Instant issuedDt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Date expiryDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)); // 24시간 후 만료

        return Jwts.builder()
                .claim("userIdx", userIdx)
                .issuedAt(Date.from(issuedDt))
                .expiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateRefreshToken() {
        return createRefreshToken();

    }

    private String createRefreshToken() {
        Instant issuedDt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
        Date expiryDate = new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 21)); // 21일(3주) 후 만료

        return Jwts.builder()
                .setIssuedAt(Date.from(issuedDt))
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public void updateRefreshToken(int userIdx, String refreshToken) {
        try {
            userRegistrationDao.findUserByUserIdx(userIdx);
            tokenDao.updateRefreshToken(userIdx, refreshToken);
            logger.info("Refresh Token updated successfully for userId: " + userIdx);

        } catch (UsernameNotFoundException e) {
            logger.error("User Not Found: ", e);
            throw new RuntimeException(e);
        }

    }

    public void destroyRefreshToken(int userIdx) {

        try {
            userRegistrationDao.findUserByUserIdx(userIdx);
            tokenDao.deleteRefreshToken();

        } catch (UsernameNotFoundException e) {
            logger.error("User Not Found: ", e);
            throw new RuntimeException(e);
        }

    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);

        Map<String, String> tokenMap = new HashMap<>();

    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);

    }

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader("Authorization-refresh", refreshToken);
    }

    public String checkRefreshTokenAndReIssueAccessToken(HttpServletResponse response, String refreshToken) {
        CustomUserDetails user = tokenDao.getUserByRefreshToken(refreshToken);
        if (user != null && !isTokenExpired(refreshToken)) {
            String accessToken = generateToken(user.getUserIdx());
            setAccessTokenHeader(response, "Bearer " + accessToken);
            response.setStatus(HttpServletResponse.SC_OK); // 성공적으로 액세스 토큰을 재발급했을 경우
            return accessToken; // 재발급된 액세스 토큰 반환
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 적절한 HTTP 응답 코드 설정
            return "Invalida or expired refresh token";
        }
    }

}