package com.stonepaperscissor.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.stonepaperscissor.entity.Game;

@Repository
public class GameRepository {
	
	/***
	 * (gameId, Game) stores the game details corresponding to a unique game id 
	 */
	private HashMap<String, Game> gameRecords = new HashMap<String, Game>();
	
	/**
	 * creates new game and returns a unique id
	 * @param Game (game details)
	 * @return String (a unique identifier for game)
	 */
	public String createNewGame(Game game) {
		String newGameId = UUID.randomUUID().toString();
		this.gameRecords.put(newGameId, game);
		return newGameId;
	}
	
	/**
	 * returns the game details
	 * @param gameId a unique identifier for game
	 * @return details corresponding to the game
	 */
	public Game getGameDetails(String gameId) {
		return this.gameRecords.get(gameId);
	}
	
	/**
	 * updates the winner of game
	 * @param gameId unique identifier for game
	 * @param winnerId unique identifier for user, represents the game winner here
	 * @return true/false where true means status updated, false otherwise
	 */
	public Boolean updateGameWinner(String gameId, String winnerId) {
		Game game = this.gameRecords.get(gameId);
		if(game == null) {
			return false;
		}
		game.setWinnerId(winnerId);
		this.gameRecords.put(gameId, game);
		return true;
	}
	
	/**
	 * finds the winnerId of game
	 * @param gameId unique identifier for game
	 * @return winner id of the game
	 */
	public String getGameWinnerId(String gameId) {
		Game game = this.gameRecords.get(gameId);
		if(game == null) {
			return "Game does not exist";
		}
		return this.gameRecords.get(gameId).getWinnerId();
	}
}
