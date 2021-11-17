package com.softserve.teachua.controller.user;

import com.softserve.teachua.controller.marker.Api;
import com.softserve.teachua.dto.user.SuccessRegistration;
import com.softserve.teachua.dto.user.SuccessVerification;
import com.softserve.teachua.dto.user.UserProfile;
import com.softserve.teachua.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@Tag(name="registration", description="the Registration API")
public class RegistrationController implements Api {

    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    /**
     * The controller returns dto {@code SuccessRegistration} of sign-upped user.
     * @param code - code of user verification
     * @return new {@code SuccessRegistration}.
     */
    @GetMapping("/verify")
    public SuccessVerification verifyUser(@Param("code") String code) {
        //log.info("Controller \"verify\",  code = " + code);
        return userService.verify(code);
    }

    /**
     * The controller returns dto {@code SuccessRegistration} of sign-upped user.
     *
     * @param userProfile - dto with all params.
     * @return new {@code SuccessRegistration}.
     */
    @PostMapping("/signup")
    public SuccessRegistration signUp(
            @Valid
            @RequestBody
                    UserProfile userProfile) {
        //log.info("Controller \"signup\", userProfile = " + userProfile.toString());
        return userService.registerUser(userProfile);
    }
}