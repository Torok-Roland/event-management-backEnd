package com.event.rest;

import com.event.wrapper.UserWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping(path = "/user")
public interface UserRest {

    @PostMapping(path = "/signup")
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap);

    @PostMapping(path = "/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/get")
    public ResponseEntity<List<UserWrapper>> getAllUser();

//    @PostMapping(path = "/update")
//    public ResponseEntity<String> update(@RequestBody Map<String, String> requestMap);

    @GetMapping(path = "/checkToken") //for validate the user if its user or admin
    public ResponseEntity<String> checkToken();

    @PostMapping(path = "/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> requestMap);

//    @PostMapping(path = "/forgotPassword")
//    public ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Integer id);
}
