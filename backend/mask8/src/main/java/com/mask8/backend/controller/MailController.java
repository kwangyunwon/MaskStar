package com.mask8.backend.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mask8.backend.service.MailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RestController
@CrossOrigin(origins= {"*"},maxAge=6000)
@Api(tags= {"mask8"})
public class MailController {
	
	@Autowired
	private MailService mailService;
	
	@ApiOperation(value="이메일인증",response=String.class)
	@PostMapping("signup/email")
	public ResponseEntity<String> email(@RequestBody ArrayList<String> list) throws Exception{
		String email=list.get(0)+"@"+list.get(1);
		String result="이메일 전송 완료";
		try{
			String key=mailService.sendEmail(email);
			return new ResponseEntity<String>(key,HttpStatus.OK);
		}catch(Exception e){
			result="이메일 전송 실패";
			return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT);
		}
		
	}

}
