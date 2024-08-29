package com.cooksystems.GroupProject1.services.Impl;

import com.cooksystems.GroupProject1.repositories.HashtagRepository;
import com.cooksystems.GroupProject1.repositories.UserRepository;
import com.cooksystems.GroupProject1.services.ValidateService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ValidateServiceImpl implements ValidateService {

    private final HashtagRepository hashtagRepository;
    private final UserRepository userRepository;

    @Override
    public boolean checkHashTagExists(String label) {
        String formattedLabel = "#" + label;
        return hashtagRepository.existsByLabel(formattedLabel);
    }

    @Override
    public boolean checkUsernameExists(String username) {
        return userRepository.existsByCredentialsUsername(username);
    }

    @Override
    public boolean checkUsernameAvailable(String username) {
        return !userRepository.existsByCredentialsUsername(username);
    }
}
