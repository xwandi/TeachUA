package com.softserve.teachua.dto.user;

import com.softserve.teachua.dto.marker.Convertible;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UserProfile implements Convertible {

    private Long id;

    @NotEmpty
    private String email;


    @NotEmpty
    @Pattern(regexp ="^[^-ЁёЪъЫыЭэ]*$",message = "Last name not can have russian letters ")
    private String firstName;

    @NotEmpty
    @Pattern(regexp ="^[^-ЁёЪъЫыЭэ]*$",message = "Last name not can have russian letters ")
    private String lastName;

    @NotEmpty
    private String phone;

//    @NotEmpty
    private String password;

    @NotEmpty
    private String roleName;

    private String verificationCode;

    private String urlLogo;

    private String status;

}
