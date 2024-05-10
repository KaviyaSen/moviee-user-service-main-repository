package com.booking.userservice.service;

import com.booking.userservice.config.SpringTestConfig;
import com.booking.userservice.dto.UserDetailsDTO;
import com.booking.userservice.entity.UserDetailsEntity;
import com.booking.userservice.exception.CustomException;
import com.booking.userservice.entity.UserPlan;
import com.booking.userservice.repository.UserDetailsRepository;
import com.booking.userservice.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.lang.StringTemplate.STR;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {SpringTestConfig.class})
class UserDetailsServiceImplTest {

    private UserDetailsServiceImpl userDetailsService;
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        userDetailsRepository = mock(UserDetailsRepository.class);
        userDetailsService = new UserDetailsServiceImpl(userDetailsRepository, modelMapper, bCryptPasswordEncoder);
    }

    @Test
    void createUser_success() {
        UserDetailsEntity userDetails = createUserDetailsEntity();
        when(userDetailsRepository.save(any(UserDetailsEntity.class))).thenReturn(userDetails);

        UserDetailsDTO userDetailsDTO = userDetailsService.createUser(createUsersDetailsDTO());
        assertNotNull(userDetailsDTO);
        assertEquals("TestUser", userDetailsDTO.getUserName());
        assertEquals("test@testemail.com", userDetailsDTO.getEmailId());
    }

    @Test
    void updateUser_success() {
        String userName = "TestUser";
        UserDetailsEntity userDetails = createUserDetailsEntity();
        when(userDetailsRepository.save(any(UserDetailsEntity.class))).thenReturn(userDetails);

        UserDetailsDTO userDetailsDTO = userDetailsService.updateUser(userName, createUsersDetailsDTO());
        assertNotNull(userDetailsDTO);
        assertEquals("TestUser", userDetailsDTO.getUserName());
        assertEquals("test@testemail.com", userDetailsDTO.getEmailId());
    }

    @Test
    void updateUser_noUserFound() {
        String userName = "TestUser";

        assertThrows(CustomException.class,
                () -> userDetailsService.updateUser(userName, createUsersDetailsDTO()),
                STR."No user present with given username: \{userName}");
    }

    @Test
    void getAllUsers() {
        when(userDetailsRepository.findAll()).thenReturn(List.of(createUserDetailsEntity()));

        List<UserDetailsDTO> userDetails = userDetailsService.getAllUsers();
        assertNotNull(userDetails);
        assertEquals(1, userDetails.size());
        assertEquals("TestUser", userDetails.getFirst().getUserName());
    }

    @Test
    void getByUserName_Success() {

        UserDetailsDTO userDetailsDTO = userDetailsService.getByUserName("TestUser");
        assertNotNull(userDetailsDTO);
        assertEquals("TestUser", userDetailsDTO.getUserName());
        assertEquals("test@testemail.com", userDetailsDTO.getEmailId());
    }

    @Test
    void deactivateUser_success() {
        String userName = "TestUser";
        String response = userDetailsService.deactivateUser(userName);

        assertEquals(STR."User \{userName} deleted successfully", response);
        verify(userDetailsRepository, times(1)).deactivateUser(userName);
    }

    @Test
    void deactivateUser_noUserFound() {
        String userName = "TestUser";
        String response = userDetailsService.deactivateUser(userName);

        assertEquals(STR."No user found with given name \{userName}", response);
        verify(userDetailsRepository, times(0)).deactivateUser(userName);
    }

    private UserDetailsEntity createUserDetailsEntity() {
        UserDetailsEntity userDetails = new UserDetailsEntity();
        userDetails.setId(100101);
        userDetails.setUserName("TestUser");
        userDetails.setFirstName("Test");
        userDetails.setLastName("User");
        userDetails.setPassword("Test@123");
        userDetails.setEmailId("test@testemail.com");
        userDetails.setPhoneNumber("9876543210");
        userDetails.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userDetails.setUserPlan(UserPlan.FREE);
        userDetails.setCreatedAt(LocalDateTime.now());
        userDetails.setUpdatedAt(LocalDateTime.now());

        return userDetails;
    }

    private UserDetailsDTO createUsersDetailsDTO() {
        UserDetailsDTO userDetailsDTO = new UserDetailsDTO();
        userDetailsDTO.setUserName("TestUser");
        userDetailsDTO.setFirstName("Test");
        userDetailsDTO.setLastName("User");
        userDetailsDTO.setPassword("Test@123");
        userDetailsDTO.setEmailId("test@testemail.com");
        userDetailsDTO.setPhoneNumber("9876543210");
        userDetailsDTO.setDateOfBirth(LocalDate.of(2000, 1, 1));
        userDetailsDTO.setUserPlan(UserPlan.FREE.name());

        return userDetailsDTO;
    }
}
