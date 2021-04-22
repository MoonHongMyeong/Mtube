package me.moon.Mtube.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnsuitableUserException extends RuntimeException{
    public UnsuitableUserException(String message){
        super(message);
    }
    public UnsuitableUserException(Throwable cause){
        super(cause);
    }
    public UnsuitableUserException(String message, Throwable cause){
        super(message, cause);
    }

}
