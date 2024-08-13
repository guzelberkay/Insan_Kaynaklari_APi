package org.takim2.insan_kaynaklari_api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.takim2.insan_kaynaklari_api.entity.enums.UserRole;
import org.takim2.insan_kaynaklari_api.exception.HumanResourcesAppException;

import java.util.Date;
import java.util.Optional;

import static org.takim2.insan_kaynaklari_api.exception.ErrorType.*;

@Component
public class JwtTokenManager {
    @Value("${authservice.secret.secret-key}")
    String secretKey;
    @Value("${authservice.secret.issuer}")
    String issuer;
//    Long expireTime = 1000L * 10; // 30 saniyelik bir zaman
    Long expireTime = 1000L * 60 * 30; // 30 dakikalık bir zaman


    public Optional<String> createToken(Long userId,String email,Long companyId){
        String token="";

        //claim içersindeki değerler herkes tarafından görülebilir.

        try {
            token = JWT.create()
                    .withAudience()
                    .withClaim("userId",userId)
                    .withClaim("email",email)
                    .withClaim("companyId",companyId)
                    .withIssuer(issuer)
                    .withIssuedAt(new Date())
                    .sign(Algorithm.HMAC512(secretKey));
            return Optional.of(token);
        } catch (IllegalArgumentException e) {
            throw new HumanResourcesAppException(TOKEN_CREATION_FAILED);
        } catch (JWTCreationException e) {
            throw new HumanResourcesAppException(TOKEN_CREATION_FAILED);
        }
    }

    public Optional<Long> getUserIdFromToken(String token){
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            decodedJWT = verifier.verify(token);

            if(decodedJWT==null){
                return Optional.empty();
            } else {
                return Optional.of(decodedJWT.getClaim("userId").asLong());
            }

        } catch (IllegalArgumentException e) {
            throw new HumanResourcesAppException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new HumanResourcesAppException(TOKEN_VERIFY_FAILED);
        }
    }


    public Optional<String> getEmailFromToken(String token){
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            decodedJWT = verifier.verify(token);

            if(decodedJWT==null){
                return Optional.empty();
            } else {
                return Optional.of(decodedJWT.getClaim("email").asString());
            }

        } catch (IllegalArgumentException e) {
            throw new HumanResourcesAppException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new HumanResourcesAppException(TOKEN_VERIFY_FAILED);
        }
    }


    public Optional<Long> getCompanyIdFromToken(String token){
        DecodedJWT decodedJWT = null;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            decodedJWT = verifier.verify(token);

            if(decodedJWT==null){
                return Optional.empty();
            } else {
                return Optional.of(decodedJWT.getClaim("companyId").asLong());
            }

        } catch (IllegalArgumentException e) {
            throw new HumanResourcesAppException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new HumanResourcesAppException(TOKEN_VERIFY_FAILED);
        }
    }


    public String createToken(Long id, UserRole role) {
        String token = "";
        try {
            token = JWT.create().withAudience()
                    .withClaim("userId",
                            id)
                    .withClaim("role",
                            role.name())
                    .withIssuer(issuer)
                    .withIssuedAt(new Date(System.currentTimeMillis()))
                    .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                    .sign(Algorithm.HMAC512(secretKey));
            return token;
        } catch (IllegalArgumentException e) {
            throw new HumanResourcesAppException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new HumanResourcesAppException(TOKEN_VERIFY_FAILED);
        }
    }


    public Optional<String> getRoleFromToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC512(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).withIssuer(issuer).build();
            DecodedJWT decodedJWT = verifier.verify(token);

            if (decodedJWT == null)
                return Optional.empty();

            String role = decodedJWT.getClaim("role").asString();
            return Optional.of(role);
        } catch (IllegalArgumentException e) {
            throw new HumanResourcesAppException(TOKEN_FORMAT_NOT_ACCEPTABLE);
        } catch (JWTVerificationException e) {
            throw new HumanResourcesAppException(TOKEN_VERIFY_FAILED);
        }

    }



}
