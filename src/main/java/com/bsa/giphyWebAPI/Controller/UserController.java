package com.bsa.giphyWebAPI.Controller;

import com.bsa.giphyWebAPI.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/{id}/generate")
    public String generateGif(@PathVariable String id, @RequestParam(required = false) Boolean force, @RequestParam String query) {
        return userService.generateGif(Optional.ofNullable(force), query, id);
    }

    @GetMapping("/{id}/search")
    public String findGif(@PathVariable String id, @RequestParam(required = false) Boolean force, @RequestParam String query) {
        return userService.searchGif(Optional.ofNullable(force), query, id);
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<Map<String, String>>> getUserHistory(@PathVariable String id) {
        return userService.getUserHistory(id);
    }

    @GetMapping("/{id}/all")
    public ResponseEntity<List<Map<String, Object>>> getAllUserFiles(@PathVariable String id){
        return userService.getAllUserFiles(id);
    }

    @DeleteMapping("/{id}/history/clean")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void cleanUserHistory(@PathVariable String id){
        userService.cleanUserHistory(id);
    }

    @DeleteMapping("/{id}/reset")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void resetUserCache(@PathVariable String id, @RequestParam(required = false) String query){
        userService.resetUserCache(id, Optional.ofNullable(query));
    }

    @DeleteMapping("/{id}/clean")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void cleanUser(@PathVariable String id){
        userService.cleanUser(id);
    }
}
