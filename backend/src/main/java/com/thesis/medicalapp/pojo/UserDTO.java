package com.thesis.medicalapp.pojo;

import com.thesis.medicalapp.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    @NotEmpty
    private String username;
    @NotEmpty
    private String phone;
    private String imageUrl;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
