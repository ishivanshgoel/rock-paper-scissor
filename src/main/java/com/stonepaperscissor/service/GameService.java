package com.stonepaperscissor.service;

import com.stonepaperscissor.entity.GameMove;
import com.stonepaperscissor.exception.GameMoveInvalidException;
import com.stonepaperscissor.exception.GameNotCompletedExcpetion;
import com.stonepaperscissor.exception.GameNotFoundException;
import com.stonepaperscissor.exception.UserNotFoundException;
import com.stonepaperscissor.exception.UserNotRegisteredInGameException;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;

public interface GameService {
	CreateNewGameResponse createNewGameAndAddUser(CreateNewGameDto createNewGameDto) throws UserNotFoundException;
	PlayGameResponse playGame(String gameId, PlayGameDto playGameDto) throws GameNotFoundException, UserNotRegisteredInGameException, GameMoveInvalidException ;
	String getGameWinner(String gameId) throws GameNotFoundException, GameNotCompletedExcpetion;
	GameMove generateRandomMove();
	Integer findWinner(GameMove computersMove, GameMove playersMove);
}
