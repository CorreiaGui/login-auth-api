package br.com.correia.login_auth_api.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.correia.login_auth_api.dto.LoginRequestDTO;
import br.com.correia.login_auth_api.dto.RegisterRequestDTO;
import br.com.correia.login_auth_api.dto.ReponseDTO;
import br.com.correia.login_auth_api.infra.security.TokenService;
import br.com.correia.login_auth_api.model.user.User;
import br.com.correia.login_auth_api.repository.UserRepository;
import br.com.correia.login_auth_api.services.AuthService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final UserRepository repository;
	
	private final PasswordEncoder passwordEncoder;

	private final TokenService tokenService;

	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<ReponseDTO> login(@RequestBody LoginRequestDTO body){
		User user = this.repository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("Usuário não encontrado."));
		if (passwordEncoder.matches(body.password(), user.getPassword())) {
			String token = this.tokenService.generateToken(user);
			return ResponseEntity.ok(new ReponseDTO(user.getName(), token));
		}
		return ResponseEntity.badRequest().build(); 
	}
	
	@PostMapping("/register")
	public ResponseEntity<ReponseDTO> register(@RequestBody RegisterRequestDTO body) {
		Optional<User> usuarioBase = this.repository.findByEmail(body.email());
		if (usuarioBase.isEmpty()) {
			User user = authService.saveUser(body);
			String token = this.tokenService.generateToken(user);
			ReponseDTO dto = new ReponseDTO(user.getName(), token);
			return ResponseEntity.ok(dto);
		}
		return ResponseEntity.badRequest().build();
	}
	
}
