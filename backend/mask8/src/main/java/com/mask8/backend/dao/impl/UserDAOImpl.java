package com.mask8.backend.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mask8.backend.dao.UserDAO;
import com.mask8.backend.domain.AuthVO;

@Repository
public class UserDAOImpl implements UserDAO {
	private final String ns = "UserMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean createUser(AuthVO authVO) {
		int result =sqlSession.insert(ns+"createUser",authVO);
		if(result>0) return true;
		return false;
	}

	@Override
	public boolean updateUser(AuthVO authVO) {
		int result = sqlSession.update(ns+"updateUser",authVO);
		if(result>0) return true;
		return false;
	}

	@Override
	public boolean deleteUser(String id) {
		int result = sqlSession.update(ns+"userDelete",id);
		if(result>0) return true;
		return false;
	}

	@Override
	public AuthVO findById(String id) {
		return sqlSession.selectOne(ns+"findById",id);
	}

	@Override
	public boolean nickNameCheck(String nickname) {
		String id = sqlSession.selectOne(ns+"nicknameCheck",nickname);
		if(id!=null) return true;
		return false;
	}

	@Override
	public boolean idCheck(String id) {
		String id2 = sqlSession.selectOne(ns+"idCheck",id);
		if(id2!=null) return true;
		return false;
	}

	@Override
	public boolean emailCheck(String email) {
		String id = sqlSession.selectOne(ns+"emailCheck",email);
		if(id!=null) return true;
		return false;
	}

	@Override
	public boolean updatePassword(AuthVO authVO) {
		int result = sqlSession.update(ns+"updatePassword",authVO);
		if(result>0) return true;
		return false;
	}

}
