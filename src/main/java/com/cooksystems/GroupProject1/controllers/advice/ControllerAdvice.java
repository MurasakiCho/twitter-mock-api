package com.cooksystems.GroupProject1.controllers.advice;

import com.cooksystems.GroupProject1.dtos.ErrorDto;
import com.cooksystems.GroupProject1.exceptions.BadRequestException;
import com.cooksystems.GroupProject1.exceptions.NotAuthorizedException;
import com.cooksystems.GroupProject1.exceptions.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@org.springframework.web.bind.annotation.ControllerAdvice(basePackages = {"com.cooksystems.GroupProject1.controllers"})
@ResponseBody
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    public ErrorDto handleBadRequestException(HttpServletRequest request, BadRequestException badRequestException){
        return new ErrorDto(badRequestException.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ErrorDto handleNotFoundException(HttpServletRequest request, NotFoundException notFoundException){
        return new ErrorDto(notFoundException.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(NotAuthorizedException.class)
    public ErrorDto handleNotAuthroizedException(HttpServletRequest request, NotAuthorizedException notAuthorizedException){
        return new ErrorDto(notAuthorizedException.getMessage());
    }

}
