package com.stonepaperscissor.service;

import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;
import com.stonepaperscissor.payloads.game.PlayGameDto;
import com.stonepaperscissor.payloads.game.PlayGameResponse;

public interface GameService {
	CreateNewGameResponse createNewGameAndAddUser(CreateNewGameDto createNewGameDto);
	PlayGameResponse playGame(String gameId, PlayGameDto playGameDto);
	String getGameWinner(String gameId);
}
