package com.stonepaperscissor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.exception.GameNotCompletedExcpetion;
import com.stonepaperscissor.exception.GameNotFoundException;
import com.stonepaperscissor.exception.UserNotFoundException;
import com.stonepaperscissor.exception.UserNotRegisteredInGameException;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;
import com.stonepaperscissor.service.GameService;

@RestController
@RequestMapping("/api/v1/game")
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	/**
	 * starts a new game
	 * @return CreateNewGameResponse
	 */
	@PostMapping("/")
	public ResponseEntity<CreateNewGameResponse> createNewGame(@RequestBody CreateNewGameDto createNewGameDto) 
			throws UserNotFoundException {
		try {
			CreateNewGameResponse createNewGameResponse = this.gameService.createNewGameAndAddUser(createNewGameDto);
			return new ResponseEntity<CreateNewGameResponse> (createNewGameResponse, HttpStatus.CREATED);	
		} catch(UserNotFoundException e) {
			throw e;
		}
	}
	
	/**
	 * @param gameId unique identifier for the game
	 * @param playGameDto parameters required as input to play the game
	 * @return PlayGameResponse
	 * @throws UserNotRegisteredInGameException, GameNotFoundException 
	 */
	@PostMapping("/{gameId}/play")
	public ResponseEntity<PlayGameResponse> playGame(@PathVariable String gameId, @RequestBody PlayGameDto playGameDto) 
			throws UserNotRegisteredInGameException, GameNotFoundException {
		try {
			PlayGameResponse playGameResponse = this.gameService.playGame(gameId, playGameDto);
			return new ResponseEntity<PlayGameResponse> (playGameResponse, HttpStatus.OK);	
		} catch(UserNotRegisteredInGameException | GameNotFoundException e) {
			throw e;
		}
	}
	
	/**
	 * @param gameId a unique identifier for the game
	 * @return returns the winner of game (Player wins/ Computer wins/ It is a tie).
	 */
	@GetMapping("/{gameId}/winner")
	public ResponseEntity<String> getWinnerOfGame(@PathVariable String gameId) 
			throws GameNotFoundException, GameNotCompletedExcpetion {
		try {
			String winner = this.gameService.getGameWinner(gameId);
			return new ResponseEntity<String> (winner, HttpStatus.OK);	
		} catch(GameNotCompletedExcpetion | GameNotFoundException e) {
			throw e;
		}
	}
}
