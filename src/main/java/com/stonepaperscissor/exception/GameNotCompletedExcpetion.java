package com.stonepaperscissor.exception;

public class GameNotCompletedExcpetion extends Exception {
	public GameNotCompletedExcpetion() {
		super("Game is not yet completed");
	}
}
