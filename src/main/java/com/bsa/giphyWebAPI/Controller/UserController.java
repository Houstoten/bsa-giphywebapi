package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/user/{id}/generate")
    public String generateGif(@PathVariable String id, @RequestParam(required = false) Boolean force, @RequestParam String query) {
        return userService.generateGif(Optional.ofNullable(force), query, id);
    }

    @GetMapping("/user/{id}/search")
    public String findGif(@PathVariable String id, @RequestParam(required = false) Boolean force, @RequestParam String query){
        return userService.searchGif(Optional.ofNullable(force), query, id);
    }
}
