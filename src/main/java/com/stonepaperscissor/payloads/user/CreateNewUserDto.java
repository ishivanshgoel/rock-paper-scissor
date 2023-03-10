package com.stonepaperscissor.payloads.user;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CreateNewUserDto {
	
	@NotBlank(message = "name is required")
	private String name;
}