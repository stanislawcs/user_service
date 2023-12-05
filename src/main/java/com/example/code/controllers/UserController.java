package com.example.code.controllers;
import com.example.code.dto.ListUserDTO;
import com.example.code.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Validated
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ListUserDTO>> getAll(@RequestParam("page")
                                                    @NotNull(message = "Parameter 'page' should not be null") Integer page,
                                                    @RequestParam("users-per-page")
                                                    @NotNull(message = "Parameter 'users-per-page' should not be null") Integer usersPerPage) {

        return new ResponseEntity<>(userService.findAllWithPagination(page, usersPerPage), HttpStatus.OK);
    }


}
