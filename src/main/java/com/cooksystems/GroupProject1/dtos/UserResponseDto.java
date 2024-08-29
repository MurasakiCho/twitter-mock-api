package com.cooksystems.GroupProject1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class UserResponseDto {
    private String username;
    private ProfileDto profileDto;
    private Timestamp joined;

    public UserResponseDto setProfileDto(ProfileDto profileDto){
        this.profileDto = profileDto;
        return this;
    }
}
