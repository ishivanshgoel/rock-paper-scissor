package com.stonepaperscissor.exception;

public class GameNotFoundException extends Exception {
	public GameNotFoundException() {
		super("Provided pair of userId and gameId does not associate");
	}
}
