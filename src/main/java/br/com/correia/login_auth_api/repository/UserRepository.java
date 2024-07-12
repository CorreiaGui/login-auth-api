package br.com.correia.login_auth_api.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.correia.login_auth_api.model.user.User;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByEmail(String email);
	
}
