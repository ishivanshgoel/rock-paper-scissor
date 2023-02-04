package com.stonepaperscissor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.payloads.user.CreateNewUserDto;
import com.stonepaperscissor.payloads.user.CreateNewUserResponse;
import com.stonepaperscissor.service.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	/***
	 * @param CreateNewUserDto
	 * @return CreateNewUserResponse
	 */
	@PostMapping("/user/add")
	public ResponseEntity<CreateNewUserResponse> addUser(@RequestBody CreateNewUserDto createNewUserDto) {
		CreateNewUserResponse createNewUserResponse = this.userService.registerNewUser(createNewUserDto);
		return new ResponseEntity<CreateNewUserResponse> (createNewUserResponse, HttpStatus.CREATED);
	}
}
