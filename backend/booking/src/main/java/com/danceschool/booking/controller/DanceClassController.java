package com.danceschool.booking.controller;

import com.danceschool.booking.entity.DanceClass;
import com.danceschool.booking.repository.DanceClassRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classes")
@CrossOrigin(origins = "*") 
public class DanceClassController {

    private final DanceClassRepository danceClassRepository;

    public DanceClassController(DanceClassRepository danceClassRepository) {
        this.danceClassRepository = danceClassRepository;
    }


    @GetMapping
    public List<DanceClass> getAllClasses() {
        return danceClassRepository.findAll();
    }

    
    @PostMapping
    public DanceClass createClass(@RequestBody DanceClass danceClass) {
        return danceClassRepository.save(danceClass);
    }
}