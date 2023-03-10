package com.stonepaperscissor.payloads.game;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateNewGameDto {
	
	@NotBlank(message = "userId is required")
	private String userId;
}