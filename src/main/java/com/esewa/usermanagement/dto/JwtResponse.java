package com.esewa.usermanagement.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class JwtResponse {
    private String jwtToken;
    private String username;

    public JwtResponse(String jwtToken) {
        this.jwtToken = jwtToken;

    }
    public String getToken() {
        return jwtToken;
    }
}
