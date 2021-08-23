package com.mask8.backend.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mask8.backend.dao.UserDAO;
import com.mask8.backend.domain.AuthVO;
import com.mask8.backend.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public boolean createUser(AuthVO authVO) {
		return userDAO.createUser(authVO);
	}

	@Override
	public boolean updateUser(AuthVO authVO) {
		return userDAO.updateUser(authVO);
	}

	@Override
	public boolean deleteUser(String id) {
		return userDAO.deleteUser(id);
	}

	@Override
	public AuthVO findById(String id) {
		return userDAO.findById(id);
	}

	@Override
	public boolean nickNameCheck(String nickname) {
		return userDAO.nickNameCheck(nickname);
	}

	@Override
	public boolean idCheck(String id) {
		return userDAO.idCheck(id);
	}

	@Override
	public boolean emailCheck(String email) {
		return userDAO.emailCheck(email);
	}

	@Override
	public boolean updatePassword(AuthVO authVO) {
		authVO.setPassword(bcryptEncoder.encode(authVO.getPassword()));
		return userDAO.updatePassword(authVO);
	}

}
