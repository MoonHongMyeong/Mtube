package me.moon.Mtube.dto.playlist;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPlaylistResponseDto {
    private Long id;
    private String name;
    private String playlistName;
}
