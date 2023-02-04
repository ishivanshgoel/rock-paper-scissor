package com.stonepaperscissor.payloads.game;

import com.stonepaperscissor.entity.GameMove;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class PlayGameDto {
	
	@NotBlank
	private String userId;

	@NotBlank
	private GameMove move;
}