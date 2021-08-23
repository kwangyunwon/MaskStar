package com.mask8.backend.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mask8.backend.config.JwtRequest;
import com.mask8.backend.config.JwtTokenUtil;
import com.mask8.backend.domain.AuthVO;
import com.mask8.backend.service.JwtUserDetailsService;
import com.mask8.backend.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RestController
@CrossOrigin(origins= {"*"},maxAge=6000)
@Api(tags= {"mask8"})

public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	@Autowired
	private UserService userService;
	@Autowired
	JwtTokenUtil util;
	
	@ApiOperation(value = "로그인", response = Map.class)
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		AuthVO authVO = userService.findById(authenticationRequest.getUsername());
		Date a=util.getExpirationDateFromToken(token);
		SimpleDateFormat sDate2 = new SimpleDateFormat("HH:mm"); //시간표기
		Map map = new HashMap<>();
		map.put("date",sDate2.format(a).toString());
		map.put("token",token);
		map.put("userIdx",authVO.getUserIdx());
		map.put("nickname",authVO.getNickname());
		map.put("id",authenticationRequest.getUsername());
		
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	private void authenticate(String username, String password) throws Exception {
		try {
			
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
	@ApiOperation(value = "회원가입", response = String.class)
	@PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody AuthVO authVO) { // 회원 추가
    	String result="";
        try {
        	userDetailsService.save(authVO);
            result="회원가입 성공 !";
			return new ResponseEntity<String>(result,HttpStatus.OK);
        } catch (Exception e) {
        	e.printStackTrace();
        	result="회원가입 실패..";
        	System.out.println(result);
			return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT);
        }
    }
}