package vn.fpt.courseservice.service;

import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.dto.request.LoginRequest;
import vn.fpt.courseservice.dto.response.LoginResponse;
import vn.fpt.courseservice.enums.TokenType;
import vn.fpt.courseservice.model.User;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final RedisService redisService;

    public LoginResponse login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        User user = (User) authenticate.getPrincipal();

        String accessToken = jwtService.generateToken(user, TokenType.ACCESS_TOKEN);
        String refreshToken = jwtService.generateToken(user, TokenType.REFRESH_TOKEN);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public void logout(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);

        String jwtId = signedJWT.getJWTClaimsSet().getJWTID();
        long expirationTim = signedJWT.getJWTClaimsSet().getExpirationTime().getTime();
        long now = System.currentTimeMillis();

        int duration = (int) (expirationTim - now);

        redisService.add(jwtId, token, duration);
    }


}
