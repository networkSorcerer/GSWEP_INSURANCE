package com.gswep.insurance.user.response;

import com.gswep.insurance.user.entity.User;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDTO {
    private Long user_id;
    private String email;
    private String username;
    private Integer phoneNumber;
    private String profilePictureUrl;
    private String role;

    public UserResponseDTO(User user) {
        this.user_id = user.getUser_id();
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.profilePictureUrl = user.getProfilePictureUrl();
        this.role = String.valueOf(user.getRole());
    }


}
