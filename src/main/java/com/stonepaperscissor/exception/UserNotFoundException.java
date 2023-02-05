package com.stonepaperscissor.exception;

public class UserNotFoundException extends Exception {
	public UserNotFoundException() {
		super("userId does not exist");
	}
}
