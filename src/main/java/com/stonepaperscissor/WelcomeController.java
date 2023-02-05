package com.stonepaperscissor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.stonepaperscissor.exception.UserNotFoundException;
import com.stonepaperscissor.payloads.game.CreateNewGameDto;
import com.stonepaperscissor.payloads.game.CreateNewGameResponse;

import jakarta.validation.Valid;

@RestController
public class WelcomeController {
	
	@GetMapping("/")
	public String sayHello() {
		return "Hello guyss";
	}
	
	@PostMapping("/")
	public ResponseEntity<CreateNewGameResponse> createNewGame(@Valid @RequestBody CreateNewGameDto createNewGameDto) 
			throws UserNotFoundException {
		try {
			CreateNewGameResponse createNewGameResponse = new CreateNewGameResponse("okk");
			return new ResponseEntity<CreateNewGameResponse> (createNewGameResponse, HttpStatus.CREATED);	
		} catch(Exception e) {
			throw e;
		}
	}
}
