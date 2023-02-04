package com.stonepaperscissor.payloads.game;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateNewGameResponse {
	private String gameId;
}
