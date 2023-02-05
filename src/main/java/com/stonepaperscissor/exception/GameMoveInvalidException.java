package com.stonepaperscissor.exception;

public class GameMoveInvalidException extends Exception {
	public GameMoveInvalidException() {
		super("Provided move value is invalid");
	}
}
