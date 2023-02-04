package com.stonepaperscissor.payloads;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateNewGameDto {
	
	@NotBlank
	private String userId;
}