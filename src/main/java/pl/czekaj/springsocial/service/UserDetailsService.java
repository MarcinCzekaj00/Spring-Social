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

    private static final int PAGE_SIZE = 10;
    private final UserDetailsRepository userDetailsRepository;
    private final UserRepository userRepository;

    /* Method returning all details
       Uncomment it and getDetails in UserDetailsController if needed

    @Cacheable(cacheNames = "getDetails")
    public List<UserDetailsDto> getDetails(int page, Sort.Direction sort) {
        List<UserDetails> details = userDetailsRepository.findAllDetails(PageRequest.of(page, PAGE_SIZE, Sort.by(sort, "userDetailsId")));
        return UserDetailsDtoMapper.mapToUserDetailsDtos(details);
    }*/

    @Cacheable(cacheNames = "getSingleDetails")
    public UserDetailsDto getSingleDetails(Long id){
        User user = userRepository.findById(id).orElseThrow();
        UserDetails details = user.getDetails();
        return UserDetailsDtoMapper.mapToUserDetailsDtos(details);
    }

    @CachePut(cacheNames = "addDetails", key= "#result.userDetailsId")
    public UserDetailsDto addDetails(UserDetails userDetails, Long id){
        return setDetailsAndUserDetails(userDetails,id);
    }

    @CachePut(cacheNames = "editDetails", key= "#result.userDetailsId")
    public UserDetailsDto editDetails(UserDetails userDetails, Long id){
        return setDetailsAndUserDetails(userDetails,id);
    }

    @Transactional
    @CacheEvict(cacheNames = "deleteDetails")
    public void deleteDetails(Long id){
        User user = userRepository.findById(id).orElseThrow();
        Long udId = user.getDetails().getUserDetailsId();
        userRepository.removeDetails(id);
        userDetailsRepository.removeDetails(udId);
    }

    private UserDetailsDto setDetailsAndUserDetails(UserDetails userDetails, Long id){
        User user = userRepository.findById(id).orElseThrow();
        user.setDetails(userDetails);
        userDetailsRepository.save(userDetails);
        userRepository.save(user);
        return UserDetailsDtoMapper.mapToUserDetailsDtos(userDetails);
    }

}

