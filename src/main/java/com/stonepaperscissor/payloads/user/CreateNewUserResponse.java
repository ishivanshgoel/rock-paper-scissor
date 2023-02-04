package com.stonepaperscissor.payloads.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateNewUserResponse {
	private String userId;
}
