package com.cooksystems.GroupProject1.exceptions;

import lombok.*;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotFoundException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
}
