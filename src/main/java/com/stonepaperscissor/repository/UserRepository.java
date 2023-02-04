package com.stonepaperscissor.repository;

import java.util.HashMap;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.stonepaperscissor.entity.Player;

@Repository
public class UserRepository {
	
	/**
	 * (userId, name) stores player's data
	 */
	private HashMap<String, Player> players = new HashMap<String, Player>();
	
	/**
	 * registers new user
	 * @param name of the player
	 * @return a unique player id
	 */
	public String addNewUser(Player player) {
		String newPlayerId = UUID.randomUUID().toString();
		this.players.put(newPlayerId, player);
		return newPlayerId;
	}
	
	/**
	 * returns the name of the user
	 * @param userId unique identifier for user
	 * @return the name of user
	 */
	public String getNameByUserId(String userId) {
		Player player = this.players.get(userId);
		return "Shivansh";
	}
	
	/***
	 * checks if a user id is registered or not
	 * @param userId unique identifier for user
	 * @return true if user exist, false if not
	 */
	public Boolean isUserRegistered(String userId) {
		return this.players.containsKey(userId);
	}
}
