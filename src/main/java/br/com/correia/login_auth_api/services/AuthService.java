package br.com.correia.login_auth_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.correia.login_auth_api.dto.RegisterRequestDTO;
import br.com.correia.login_auth_api.model.user.User;
import br.com.correia.login_auth_api.repository.UserRepository;

@Service
public class AuthService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository repository;
	
	public User saveUser(RegisterRequestDTO body) {
		User user = new User();
		user.setPassword(passwordEncoder.encode(body.password()));
		user.setEmail(body.email());
		user.setName(body.nome());
		this.repository.save(user);
		return user;
	}
	
}
