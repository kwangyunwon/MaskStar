package com.mask8.backend.domain;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class AuthVO implements UserDetails {
	
	private int userIdx;
	private String id;
	private String name;
	private String password;
	private String email;
	private String nickname;
	private String birthdate;
	private String auth;
	

	
	public AuthVO() {
		super();
	}
	
	
	public AuthVO(String id, String password) { //비밀번호 변경
		super();
		this.id = id;
		this.password = password;
	}


	public AuthVO(String id, String name, String nickname, String birthdate) { //회원 수정
		super();
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.birthdate = birthdate;
	}


	public AuthVO(String id, String password,String auth) { // 로그인
		this.id = id;
		this.password = password;
		this.auth=auth;
	}
	
	public AuthVO(String id, String name, String password, String email, String nickname, String birthdate) { // 회원가입
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.birthdate = birthdate;
	}
	
	
	public AuthVO(String id, String name, String password, String email, String nickname, String birthdate,
			int userIdx) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.birthdate = birthdate;
		this.userIdx = userIdx;
	}

	
	public AuthVO(int user_idx, String id, String name, String password, String email, String nickname,
			String birthdate, String auth) {
		super();
		this.userIdx = userIdx;
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.nickname = nickname;
		this.birthdate = birthdate;
		this.auth = auth;
	}


	public int getUserIdx() {
		return userIdx;
	}


	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> roles = new HashSet<>();
        for (String role : auth.split(",")) {
            roles.add(new SimpleGrantedAuthority(role));
        }
        return roles;
	}
	@JsonIgnore
	@Override
	public String getUsername() {
		return id;
	}
	@Override
    public String getPassword() {
        return password;
    }
	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@JsonIgnore
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}


	@Override
	public String toString() {
		return "AuthVO [user_idx=" + userIdx + ", id=" + id + ", name=" + name + ", password=" + password + ", email="
				+ email + ", nickname=" + nickname + ", birthdate=" + birthdate + ", auth=" + auth + "]";
	}

	
	
	
}

