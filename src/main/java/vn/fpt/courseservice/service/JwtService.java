package vn.fpt.courseservice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.fpt.courseservice.enums.TokenType;
import vn.fpt.courseservice.model.User;
import java.text.ParseException;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final RedisService redisService;

    public String generateToken(User user, TokenType tokenType) {

        JWSAlgorithm algorithm = tokenType == TokenType.ACCESS_TOKEN ? JWSAlgorithm.HS384 : JWSAlgorithm.HS512;
        // 1 Header
        JWSHeader header = new JWSHeader(algorithm);
        // 2 Payload
        Date issueTime = new Date();

        long expiration = tokenType == TokenType.ACCESS_TOKEN ? 5 : 14;
        Date expirationTime = Date.from(issueTime.toInstant().plus(expiration, tokenType == TokenType.ACCESS_TOKEN ? ChronoUnit.MINUTES : ChronoUnit.DAYS));

        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getEmail())
                .issueTime(issueTime)
                .expirationTime(expirationTime)
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(claimsSet.toJSONObject());
        // 3. Signature
        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(secretKey));
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }

        return  jwsObject.serialize();
    }

    public boolean verifyToken(String token) throws ParseException {
        SignedJWT signedJWT = SignedJWT.parse(token);
        Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();

        if(expirationTime.before(new Date())) {
            return false;
        }

        String tokenBlackList = redisService.get(signedJWT.getJWTClaimsSet().getJWTID());

        if(tokenBlackList != null) {
            return false;
        }

        return true;
    }

}
