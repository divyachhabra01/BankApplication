package com.springboot.authentication.BankApplication.controller;

import com.springboot.authentication.BankApplication.auth.UsernamePwdAuthenticationProvider;
import com.springboot.authentication.BankApplication.constants.SecurityConstants;
import com.springboot.authentication.BankApplication.dto.CustomerDTO;
import com.springboot.authentication.BankApplication.dto.LoginRequestDTO;
import com.springboot.authentication.BankApplication.service.CustomerService;
import com.springboot.authentication.BankApplication.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value ="/api/v1")

public class LoginController {
    private final CustomerService customerService;

    private final PasswordEncoder passwordEncoder;

    private final UsernamePwdAuthenticationProvider usernamePwdAuthenticationProvider;

    private JwtUtil jwtUtil;



    public LoginController(CustomerService customerService, PasswordEncoder passwordEncoder, UsernamePwdAuthenticationProvider usernamePwdAuthenticationProvider, JwtUtil jwtUtil) {
        this.customerService = customerService;
        this.passwordEncoder = passwordEncoder;
        this.usernamePwdAuthenticationProvider = usernamePwdAuthenticationProvider;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping(value = "/register",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> userRegistration(@RequestBody @Validated CustomerDTO customerDTO) {
        ResponseEntity<?> response = null;
            String hashedPwd = passwordEncoder.encode(customerDTO.getPassword());
            customerDTO.setPassword(hashedPwd);
            Long customerId = customerService.userRegistration(customerDTO);
            if (customerId > 0) {
                log.debug("New User registered successfully with user id: " + customerId);
                response = ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully with user id: " + customerId);
            }
        return response;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody @Validated LoginRequestDTO loginRequestDTO){
        ResponseEntity<String> response=null;
        HttpHeaders headers= new HttpHeaders();
        UsernamePasswordAuthenticationToken token= new UsernamePasswordAuthenticationToken(loginRequestDTO.getUserName(),loginRequestDTO.getPassword());
         Authentication authentication=usernamePwdAuthenticationProvider.authenticate(token);
        headers.add(SecurityConstants.JWT_HEADER,jwtUtil.generateToken(authentication));
        if(authentication.isAuthenticated()){
            log.debug("New User registered successfully with user id: ");
            response=ResponseEntity.ok().headers(headers).body("User logged in successfully!");
         }
         return response;
    }

}
