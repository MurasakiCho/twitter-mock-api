package com.cooksystems.GroupProject1.controllers;

import com.cooksystems.GroupProject1.services.ValidateService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/validate")
public class ValidateController {

    private final ValidateService validateService;
	

    @GetMapping("/tag/exists/{label}")
    public boolean checkHashTagExists(@PathVariable String label){
        return validateService.checkHashTagExists(label);
    }

    @GetMapping("/username/exists/@{username}")
    public boolean checkUsernameExists(@PathVariable String username){
        return validateService.checkUsernameExists(username);
    }

	@GetMapping("/username/available/@{username}")
    public boolean checkUsernameAvailable(@PathVariable String username){
        return validateService.checkUsernameAvailable(username);
    }
}
