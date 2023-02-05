package com.stonepaperscissor.payloads.game;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PlayGameDto {
	
	@NotBlank(message = "userId is required")
	private String userId;

	@NotBlank(message = "move is required")
	private String move;
}