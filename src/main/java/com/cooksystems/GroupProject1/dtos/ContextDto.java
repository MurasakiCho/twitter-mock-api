package com.cooksystems.GroupProject1.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class ContextDto {
    private TweetRequestDto tweet;
    private List<TweetRequestDto> before;
    private List<TweetRequestDto> after;
}
