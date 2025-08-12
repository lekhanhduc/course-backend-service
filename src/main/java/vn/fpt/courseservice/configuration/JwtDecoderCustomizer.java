package vn.fpt.courseservice.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;
import vn.fpt.courseservice.service.JwtService;
import javax.crypto.spec.SecretKeySpec;
import java.text.ParseException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtDecoderCustomizer implements JwtDecoder {

    @Value("${jwt.secret-key}")
    private String secretKey;

    private final JwtService jwtService;
    private NimbusJwtDecoder nimbusJwtDecoder = null;

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            boolean isValid = jwtService.verifyToken(token);

            if(!isValid){
                throw new JwtException("Invalid token");
            }

            if(Objects.isNull(nimbusJwtDecoder)){
                SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HS384");
                nimbusJwtDecoder = NimbusJwtDecoder.withSecretKey(secretKeySpec)
                        .macAlgorithm(MacAlgorithm.HS384)
                        .build();
            }

            return nimbusJwtDecoder.decode(token);

        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
