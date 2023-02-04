package com.stonepaperscissor.service;

import com.stonepaperscissor.payloads.user.CreateNewUserDto;
import com.stonepaperscissor.payloads.user.CreateNewUserResponse;

public interface UserService {
	CreateNewUserResponse registerNewUser(CreateNewUserDto createNewUserDto);
}
