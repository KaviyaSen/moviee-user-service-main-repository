package com.booking.userservice.controller;

import com.booking.userservice.dto.UserDetailsDTO;
import com.booking.userservice.service.UserDetailsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/v1/users")
public class UserDetailsController {

    /*@Autowired
    private UserDetailsService userDetailsService;*/
    private final UserDetailsService userDetailsService;

    public UserDetailsController(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    public ResponseEntity<List<UserDetailsDTO>> getAllUsers() {
        return ResponseEntity.ok(userDetailsService.getAllUsers());
    }

    @GetMapping("{userName}")
    public ResponseEntity<UserDetailsDTO> getByUserName(@PathVariable String userName) {
        return ResponseEntity.ok(userDetailsService.getByUserName(userName));
    }

    @PostMapping
    public ResponseEntity<UserDetailsDTO> creatUser(@RequestBody @Valid UserDetailsDTO userDetailsDTO) {
        return ResponseEntity.ok(userDetailsService.createUser(userDetailsDTO));
    }

    @PutMapping("{userName}")
    public ResponseEntity<UserDetailsDTO> updateUser(@PathVariable String userName,
                                                     @RequestBody @Valid UserDetailsDTO userDetailsDTO) {
        return ResponseEntity.ok(userDetailsService.updateUser(userName, userDetailsDTO));
    }

    @PatchMapping("/deactivate/{userName}")
    public ResponseEntity<String> deactivateUser(@PathVariable String userName) {
        return ResponseEntity.ok(userDetailsService.deactivateUser(userName));
    }
}
