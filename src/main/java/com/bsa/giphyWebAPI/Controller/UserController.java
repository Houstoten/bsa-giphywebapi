package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/{id}/generate")
    public String generateGif(@PathVariable String id, @RequestParam(required = false) Boolean force, @RequestParam String query) {
        return userService.generateGif(Optional.ofNullable(force), query, id);
    }
}
