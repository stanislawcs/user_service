package com.example.code.dto;

import com.example.code.dto.validation.OnCreate;
import com.example.code.dto.validation.OnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserDTO {

    private Long id;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    private String username;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Email(groups = {OnCreate.class, OnUpdate.class})
    private String email;

    @NotBlank(groups = {OnCreate.class, OnUpdate.class})
    @Size(min = 4, max = 20, groups = {OnCreate.class, OnUpdate.class})
    private String password;

}
