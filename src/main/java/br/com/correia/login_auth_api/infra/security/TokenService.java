package br.com.correia.login_auth_api.infra.security;

import static com.auth0.jwt.algorithms.Algorithm.HMAC256;
import static java.time.LocalDateTime.now;
import static java.time.ZoneOffset.of;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.correia.login_auth_api.model.user.User;

@Service
public class TokenService {

	private static final String APLICATION_NAME = "login-auth-api";
	private static final int DUAS_HORAS = 2;
	private static final String GTM_BRASIL = "-3";

	@Value("${api.security.token.secret}")
	private String secret;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = HMAC256(secret);
			String token = JWT.create().withIssuer(APLICATION_NAME).withSubject(user.getEmail())
					.withExpiresAt(generateExpirationDate()).sign(algorithm);
			return token;
		} catch (JWTCreationException error) {
			throw new RuntimeException("Erro ao autenticar.");
		}
	}

	public String validateToken(String token) {
		try {
			Algorithm algorithm = HMAC256(secret);
			return JWT.require(algorithm).withIssuer(APLICATION_NAME).build().verify(token).getSubject();
		} catch (JWTVerificationException e) {
			return null;
		}
	}

	private Instant generateExpirationDate() {
		return now().plusHours(DUAS_HORAS).toInstant(of(GTM_BRASIL));
	}
}
