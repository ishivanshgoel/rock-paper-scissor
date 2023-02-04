package com.stonepaperscissor.service.implementation;

import java.util.ArrayList;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonepaperscissor.entity.Game;
import com.stonepaperscissor.entity.GameMove;
import com.stonepaperscissor.entity.Player;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;
import com.stonepaperscissor.repository.GameRepository;
import com.stonepaperscissor.repository.UserRepository;
import com.stonepaperscissor.service.GameService;

@Service
public class GameServiceImplementation implements GameService {
	
	@Autowired
	private GameRepository gameRepository;
	
	@Autowired
	private UserRepository userRespository;
	
	@Override
	public CreateNewGameResponse createNewGameAndAddUser(CreateNewGameDto createNewGameDto) {
		
		// get the user id of external player
		String userId = createNewGameDto.getUserId();
		
		// create a second player (Name = Computer)
		String computerId = userRespository.addNewUser(new Player("Computer"));
		
		// create a new game and players
		Game game = new Game();
		ArrayList<String> players = new ArrayList<String>();
		players.add(computerId);
		players.add(userId);
		game.setPlayers(players);
		
		// save the new game
		String newGameId = this.gameRepository.createNewGame(game);
		
		// return response
		return new CreateNewGameResponse(newGameId);
	}
	
	@Override
	public PlayGameResponse playGame(String gameId, PlayGameDto playGameDto) {
		
		// get the details of game
		Game game = this.gameRepository.getGameDetails(gameId);
		
		// in-case the game does not exist
		if(game == null) {
			
		}
		
		// generate a random move for computer
		GameMove computersMove = this.generateRandomMove();
		
		// move entered by external player
		GameMove playersMove = playGameDto.getMove();
		
		// decide the winner against the game rules
		Integer winner = this.findWinner(computersMove, playersMove);
		
		// get the game players to find their userId
		ArrayList<String> players = game.getPlayers();
		
		// mark the game as completed
		game.setIsCompleted(true);
		
		// zero means computer is winner
		if(winner == 0) {
			this.gameRepository.updateGameWinner(gameId, players.get(0));
			return new PlayGameResponse(this.userRespository.getNameByUserId(players.get(0)) + " wins");
		} 
		
		// external player is winner
		else if (winner == 1) {
			this.gameRepository.updateGameWinner(gameId, players.get(1));
			return new PlayGameResponse(this.userRespository.getNameByUserId(players.get(1)) + " wins");
		}
		
		// game is tie
		return new PlayGameResponse("Is is a tie");
	}
	
	@Override
	public String getGameWinner(String gameId) {
		return this.gameRepository.getGameWinnerId(this.userRespository.getNameByUserId(gameId));
	}
	
	/**
	 * generates a random game move (from Rock, Paper or Scissor)
	 * @return Random (Rock/ Paper/ Scissor)
	 */
	private GameMove generateRandomMove() {
		return GameMove.values()[new Random().nextInt(GameMove.values().length)];
	}	
	
	/**
	 * 
	 * @param computersMove move made by computer
	 * @param playersMove move made my external user
	 * @return 0 if computer wins, 1 if user wins, > 2 if it is a tie
	 */
	private Integer findWinner(GameMove computersMove, GameMove playersMove) {
		if(computersMove == playersMove) {
			return 2;
		}
		
		if(playersMove == GameMove.Paper) {
			
			// computer lost
			if(computersMove == GameMove.Rock) return 1;
			
			// computer wins in case of scissor
			else return 0;
			
		} else if(playersMove == GameMove.Rock) {
			
			// computer wins
			if(computersMove == GameMove.Paper) return 0;
			
			// computer lost in case of scissor
			else return 1;
		} else if(playersMove == GameMove.Scissor) {
			
			// computer wins
			if(computersMove == GameMove.Rock) return 0;
			
			// computer lost in case of Paper
			else return 1;
		}
		
		return -1;
	}
}
