package com.familywebshop.stylet.service.user;

import com.familywebshop.stylet.model.user.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;

@Service
public interface JwtService {

    String extractUsernameFromToken(String token);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    String generateToken(User user);

    String generateToken(Map<String, Object> extraClaims, User user);

    boolean isTokenValid(String token, UserDetails userDetails);
}