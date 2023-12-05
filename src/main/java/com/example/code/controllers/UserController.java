package com.example.code.controllers;

import com.example.code.dto.ListUserDTO;
import com.example.code.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ListUserDTO>> getAll(@RequestParam(value = "page", required = false)
                                                    Integer page,
                                                    @RequestParam(value = "users-per-page", required = false)
                                                    Integer usersPerPage) {

        if(page == null || usersPerPage == null){
            return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(userService.findAllWithPagination(page,usersPerPage),HttpStatus.OK);
        }
    }



}
