package com.mask8.backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mask8.backend.domain.AuthVO;

@Service
@Transactional
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override // 기본적인 반환 타입은 UserDetails, UserDetails를 상속받은 UserInfo로 반환 타입 지정 (자동으로 다운 캐스팅됨)
	public AuthVO loadUserByUsername(String id) throws UsernameNotFoundException { // 시큐리티에서 지정한 서비스이기 때문에 이 메소드를 필수로 구현
		try {
			AuthVO vo= userService.findById(id);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return userService.findById(id);
	}
	/**
	 * 회원정보 저장
	 *XB
	 * @param infoDto 회원정보가 들어있는 DTO
	 * @return 저장되는 회원의 PK
	 */
	@Transactional
	public boolean save(AuthVO authVO) {
		
		authVO.setPassword(bcryptEncoder.encode(authVO.getPassword()));
		
		return userService.createUser(authVO);
	}
	
}