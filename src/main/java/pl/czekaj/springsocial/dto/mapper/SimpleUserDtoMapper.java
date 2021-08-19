package pl.czekaj.springsocial.dto.mapper;

import pl.czekaj.springsocial.dto.SimpleUserDto;
import pl.czekaj.springsocial.model.User;

import java.util.List;
import java.util.stream.Collectors;

public class SimpleUserDtoMapper {

    private SimpleUserDtoMapper(){
    }

    public static List<SimpleUserDto> mapTosimpleUserDtos(List<User> users){
        return users.stream().map(SimpleUserDtoMapper::mapToSimpleUserDto).collect(Collectors.toList());
    }

    public static SimpleUserDto mapTosimpleUserDtos(User user){
        return mapToSimpleUserDto(user);
    }

    private static SimpleUserDto mapToSimpleUserDto(User user) {
        return SimpleUserDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole())
                .details(user.getDetails())
                .build();
    }
}

