package com.mask8.backend.service;

import java.util.List;

import com.mask8.backend.domain.AuthVO;

public interface UserService {
	public boolean createUser(AuthVO authVO); // 회원가입
	public boolean updateUser(AuthVO authVO); // 유저 정보 수정
	public boolean deleteUser(String id); // 회원탈퇴

	public boolean nickNameCheck(String nickname); // 닉네임 중복체크
	public boolean idCheck(String id); // 아이디 중복체크
	public boolean emailCheck(String email); // 이메일 중복 체크
	
	public boolean updatePassword(AuthVO authVO); // 비밀번호 변경
	public AuthVO findById(String id); // 유저 전체 정보 조회
	
}
