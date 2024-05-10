package com.booking.userservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/")
public class ConfigController {

    @GetMapping("memstat")
    public String getMemoryStatistics() {
        String heapSize = String.valueOf(Runtime.getRuntime().totalMemory()/1000000);
        String maxMem = String.valueOf(Runtime.getRuntime().maxMemory()/1000000);
        String minMem = String.valueOf(Runtime.getRuntime().freeMemory()/1000000);
        return StringTemplate.STR."Heap Size : \{heapSize} MB, Max memory : \{maxMem} MB, Min memory : \{minMem} MB";
    }
}
