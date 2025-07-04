package com.gswep.insurance.user.requestDTO;

import com.gswep.insurance.user.entity.UserRoleEnum;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequestDTO {
    private String email;
    private String password;
    private String username;
    private Integer phoneNumber;
    private UserRoleEnum roleEnum;
}
