package com.softserve.teachua.controller;

import com.softserve.teachua.dto.user.SuccessUpdatedUser;
import com.softserve.teachua.dto.user.UserProfile;
import com.softserve.teachua.dto.user.UserResponse;
import com.softserve.teachua.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * The controller returns information {@code UserResponse} about user.
     *
     * @param id - put user id.
     * @return new {@code UserResponse}.
     */
    @GetMapping("/user/{id}")
    public UserResponse findById(@PathVariable("id") Long id) {
        return userService.getUserProfileById(id);
    }

    /**
     * The controller returns information {@code List <UserResponse>} about users.
     *
     * @return new {@code List <UserResponse>}.
     */
    @GetMapping("/users")
    public List<UserResponse> findAllUsers() {
        return userService.getListOfUsers();
    }

    /**
     * The controller returns information {@code UserResponse} about updated user.
     *
     * @param userProfile - Place dto with all parameters for update existed user.
     * @return new {@code UserProfile}.
     */
    @PutMapping("/user")
    public SuccessUpdatedUser updateUser(@Valid @RequestBody UserProfile userProfile) {
        return userService.updateUser(userProfile);
    }

    /**
     * The controller returns information {@code UserResponse} about deleted user.
     *
     * @param id - put user id.
     * @return new {@code UserResponse}.
     */
    // TODO
    @DeleteMapping("/user/{id}")
    public UserProfile deleteById(@PathVariable("id") Long id) {
        return null;
    }
}
