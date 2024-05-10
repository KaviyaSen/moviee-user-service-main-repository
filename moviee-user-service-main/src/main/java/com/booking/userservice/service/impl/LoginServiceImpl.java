package com.booking.userservice.service.impl;

import com.booking.userservice.dto.LoginDTO;
import com.booking.userservice.entity.UserDetailsEntity;
import com.booking.userservice.repository.UserDetailsRepository;
import com.booking.userservice.service.LoginService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginServiceImpl implements LoginService {

    private final UserDetailsRepository userDetailsRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public LoginServiceImpl(UserDetailsRepository userDetailsRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDetailsRepository = userDetailsRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public String login(LoginDTO loginDTO) {
        Optional<UserDetailsEntity> entityOptional = userDetailsRepository.findByUserNameAndIsActiveTrue(loginDTO.getUserName());
        if (entityOptional.isEmpty()) {
            return "User not found";
        }

        return bCryptPasswordEncoder.matches(loginDTO.getPassword(), entityOptional.get().getPassword())
                ? "Success" : "Password does not match";
    }
}
