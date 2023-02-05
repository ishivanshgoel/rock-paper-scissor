package com.stonepaperscissor.test.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.stonepaperscissor.entity.GameMove;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;
import com.stonepaperscissor.payloads.user.CreateNewUserDto;
import com.stonepaperscissor.payloads.user.CreateNewUserResponse;
import com.stonepaperscissor.service.GameService;
import com.stonepaperscissor.service.UserService;

@SpringBootTest
class GameServiceTest {
	
	@Autowired
	private GameService gameService;
	
	@Autowired UserService userService;

	@Test
	@DisplayName("Create New Game succeeds after creating a new user.")
	void testGameCreationSuccessScenario() {
		
		// prepare request data
		CreateNewUserDto createNewUserDto = new CreateNewUserDto();
		createNewUserDto.setName("Shivansh");
		
		// call register new user service to get the unique userId
		CreateNewUserResponse createNewUserResponse = this.userService.registerNewUser(createNewUserDto);
		
		// get unique userId from response
		String userId = createNewUserResponse.getUserId();
		
		// create a new game with the userId
		CreateNewGameDto createNewGameDto = new CreateNewGameDto();
		createNewGameDto.setUserId(userId);

		try {
			CreateNewGameResponse createNewGameResponse = this.gameService.createNewGameAndAddUser(createNewGameDto);
			String gameId = createNewGameResponse.getGameId();
			assertThat(gameId.length()).isEqualTo(36);
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	
	@Test
	@DisplayName("Create New Game fails when incorrect userId is passed.")
	void testGameCreationFailureScenario() {
		
		// create a new game with the a random userId (not stored in memory)
		CreateNewGameDto createNewGameDto = new CreateNewGameDto();
		createNewGameDto.setUserId("69e11029-6dc7-4d75-9be8-61783af87660");

		try {
			CreateNewGameResponse createNewGameResponse = this.gameService.createNewGameAndAddUser(createNewGameDto);
			createNewGameResponse.getGameId();
		} catch(Exception e) {
			assertEquals(e.getMessage(), "userId does not exist");
		}
	}
	
	@Test
	@DisplayName("Play game succeed scenario, when passed a correct move value.")
	void testPlayGame() {
		
		// prepare request data
		CreateNewUserDto createNewUserDto = new CreateNewUserDto();
		createNewUserDto.setName("Shivansh");
		
		// list of possible responses from the API.
		String[] possibleResponse = new String[] {"Shivansh wins", "Computer wins", "It is a tie"};
		
		// call register new user service to get the unique userId
		CreateNewUserResponse createNewUserResponse = this.userService.registerNewUser(createNewUserDto);
		
		// get unique userId from response
		String userId = createNewUserResponse.getUserId();
		
		// create a new game with the userId
		CreateNewGameDto createNewGameDto = new CreateNewGameDto();
		createNewGameDto.setUserId(userId);

		try {			
			// create new response to play the response
			CreateNewGameResponse createNewGameResponse = this.gameService.createNewGameAndAddUser(createNewGameDto);
			String gameId = createNewGameResponse.getGameId();
			String move = "Rock";
			
			// create request DTO
			PlayGameDto playGameDto = new PlayGameDto();
			playGameDto.setMove(move);
			playGameDto.setUserId(userId);
			
			// call the service
			PlayGameResponse playGameResponse = this.gameService.playGame(gameId, playGameDto);
			
			Boolean isFound = false;
			for(String possibility : possibleResponse) {
				if(possibility.equals(playGameResponse.getWinnerResponse())) {
					isFound = true;
				}
			}
			assertTrue(isFound);
		} catch(Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Paper, Computer = Rock, Player wins")
	void testFindWinnerPlayerPaperComputerRock() {
		GameMove playerMove = GameMove.Paper;
		GameMove computerMove = GameMove.Rock;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 1); // player is winner
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Paper, Computer = Paper, It is a tie")
	void testFindWinnerPlayerPaperComputerPaper() {
		GameMove playerMove = GameMove.Paper;
		GameMove computerMove = GameMove.Paper;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 2); // It is a tie
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Paper, Computer = Scissor, Computer Wins")
	void testFindWinnerPlayerPaperComputerScissor() {
		GameMove playerMove = GameMove.Paper;
		GameMove computerMove = GameMove.Scissor;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 0); // computer wins
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Scissor, Computer = Rock, Computer Wins")
	void testFindWinnerPlayerScissorComputerRock() {
		GameMove playerMove = GameMove.Scissor;
		GameMove computerMove = GameMove.Rock;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 0); // computer wins
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Scissor, Computer = Paper, Player Wins")
	void testFindWinnerPlayerScissorComputerPaper() {
		GameMove playerMove = GameMove.Scissor;
		GameMove computerMove = GameMove.Paper;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 1); // player wins
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Scissor, Computer = Scissor, It is a tie")
	void testFindWinnerPlayerScissorComputerScissor() {
		GameMove playerMove = GameMove.Scissor;
		GameMove computerMove = GameMove.Scissor;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 2); // It is a tie
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Scissor, Computer = Rock, It is a tie")
	void testFindWinnerPlayerRockComputerRock() {
		GameMove playerMove = GameMove.Rock;
		GameMove computerMove = GameMove.Rock;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 2); // It is a tie
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Rock, Computer = Paper, Computer Wins")
	void testFindWinnerPlayerRockComputerPaper() {
		GameMove playerMove = GameMove.Rock;
		GameMove computerMove = GameMove.Paper;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 0); // computer wins
	}
	
	@Test
	@DisplayName("Test rock paper scissor logic. Player = Rock, Computer = Scissor, Player wins")
	void testFindWinnerPlayerRockComputerScissor() {
		GameMove playerMove = GameMove.Rock;
		GameMove computerMove = GameMove.Scissor;
		Integer winner = this.gameService.findWinner(computerMove, playerMove);
		assertEquals(winner, 1); // player wins
	}
}
