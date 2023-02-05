package com.stonepaperscissor.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.exception.GameNotCompletedExcpetion;
import com.stonepaperscissor.exception.GameNotFoundException;
import com.stonepaperscissor.exception.UserNotFoundException;
import com.stonepaperscissor.exception.UserNotRegisteredInGameException;
import com.stonepaperscissor.payloads.common.ApiErrorResponse;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;
import com.stonepaperscissor.service.GameService;

import jakarta.validation.Valid;

@RestController
public class GameController {
	
	@Autowired
	private GameService gameService;
	
	/**
	 * starts a new game
	 * @return CreateNewGameResponse
	 */
	@PostMapping("/api/v1/game")
	public ResponseEntity createNewGame(@Valid @RequestBody CreateNewGameDto createNewGameDto) {
		try {
			CreateNewGameResponse createNewGameResponse = this.gameService.createNewGameAndAddUser(createNewGameDto);
			return ResponseEntity.status(HttpStatus.CREATED).body(createNewGameResponse);
		} catch(UserNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(e.getMessage()));
		}
	}
	
	/**
	 * @param gameId unique identifier for the game
	 * @param playGameDto parameters required as input to play the game
	 * @return PlayGameResponse
	 * @throws UserNotRegisteredInGameException, GameNotFoundException 
	 */
	@PostMapping("/api/v1/game/{gameId}/play")
	public ResponseEntity playGame(@PathVariable String gameId, @Valid @RequestBody PlayGameDto playGameDto) {
		try {
			PlayGameResponse playGameResponse = this.gameService.playGame(gameId, playGameDto);	
			return ResponseEntity.status(HttpStatus.OK).body(playGameResponse);
		} catch(UserNotRegisteredInGameException | GameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(e.getMessage()));
		}
	}
	
	/**
	 * @param gameId a unique identifier for the game
	 * @return returns the winner of game (Player wins/ Computer wins/ It is a tie).
	 */
	@GetMapping("/api/v1/game/{gameId}/winner")
	public ResponseEntity getWinnerOfGame(@PathVariable String gameId) {
		try {
			String winner = this.gameService.getGameWinner(gameId);
			return ResponseEntity.status(HttpStatus.OK).body(winner);
		} catch(GameNotCompletedExcpetion | GameNotFoundException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiErrorResponse(e.getMessage()));
		}
	}
}
