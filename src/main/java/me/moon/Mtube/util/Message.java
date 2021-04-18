package me.moon.Mtube.util;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@NoArgsConstructor
public class Message {
    private String msg;

    public Message(String msg){
        this.msg=msg;
    }

    public Map<String, String> Message(String msg){
        Map<String, String> message = new HashMap<>();
        message.put("message", this.msg);
        return message;
    }
}
