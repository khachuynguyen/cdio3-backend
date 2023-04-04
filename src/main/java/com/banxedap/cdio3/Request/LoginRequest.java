package com.banxedap.cdio3.Request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LoginRequest {
    @NotEmpty
    @NotNull
    private String userName;
    @NotEmpty
    @NotNull
    private String password;
}
