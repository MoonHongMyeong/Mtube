package me.moon.Mtube.mapper;

import me.moon.Mtube.dto.UserSaveRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    boolean toExistEmail(String email);

    void insertUser(UserSaveRequestDto userDto);
}
