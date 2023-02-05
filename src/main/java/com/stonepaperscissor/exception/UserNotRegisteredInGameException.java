package com.stonepaperscissor.exception;

public class UserNotRegisteredInGameException extends Exception {
	public UserNotRegisteredInGameException() {
		super("userId is not registered");
	}
}
