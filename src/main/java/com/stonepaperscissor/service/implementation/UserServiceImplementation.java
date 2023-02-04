package com.stonepaperscissor.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stonepaperscissor.entity.Player;
import com.stonepaperscissor.payloads.user.CreateNewUserDto;
import com.stonepaperscissor.payloads.user.CreateNewUserResponse;
import com.stonepaperscissor.repository.UserRepository;
import com.stonepaperscissor.service.UserService;

@Service
public class UserServiceImplementation implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public CreateNewUserResponse registerNewUser(CreateNewUserDto createNewUserDto) {
		String username = createNewUserDto.getName();
		return new CreateNewUserResponse(this.userRepository.addNewUser(new Player(username)));
	}
}
