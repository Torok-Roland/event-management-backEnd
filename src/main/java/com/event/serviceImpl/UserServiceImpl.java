package com.event.serviceImpl;

import com.event.JWT.CustomerUsersDetailsService;
import com.event.JWT.JwtFilter;
import com.event.JWT.JwtUtil;
import com.event.constents.EventConstants;
import com.event.dao.UserDao;
import com.event.model.User;
import com.event.service.UserService;
import com.event.utils.EventUtils;
import com.event.wrapper.UserWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    private AuthenticationManager authenticationManager;

    private CustomerUsersDetailsService customerUsersDetailsService;

    private JwtUtil jwtUtil;

    private JwtFilter jwtFilter;


    public UserServiceImpl(UserDao userDao, AuthenticationManager authenticationManager,
                           CustomerUsersDetailsService customerUsersDetailsService, JwtUtil jwtUtil, JwtFilter jwtFilter) {
        this.userDao = userDao;
        this.authenticationManager = authenticationManager;
        this.customerUsersDetailsService = customerUsersDetailsService;
        this.jwtUtil = jwtUtil;
        this.jwtFilter = jwtFilter;
    }

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp{}", requestMap);
        try {
            if (validateSignUpMap(requestMap)) {
                User user = userDao.findByEmailId(requestMap.get("email"));
                if (Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return EventUtils.getResponseEntity("Successfully Registered", HttpStatus.OK);
                } else {
                    return EventUtils.getResponseEntity("Email already exist.", HttpStatus.BAD_REQUEST);
                }
            } else {
                return EventUtils.getResponseEntity(EventConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    private boolean validateSignUpMap(Map<String, String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber") && requestMap.containsKey("email")
                && requestMap.containsKey("password")) {
            return true;
        } else {
            return false;
        }
    }

    private User getUserFromMap(Map<String, String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(requestMap.get("password"));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login");
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );
            if (auth.isAuthenticated()) {
                if (customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\"" +
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity<String>("{\"message\":\"" + "Wait for admin approval." + "\"}",
                            HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception ex) {
            log.error("{}", ex);
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Bad Credentials." + "\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser() {
        try {
            if (jwtFilter.isAdmin()) {
                return new ResponseEntity<>(userDao.getAllUser(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> checkToken() {
        return EventUtils.getResponseEntity("true", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            if (!userObj.equals(null)) {
                if(userObj.getPassword().equals(requestMap.get("oldPassword"))){
                    userObj.setPassword(requestMap.get("newPassword"));
                    userDao.save(userObj);
                    return EventUtils.getResponseEntity("Password Updated Successfully", HttpStatus.OK);
                }
                return EventUtils.getResponseEntity("Incorrect Old Password!",HttpStatus.BAD_REQUEST);
            }
            return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteAccount(Integer id) {
        try{
            if (jwtFilter.isAdmin()) {
                Optional optional = userDao.findById(id);
                if (!optional.isEmpty()) {
                    userDao.deleteById(id);
                    return EventUtils.getResponseEntity("Admin allowed to delete user", HttpStatus.OK);
                } else {
                    return EventUtils.getResponseEntity("User id does not exist!", HttpStatus.OK);
                }
            } else {
                    return EventUtils.getResponseEntity(EventConstants.UNAUTHORIZED_ACCESS,HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

//    @Override
//    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
//        try{
//            User user = userDao.findByEmail(requestMap.get("email"));
//
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        return EventUtils.getResponseEntity(EventConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
//
//    }

}
