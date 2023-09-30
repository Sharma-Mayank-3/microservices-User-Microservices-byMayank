package com.user.microservice.serviceImpl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.microservice.dto.HotelDto;
import com.user.microservice.exception.RestTemplateException;
import com.user.microservice.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements HotelService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${hotel-service.basepath}")
    private String hotelServiceBasePath;

    @Override
    public HotelDto createHotel(HotelDto hotelDto) {

        HotelDto hotelDto1 = null;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the object as the request body and headers
        HttpEntity<HotelDto> requestEntity = new HttpEntity<>(hotelDto, headers);

        // Send the POST request and receive the response
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                hotelServiceBasePath,
                requestEntity,
                String.class
        );

        String responseBody = responseEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
            Object data = jsonMap.get("data");
            HotelDto hotelDto2 = objectMapper.convertValue(data, HotelDto.class);

            hotelDto1 = hotelDto2;
        }catch (Exception e){
            e.printStackTrace();
        }

        return hotelDto1;

    }

    @Override
    public HotelDto findHotelById(int hotelId) {
//        String baseUrl = hotelServiceBasePath;
//        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(baseUrl)
//                .queryParam("productId", productId);
//        String url = builder.toUriString();



        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send the GET request and receive the response
        try{
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                    hotelServiceBasePath+"/"+hotelId,
                    String.class,
                    requestEntity
            );

            String responseBody = responseEntity.getBody();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});
                Object data = jsonMap.get("data");

                HotelDto hotelDto = objectMapper.convertValue(data, HotelDto.class);
                return hotelDto;

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }catch (Exception e){
            throw new RestTemplateException(e.getMessage());
        }
    }

    @Override
    public List<HotelDto> getAllHotels() {

        // Set up the request headers (if required)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        // Send the GET request and receive the response
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                hotelServiceBasePath,
                String.class,
                requestEntity
        );

        String responseBody = responseEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {});

            List<Object> data = (List<Object>) jsonMap.get("data");
            List<HotelDto> hotelLists = new ArrayList<>();

            List<HotelDto> collect = data.stream().map(hotel -> objectMapper.convertValue(hotel, HotelDto.class)).collect(Collectors.toList());

            return collect;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public HotelDto updateHotel(HotelDto hotelDto, int hotelId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the headers
        HttpEntity<HotelDto> requestEntity = new HttpEntity<>(hotelDto, headers);

            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    hotelServiceBasePath + "/" + hotelId,
                    HttpMethod.PUT,
                    requestEntity,
                    String.class,
                    hotelId
            );

            String responseBody = responseEntity.getBody();
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
                });
                Object data = jsonMap.get("data");

                HotelDto hotelDto1 = objectMapper.convertValue(data, HotelDto.class);
                return hotelDto1;

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
    }

    @Override
    public String deleteHotel(int hotelId) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the HTTP request entity with the headers
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                hotelServiceBasePath + "/" + hotelId,
                HttpMethod.DELETE,
                requestEntity,
                String.class,
                hotelId
        );

        String responseBody = responseEntity.getBody();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> jsonMap = objectMapper.readValue(responseBody, new TypeReference<Map<String, Object>>() {
            });
            Object data = jsonMap.get("data");

            String deleteMessage = objectMapper.convertValue(data, String.class);
            return deleteMessage;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
