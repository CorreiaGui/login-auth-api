package br.com.correia.login_auth_api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.correia.login_auth_api.model.user.User;
import br.com.correia.login_auth_api.repository.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping(value = "/{email}")
	public User getUserByEmail(@PathVariable String email) {
		User user = repository.findByEmail(email).orElseThrow(() -> new RuntimeException());
		return ResponseEntity.ok(user).getBody();
	}
}
