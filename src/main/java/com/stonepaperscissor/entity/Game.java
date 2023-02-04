package com.stonepaperscissor.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Game {
	
	@Getter
	@Setter
	private ArrayList<String> players;
	
	@Getter
	@Setter
	private String winnerId;
	
	@Getter
	@Setter
	private Boolean isCompleted;
}
