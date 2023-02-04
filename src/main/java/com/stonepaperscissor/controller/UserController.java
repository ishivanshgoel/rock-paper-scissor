package com.stonepaperscissor.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	/**
	 * register new user
	 * @return
	 */
	@PostMapping("/user/add")
	public Boolean addUser() {
		return true;
	}
}
