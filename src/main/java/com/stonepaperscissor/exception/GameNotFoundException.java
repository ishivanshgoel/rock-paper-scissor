package com.stonepaperscissor.exception;

public class GameNotFoundException extends Exception {
	public GameNotFoundException() {
		super("gameId does not exist");
	}
}
