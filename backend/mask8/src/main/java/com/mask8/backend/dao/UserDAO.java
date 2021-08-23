package com.mask8.backend.dao;

import java.util.List;

import com.mask8.backend.domain.AuthVO;

public interface UserDAO {
	
	public boolean createUser(AuthVO authVO); // 회원가입
	public boolean updateUser(AuthVO authVO); // 회원수정
	public boolean deleteUser(String id); // 회원 탈퇴
	public boolean nickNameCheck(String nickname); // 닉네임 중복체크
	public boolean idCheck(String id); // 아이디 중복체크
	public boolean emailCheck(String email); // 이메일 중복 체크
	public boolean updatePassword(AuthVO authVO); // 비밀번호 변경
	public AuthVO findById(String Id);
	
}
