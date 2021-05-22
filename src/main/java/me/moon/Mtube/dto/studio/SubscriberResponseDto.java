package me.moon.Mtube.dto.studio;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SubscriberResponseDto {
    private int subscriberCount;
    private String orderByMonth;
}
