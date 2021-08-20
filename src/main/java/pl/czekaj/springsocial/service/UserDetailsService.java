package pl.czekaj.springsocial.service;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.czekaj.springsocial.dto.UserDetailsDto;
import pl.czekaj.springsocial.dto.mapper.UserDetailsDtoMapper;
import pl.czekaj.springsocial.model.User;
import pl.czekaj.springsocial.model.UserDetails;
import pl.czekaj.springsocial.repository.UserDetailsRepository;
import pl.czekaj.springsocial.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    /* Method returning all details
       Uncomment it and getDetails in UserDetailsController if needed

    public List<UserDetailsDto> getDetails(int page, Sort.Direction sort) {
        List<UserDetails> details = userDetailsRepository.findAllDetails(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "userDetailsId")));
        return UserDetailsDtoMapper.mapToUserDetailsDtos(details);
    }*/

    public UserDetailsDto getSingleDetails(Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        UserDetails details = user.getDetails();
        return UserDetailsDtoMapper.mapToUserDetailsDtos(details);
    }

    public UserDetailsDto addDetails(UserDetails userDetails, Long userId){
        return setDetailsAndUserDetails(userDetails,userId);
    }

    public UserDetailsDto editDetails(UserDetails userDetails, Long userId){
        return setDetailsAndUserDetails(userDetails,userId);
    }

    @Transactional
    public void deleteDetails(Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        Long udId = user.getDetails().getUserDetailsId();
        userRepository.removeDetails(userId);
        userDetailsRepository.removeDetails(udId);
    }

    private UserDetailsDto setDetailsAndUserDetails(UserDetails userDetails, Long userId){
        User user = userRepository.findById(userId).orElseThrow();
        user.setDetails(userDetails);
        userDetailsRepository.save(userDetails);
        userRepository.save(user);
        return UserDetailsDtoMapper.mapToUserDetailsDtos(userDetails);
    }

}

