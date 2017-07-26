package ru.sur_pavel.domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class InvalidPetStateTransitionException extends RuntimeException {

    public InvalidPetStateTransitionException(String message) {
        super(message);
    }

}
