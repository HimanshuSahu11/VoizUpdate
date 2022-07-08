package com.example.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.dao.UserDao;

import com.example.entities.ErrorClazz;

import com.example.entities.User;

import com.example.service.UserServices;
@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	@Autowired
	UserServices userServices;
	
	
	
	public UserController() {
		System.out.println("<<<<<<< User CONTROLLER INSTANTIATED>>>>>>>>");
	}
	
	@PostMapping("/create")
	public ResponseEntity<?> saveNewUser(@RequestBody User newuser) {
		try {
			newuser= userServices.saveNewUser(newuser);
			return new ResponseEntity<User>(newuser,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			ErrorClazz errorClazz=new ErrorClazz(500,"Email already exist");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@RequestMapping(value = "/login",method=RequestMethod.PUT)
	public ResponseEntity<?> login(@RequestBody User user,HttpSession session){
		User validUser=UserDao.login(user);
		if(validUser==null) {
			ErrorClazz errorClazz=new ErrorClazz(3, "Invalid email id/password.... Please enter Valid Credential");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}else {
			session.setAttribute("email", validUser.getEmail());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
		}
	@RequestMapping(value = "/forgotlogin",method=RequestMethod.PUT)
	public ResponseEntity<?> forgotlogin(@RequestBody User user,HttpSession session){
		User validUser=UserDao.login(user);
		if(validUser==null) {
			ErrorClazz errorClazz=new ErrorClazz(3, "Invalid email id Please enter Valid Email Address");
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.UNAUTHORIZED);
		}else {
			session.setAttribute("email", validUser.getEmail());
			return new ResponseEntity<User>(validUser,HttpStatus.OK);
		}
		}
	@PutMapping("/updatepassword")
	public ResponseEntity<?> updateUser(@RequestBody User user){
		try {
			userServices.updateUser(user);
			return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			ErrorClazz errorClazz=new ErrorClazz(500, e.getMessage());
			return new ResponseEntity<ErrorClazz>(errorClazz,HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	}
	

