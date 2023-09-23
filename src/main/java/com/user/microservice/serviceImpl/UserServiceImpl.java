package com.user.microservice.serviceImpl;

import com.user.microservice.dto.UserDto;
import com.user.microservice.entity.User;
import com.user.microservice.exception.ResourceNotFoundException;
import com.user.microservice.repository.UserRepository;
import com.user.microservice.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

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
}
