package com.danielspring.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.danielspring.app.entity.User;
import com.danielspring.app.service.S3Service;
import com.danielspring.app.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private S3Service s3Service;
	
	// Create a new User
	@PostMapping
	public ResponseEntity<?> create (@RequestBody User user){
		user.setImagenUrl(s3Service.getObjectUrl(user.getImagenPath()));
		user.setCedUrl(s3Service.getObjectUrl(user.getCedPath()));
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user));		
	}
	
	
	// Read an User
	@GetMapping("/{id}")
	public ResponseEntity<?> read(@PathVariable(value = "id") Long userId){
		
		Optional<User> oUser = userService.findById(userId);
		
		if(!oUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		
		return ResponseEntity.ok(oUser);
		
	}
	
	
	// Update an User
	@PutMapping("/{id}")
	public ResponseEntity<?> update(@RequestBody User userDetails, @PathVariable(value = "id") Long userId){
		
		Optional<User> user = userService.findById(userId);
		if(!user.isPresent()){
			return ResponseEntity.notFound().build();
		}
		
		//BeanUtils.copyProperties(userDetails, user.get());
		user.get().setName(userDetails.getName());
		user.get().setPass(userDetails.getPass());
		user.get().setEmail(userDetails.getEmail());
		user.get().setEnabled(userDetails.getEnabled());
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(user.get()));
		
	}
	
	// Delete an User
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Long userId){
		
		if(!userService.findById(userId).isPresent()) {			
			return ResponseEntity.notFound().build();
		}
		
		userService.deleteById(userId);
		return ResponseEntity.ok().build();
		
	}
	
	
	// Read all Users
	@GetMapping
	public List<User> readAll(){
		
		List<User> users = StreamSupport
				.stream(userService.findAll().spliterator(),false)
				.peek(user -> user.setImagenUrl(s3Service.getObjectUrl(user.getImagenPath())))
				.peek(user -> user.setCedUrl(s3Service.getObjectUrl(user.getCedPath())))
				.collect(Collectors.toList());
		
		return users;
		
	}

	
	
	
	
}
