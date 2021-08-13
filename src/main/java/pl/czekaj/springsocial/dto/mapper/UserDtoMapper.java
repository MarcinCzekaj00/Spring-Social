package pl.czekaj.springsocial.dto.mapper;

import pl.czekaj.springsocial.dto.UserDto;
import pl.czekaj.springsocial.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserDtoMapper {

    private UserDtoMapper(){
    }

    public static List<UserDto> mapToUserDtos(List<User> users){
        return users.stream().map(UserDtoMapper::mapToUserDto).collect(Collectors.toList());
    }

    public static UserDto mapToUserDtos(User user){
        return mapToUserDto(user);
    }

    private static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .details(user.getDetails())
                .friends(user.getFriends())
                .posts(user.getPostUserId())
                .build();
    }
}
