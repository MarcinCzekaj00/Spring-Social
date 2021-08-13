package pl.czekaj.springsocial.dto.mapper;

import pl.czekaj.springsocial.dto.UserDetailsDto;
import pl.czekaj.springsocial.model.UserDetails;

import java.util.List;
import java.util.stream.Collectors;

public class UserDetailsDtoMapper {

        private UserDetailsDtoMapper(){
        }

        public static List<UserDetailsDto> mapToUserDetailsDtos(List<UserDetails> userDetails){
            return userDetails.stream().map(UserDetailsDtoMapper::mapToUserDetailsDto).collect(Collectors.toList());
        }

        public static UserDetailsDto mapToUserDetailsDtos(UserDetails userDetails){
            return mapToUserDetailsDto(userDetails);
        }

        private static UserDetailsDto mapToUserDetailsDto(UserDetails userDetails) {
            return UserDetailsDto.builder()
                    .userDetailsId(userDetails.getUserDetailsId())
                    .dateOfBirth(userDetails.getDateOfBirth())
                    .phoneNumber(userDetails.getPhoneNumber())
                    .build();
        }

}
