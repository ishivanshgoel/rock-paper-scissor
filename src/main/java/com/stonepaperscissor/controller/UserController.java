package com.stonepaperscissor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.payloads.common.ApiErrorResponse;
import com.stonepaperscissor.payloads.user.CreateNewUserDto;
import com.stonepaperscissor.payloads.user.CreateNewUserResponse;
import com.stonepaperscissor.service.UserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	@Autowired
	private UserService userService;
	
	/***
	 * register new user by name
	 * @param CreateNewUserDto
	 * @return CreateNewUserResponse
	 */
	@PostMapping("/api/v1/user")
	public ResponseEntity addUser(@Valid @RequestBody CreateNewUserDto createNewUserDto) 
			throws Exception {
		try {
			CreateNewUserResponse createNewUserResponse = this.userService.registerNewUser(createNewUserDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createNewUserResponse);
		} catch(Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(e.getMessage()));
		}
	}
}
