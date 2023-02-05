package com.stonepaperscissor.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	Logger logger = LoggerFactory.getLogger(GameServiceImplementation.class);
	
	@Override
	public CreateNewUserResponse registerNewUser(CreateNewUserDto createNewUserDto) {
		String username = createNewUserDto.getName();
		logger.info("Create New user Request Received. User Name " + username);
		String userId = this.userRepository.addNewUser(new Player(username));
		logger.info("User Id " + userId + " created for user name " + username);
		return new CreateNewUserResponse(userId);
	}
}
