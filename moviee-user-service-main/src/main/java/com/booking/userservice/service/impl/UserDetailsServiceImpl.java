package com.booking.userservice.service.impl;

import com.booking.userservice.dto.UserDetailsDTO;
import com.booking.userservice.entity.UserDetailsEntity;
import com.booking.userservice.exception.CustomException;
import com.booking.userservice.repository.UserDetailsRepository;
import com.booking.userservice.service.UserDetailsService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository, ModelMapper modelMapper,
                                  BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetailsDTO createUser(UserDetailsDTO userDetailsDTO) {
        userDetailsDTO.setPassword(bCryptPasswordEncoder.encode(userDetailsDTO.getPassword()));
        UserDetailsEntity userDetailsEntity = userDetailsRepository.save(mapToEntity(userDetailsDTO));
        return mapToDTO(userDetailsEntity);
    }

    @Override
    public UserDetailsDTO updateUser(String userName, UserDetailsDTO userDetailsDTO) {
        Optional<UserDetailsEntity> userDetailsEntityOptional = userDetailsRepository.findByUserNameAndIsActiveTrue(userName);
        if (userDetailsEntityOptional.isEmpty()) {
            throw new CustomException(STR."No user present with given username: \{userName}");
        }

        UserDetailsEntity userDetailsEntity = userDetailsEntityOptional.get();
        userDetailsEntity.setPassword(userDetailsEntity.getPassword());
        modelMapper.map(userDetailsDTO, userDetailsEntity);
        userDetailsEntity.setUserName(userName);

        userDetailsEntity = userDetailsRepository.save(userDetailsEntity);
        return mapToDTO(userDetailsEntity);
    }

    @Override
    public List<UserDetailsDTO> getAllUsers() {
        List<UserDetailsEntity> userDetailsEntityList = userDetailsRepository.findAll();
        return userDetailsEntityList.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserDetailsDTO getByUserName(String userName) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUserNameAndIsActiveTrue(userName);
        return userDetailsEntity.map(this::mapToDTO)
                .orElse(null);
    }

    @Override
    @Transactional
    public String deactivateUser(String userName) {
        Optional<UserDetailsEntity> userDetailsEntity = userDetailsRepository.findByUserNameAndIsActiveTrue(userName);
        if (userDetailsEntity.isEmpty()) {
            return STR."No user found with given name \{userName}";
        } else {
            userDetailsRepository.deactivateUser(userName);
            return STR."User \{userName} deleted successfully";
        }
    }

    private UserDetailsDTO mapToDTO(UserDetailsEntity userDetailsEntity) {
        return modelMapper.map(userDetailsEntity, UserDetailsDTO.class);
    }

    private UserDetailsEntity mapToEntity(UserDetailsDTO userDetailsDTO) {
        return modelMapper.map(userDetailsDTO, UserDetailsEntity.class);
    }
}
