package com.cooksystems.GroupProject1.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@AllArgsConstructor
@Getter
@Setter
public class NotAuthorizedException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private String message;
}

