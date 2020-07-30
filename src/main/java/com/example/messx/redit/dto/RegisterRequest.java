package com.example.messx.redit.dto;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String username;
    private String password;
}
