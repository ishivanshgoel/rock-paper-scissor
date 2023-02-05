package com.stonepaperscissor.service.implementation;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonepaperscissor.entity.Game;
import com.stonepaperscissor.entity.GameMove;
import com.stonepaperscissor.entity.Player;
import com.stonepaperscissor.exception.GameNotCompletedExcpetion;
import com.stonepaperscissor.exception.GameNotFoundException;
import com.stonepaperscissor.exception.UserNotFoundException;
import com.stonepaperscissor.exception.UserNotRegisteredInGameException;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;
import com.stonepaperscissor.repository.GameRepository;
import com.stonepaperscissor.repository.UserRepository;
import com.stonepaperscissor.service.GameService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class GameServiceImplementation implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRespository;
	
	Logger logger = LoggerFactory.getLogger(GameServiceImplementation.class);
	
	@Override
	public CreateNewGameResponse createNewGameAndAddUser(CreateNewGameDto createNewGameDto) 
			throws UserNotFoundException {
		
		// get the user id of external player
		String userId = createNewGameDto.getUserId();
		logger.info("Game Registeration Request Received for User Id: " + userId);
		
		if(this.userRespository.isUserRegistered(userId) == false) {
			logger.error("User Id: " + userId + " does not exist.");
			throw new UserNotFoundException();
		}
		
		// create a second player (Name = Computer)
		String computerId = userRespository.addNewUser(new Player("Computer"));
		
		logger.info("Created Computer Player. User Id: " + computerId);
		
		// create a new game and players
		Game game = new Game();
		ArrayList<String> players = new ArrayList<String>();
		players.add(computerId);
		players.add(userId);
		game.setPlayers(players);
		
		// save the new game
		String newGameId = this.gameRepository.createNewGame(game);
		logger.info("New game created. Game Id: " + newGameId);
		
		// return response
		return new CreateNewGameResponse(newGameId);
	}
	
	@Override
	public PlayGameResponse playGame(String gameId, PlayGameDto playGameDto) 
			throws GameNotFoundException, UserNotRegisteredInGameException {
		
		logger.info("Play game request received. Game Id: " + gameId);
		
		// get the details of game
		Game game = this.gameRepository.getGameDetails(gameId);
		
		// in-case the game does not exist
		if(game == null) {
			logger.error("Game Id: " + gameId + " does not exist.");
			throw new GameNotFoundException();
		}
		
		String playerId = playGameDto.getUserId();
		logger.info("Game Id: " + gameId + " PlayerId: " + playerId);
		
		// get the game players to find their userId
		ArrayList<String> players = game.getPlayers();
		logger.info("Game Id: " + gameId + " Players: " + players.toString());
		
		// when user is not registered in the game
		if(players.get(1).equals(playerId) == false) {
			logger.error("PlayerId: " + playerId + " does not exist for Game Id: " + gameId);
			throw new UserNotRegisteredInGameException();
		}
		
		// generate a random move for computer
		GameMove computersMove = this.generateRandomMove();
		logger.info("Game Id: " + gameId + " Computer's move (Randomly generated) : " + computersMove.toString());
		
		// TODO: check if the move is valid or not
		
		// move entered by external player
		GameMove playersMove = GameMove.valueOf(playGameDto.getMove());
		logger.info("Game Id: " + gameId + " Players's move : " + playGameDto.getMove());
		
		// decide the winner against the game rules
		Integer winner = this.findWinner(computersMove, playersMove);
		
		// mark the game as completed
		this.gameRepository.markGameCompleted(gameId);
		
		// zero means computer is winner
		if(winner == 0) {
			this.gameRepository.updateGameWinner(gameId, players.get(0));
			logger.info("Game Id: " + gameId + " Winner : Computer");
			return new PlayGameResponse(this.userRespository.getNameByUserId(players.get(0)) + " wins");
		} 
		
		// external player is winner
		else if (winner == 1) {
			this.gameRepository.updateGameWinner(gameId, players.get(1));
			logger.info("Game Id: " + gameId + " Winner : Player");
			return new PlayGameResponse(this.userRespository.getNameByUserId(players.get(1)) + " wins");
		}
		
		// game is tie
		logger.info("Game Id: " + gameId + " Winner : It is a Tie");
		return new PlayGameResponse("Is is a tie");
	}
	
	/***
	 * generates a random move to simulate computer's turn
	 * @param gameId unique identifier for game
	 * @return (Player wins/ Computer wins/ It is a tie)
	 * @throws GameNotFoundException
	 */
	@Override
	public String getGameWinner(String gameId) throws GameNotFoundException, GameNotCompletedExcpetion {
		Game gameDetails = this.gameRepository.getGameDetails(gameId);
		logger.info("Get game winner request received. Game Id: " + gameId);
		
		if(gameDetails == null) {
			logger.error("Game Id: " + gameId + " does not exist.");
			throw new GameNotFoundException();
		}
		Boolean isGameCompleted = gameDetails.getIsCompleted();
		if(isGameCompleted == null || isGameCompleted == false) {
			logger.error("Game Id: " + gameId + " not yet completed.");
			throw new GameNotCompletedExcpetion();
		}
		
		String winner = gameDetails.getWinnerId();
		if(winner.isEmpty()) {
			logger.info("Game Id: " + gameId + " Winner: It is a tie.");
			return "It is a tie";
		}
		logger.info("Game Id: " + gameId + " Winner : " + winner);
		return this.userRespository.getNameByUserId(winner) + " wins";
	}
	
	/**
	 * generates a random game move (from Rock, Paper or Scissor)
	 * @return Random (Rock/ Paper/ Scissor)
	 */
	@Override
	public GameMove generateRandomMove() {
		return GameMove.values()[new Random().nextInt(GameMove.values().length)];
	}	
	
	/**
	 * find winner of the game against the game rules
	 * @param computersMove move made by computer
	 * @param playersMove move made my external user
	 * @return 0 if computer wins, 1 if user wins, >= 2 if it is a tie
	 */
	@Override
	public Integer findWinner(GameMove computersMove, GameMove playersMove) {
		if(computersMove == playersMove) return 2; // It is a tie

		if(playersMove == GameMove.Paper) {			
			if(computersMove == GameMove.Rock) return 1; // computer lost
			else return 0; // computer wins in case of scissor
		} else if(playersMove == GameMove.Rock) {
			if(computersMove == GameMove.Paper) return 0; // computer wins
			else return 1; // computer lost in case of scissor
		} else if(playersMove == GameMove.Scissor) {
			if(computersMove == GameMove.Rock) return 0; // computer wins
			else return 1; // computer lost in case of Paper
		}
		
		return -1;
	}
}
