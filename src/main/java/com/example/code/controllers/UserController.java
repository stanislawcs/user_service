package com.example.code.controllers;
import com.example.code.dto.ListUserDTO;
import com.example.code.dto.UserCreationResponse;
import com.example.code.dto.UserDTO;
import com.example.code.dto.validation.OnCreate;
import com.example.code.services.UserService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findOneById(@PathVariable("id") Long id){
        return new ResponseEntity<>(userService.findOneById(id),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserCreationResponse> create(@RequestBody @Validated(OnCreate.class) UserDTO userDTO){
        return new ResponseEntity<>(userService.create(userDTO),HttpStatus.CREATED);
    }


}
