package com.sid.gl.manageemployee.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class AuthRequest {
    private String username;
    private String password;
}
