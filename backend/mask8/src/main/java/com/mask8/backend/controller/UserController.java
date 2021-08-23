package com.mask8.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mask8.backend.domain.AuthVO;
import com.mask8.backend.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Controller
@RestController
@CrossOrigin(origins= {"*"},maxAge=6000)
@Api(tags= {"mask8"})
@RequestMapping("mypage")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "닉네임중복체크", response = Boolean.class)
	@GetMapping("nicknamecheck/{nickname}")
	public ResponseEntity<?> nicknameCheck(@PathVariable String nickname) {
		
		boolean result = userService.nickNameCheck(nickname);
		if (!result)
			return new ResponseEntity(result,HttpStatus.NO_CONTENT);
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	@ApiOperation(value = "아이디중복체크", response = Boolean.class)
	@GetMapping("idcheck/{id}")
	public ResponseEntity<?> idCheck(@PathVariable String id) {
		boolean result = userService.idCheck(id);
		if (!result)
			return new ResponseEntity(result,HttpStatus.NO_CONTENT);
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	@ApiOperation(value = "이메일중복체크", response = Boolean.class)
	@GetMapping("emailcheck/{email}")
	public ResponseEntity<?> emailCheck(@PathVariable String email) {
		
		boolean result = userService.emailCheck(email);
		if (!result)
			return new ResponseEntity(result,HttpStatus.NO_CONTENT);
		return new ResponseEntity(result,HttpStatus.OK);
	}
	
	@ApiOperation(value = "아이디로 유저 정보 검색", response = Boolean.class)
	@GetMapping("user/{id}")
	public ResponseEntity<?> findById(@PathVariable String id) {
		
		try {
			AuthVO vo = userService.findById(id);
			return new ResponseEntity(vo,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
	}
	
	@ApiOperation(value = "유저 정보 수정", response = Boolean.class)
	@PutMapping("update")
	public ResponseEntity<?> updateUser(@RequestBody AuthVO authVO) {
		String result="";
		
        try {
        	userService.updateUser(authVO);
            result="수정 성공 !";
			return new ResponseEntity<String>(result,HttpStatus.OK);
        } catch (Exception e) {
        	result="수정 실패..";
			return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT);
        }
	}
	
	@ApiOperation(value = "비밀번호 변경", response = Boolean.class)
	@PutMapping("update/password")
	public ResponseEntity<?> updatePassword(@RequestBody AuthVO authVO) {
		String result="";
        try {
        	userService.updatePassword(authVO);
            result="비밀번호 변경 완료";
			return new ResponseEntity<String>(result,HttpStatus.OK);
        } catch (Exception e) {
        	result="수정 실패..";
			return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT);
        }
	}
	
	@ApiOperation(value = "회원탈퇴", response = Boolean.class)
	@DeleteMapping("delete/{id}")
	public ResponseEntity<?> userDelete(@PathVariable String id) {
		String result="";
        try {
        	userService.deleteUser(id);
            result="삭제 완료";
			return new ResponseEntity<String>(result,HttpStatus.OK);
        } catch (Exception e) {
        	result="수정 실패..";
			return new ResponseEntity<String>(result,HttpStatus.NO_CONTENT);
        }
	}
	
	
}
