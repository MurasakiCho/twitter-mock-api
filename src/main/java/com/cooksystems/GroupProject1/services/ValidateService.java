package com.cooksystems.GroupProject1.services;

public interface ValidateService {
    boolean checkHashTagExists(String label);

    boolean checkUsernameExists(String username);

    boolean checkUsernameAvailable(String username);
}
