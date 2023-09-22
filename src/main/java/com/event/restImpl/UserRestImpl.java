package com.event.restImpl;

import com.event.constents.EventConstants;
import com.event.rest.UserRest;
import com.event.service.UserService;
import com.event.utils.EventUtils;
import com.event.wrapper.UserWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserRestImpl implements UserRest {

    private UserService userService;

    public UserRestImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        try {
            return userService.signUp(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        try {
            return userService.login(requestMap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            return userService.getAllUser();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<List<UserWrapper>>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> update(Map<String, String> requestMap) {
//        try{
//            return userService.update(requestMap);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    @Override
    public ResponseEntity<String> checkToken() {
        try {
            return userService.checkToken();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try{
            return userService.changePassword(requestMap);
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try{
//            return userService.forgotPassword(requestMap);
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }
}
