package com.stonepaperscissor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.payloads.user.CreateNewUserDto;
import com.stonepaperscissor.payloads.user.CreateNewUserResponse;
import com.stonepaperscissor.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	/***
	 * @param CreateNewUserDto
	 * @return CreateNewUserResponse
	 */
	@PostMapping("/")
	public ResponseEntity<CreateNewUserResponse> addUser(@RequestBody CreateNewUserDto createNewUserDto) 
			throws Exception {
		try {
			CreateNewUserResponse createNewUserResponse = this.userService.registerNewUser(createNewUserDto);
			return new ResponseEntity<CreateNewUserResponse> (createNewUserResponse, HttpStatus.CREATED);	
		} catch(Exception e) {
			throw e;
		}
	}
}
