package me.moon.Mtube.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedEmailException extends RuntimeException{

    public DuplicatedEmailException(String message){
        super(message);
    }

    public DuplicatedEmailException(String message, Throwable cause){
        super(message,cause);
    }

    public DuplicatedEmailException(Throwable cause){
        super(cause);
    }
}
