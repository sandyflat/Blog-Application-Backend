package com.sandyflat.BlogApplication.dto;

import com.sandyflat.BlogApplication.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
public class UserDTO {
    private Long id;

    @NotEmpty
    @Size(min = 4, max = 20, message = "Username must be minimum of 4 and max 20 character")
    private String name;

    @NotEmpty
    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 5, message = "Password must be minimum of 5 character")
    private String password;

    @NotNull
    private int age;

    @NotEmpty
    private String gender;

    @NotNull
    private Set<Long> roleId;

    private Set<Role> roles;
}
