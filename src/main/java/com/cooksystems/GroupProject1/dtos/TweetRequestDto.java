package com.cooksystems.GroupProject1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@Data
public class TweetRequestDto {
    private String content;
    private CredentialsDto credentialsDto;
}
