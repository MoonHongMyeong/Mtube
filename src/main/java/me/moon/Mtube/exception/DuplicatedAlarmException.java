package me.moon.Mtube.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedAlarmException extends RuntimeException {
    public DuplicatedAlarmException(String message){
        super(message);
    }

    public DuplicatedAlarmException(String message, Throwable cause){
        super(message,cause);
    }

    public DuplicatedAlarmException(Throwable cause){
        super(cause);
    }
}
