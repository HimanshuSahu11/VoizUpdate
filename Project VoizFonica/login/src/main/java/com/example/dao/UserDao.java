package com.example.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.example.entities.User;
@Repository
public interface UserDao extends JpaRepository<User, Integer>{

	Optional<User> findByEmailAndPassword(String email,String password);
	
	Optional<User> findByEmail(String email);

	public static User login(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	
}
