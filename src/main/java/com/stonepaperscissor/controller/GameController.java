package com.stonepaperscissor.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.payloads.CreateNewGameDto;
import com.stonepaperscissor.payloads.CreateNewGameResponse;
import com.stonepaperscissor.payloads.PlayGameDto;
import com.stonepaperscissor.payloads.PlayGameResponse;

@RestController
public class GameController {
	
	/**
	 * starts a new game
	 * @return CreateNewGameResponse
	 */
	@PostMapping("/game/new")
	public ResponseEntity<CreateNewGameResponse> createNewGame(@RequestBody CreateNewGameDto createNewGameDto) {
		return new ResponseEntity<CreateNewGameResponse> (new CreateNewGameResponse(), HttpStatus.CREATED);
	}
	
	/**
	 * @param gameId unique identifier for the game
	 * @param playGameDto paramters required as input to play the game
	 * @return PlayGameResponse
	 */
	@PostMapping("/game/{gameId}/play")
	public ResponseEntity<PlayGameResponse> playGame(@PathVariable String gameId, @RequestBody PlayGameDto playGameDto) {
		return new ResponseEntity<PlayGameResponse> (new PlayGameResponse(), HttpStatus.OK);
	}
	
	/**
	 * @param gameId a unique identifier for the game
	 * @return returns the winner of game (Player wins/ Computer wins/ It is a tie).
	 */
	@GetMapping("/game/{gameId}/winner")
	public ResponseEntity<String> getWinnerOfGame(@PathVariable String gameId) {
		System.out.println("Game Id " + gameId);
		return new ResponseEntity<String> ("Computer Wins", HttpStatus.OK);
	}
}
