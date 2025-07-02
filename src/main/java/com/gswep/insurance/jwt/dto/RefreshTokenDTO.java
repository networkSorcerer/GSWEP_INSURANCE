package com.gswep.insurance.jwt.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshTokenDTO {

    private String refreshToken;
    private Long refreshTokenExpiresIn;
}
