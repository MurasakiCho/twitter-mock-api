package com.cooksystems.GroupProject1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@NoArgsConstructor
@Data
public class TweetResponseDto {
    private Long id;
    private UserResponseDto author;
    private Timestamp posted;
    private String content;
    private TweetResponseDto inReplyTo;
    private TweetResponseDto repostOf;
    private List<TweetResponseDto> replies;
}
