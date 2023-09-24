package com.user.microservice.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.microservice.dto.HotelDto;
import com.user.microservice.dto.RatingDto;
import com.user.microservice.dto.UserDto;
import com.user.microservice.dto.UserRatingHotelDto;
import com.user.microservice.entity.User;
import com.user.microservice.exception.ResourceNotFoundException;
import com.user.microservice.repository.UserRepository;
import com.user.microservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public UserDto createUser(UserDto userDto) {
        User map = this.modelMapper.map(userDto, User.class);
        User save = this.userRepository.save(map);
        return this.modelMapper.map(save, UserDto.class);
    }

    @Override
    public UserDto getUserById(int userId) {
        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        return this.modelMapper.map(user, UserDto.class);
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> all = this.userRepository.findAll();
        return all.stream().map(user-> this.modelMapper.map(user, UserDto.class)).collect(Collectors.toList());
    }

    @Override
    public UserRatingHotelDto getUserRatingHotel(int userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userId", userId));
        UserDto map = this.modelMapper.map(user, UserDto.class);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send the GET request and receive the response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "http://localhost:8083/microservices/rating/user?userId="+user.getUserId(),
                String.class,
                requestEntity
        );

        String responseBody = responseEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            List<Object> data2 = (List<Object>) jsonMap.get("data");

            List<RatingDto> ratingDtos = data2.stream().map(data -> objectMapper.convertValue(data, RatingDto.class)).collect(Collectors.toList());

            ratingDtos.stream().map(rating -> {
                // create a method and call to Hotel Service, way 1.
                RatingDto hotelAssocitedWithRating = getHotelAssocitedWithRating(rating);
                return hotelAssocitedWithRating;


                // Call to Hotel Service, way 2.
//                ResponseEntity<String> forEntity = restTemplate.getForEntity(
//                        "http://localhost:8082/microservices/hotel/" + rating.getHotelId(),
//                        String.class,
//                        requestEntity
//                );
//                String responseEntityBody = forEntity.getBody();
//                try {
//                    Map<String, Object> jsonMap1 = objectMapper.readValue(responseEntityBody, new TypeReference<Map<String, Object>>() {});
//                    Object data = jsonMap1.get("data");
//
//                    HotelDto hotelDto = objectMapper.convertValue(data, HotelDto.class);
//                    rating.setHotelDto(hotelDto);
//                    return rating;
//
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                return null;
            }).collect(Collectors.toList());

            return UserRatingHotelDto.builder().userDto(map).ratings(ratingDtos).build();

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    private RatingDto getHotelAssocitedWithRating(RatingDto ratingDto){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send the GET request and receive the response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "http://localhost:8082/microservices/hotel/" + ratingDto.getHotelId(),
                String.class,
                requestEntity
        );

        String responseBody = responseEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            Object data = jsonMap.get("data");

            HotelDto hotelDto = objectMapper.convertValue(data, HotelDto.class);
            ratingDto.setHotelDto(hotelDto);
            return ratingDto;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
