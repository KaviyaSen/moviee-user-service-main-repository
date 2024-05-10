package com.booking.userservice.service;

import com.booking.userservice.dto.UserDetailsDTO;

import java.util.List;

public interface UserDetailsService {
    UserDetailsDTO createUser(UserDetailsDTO userDetailsDTO);

    UserDetailsDTO updateUser(String userName, UserDetailsDTO userDetailsDTO);

    List<UserDetailsDTO> getAllUsers();

    UserDetailsDTO getByUserName(String userName);

    String deactivateUser(String userName);
}
