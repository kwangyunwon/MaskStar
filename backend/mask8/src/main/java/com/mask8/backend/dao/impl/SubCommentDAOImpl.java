package com.mask8.backend.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mask8.backend.dao.SubCommentDAO;
import com.mask8.backend.domain.SubCommentVO;

@Repository
public class SubCommentDAOImpl implements SubCommentDAO {

	private String ns = "subCommentMapper.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean insertSubComment(SubCommentVO subCommentVO) {
		int result = sqlSession.insert(ns + "insertSubComment", subCommentVO);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean updateSubComment(SubCommentVO subCommentVO) {
		int result = sqlSession.update(ns + "updateSubComment", subCommentVO);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteSubComment(int subCommentIdx) {
		int result = sqlSession.update(ns + "deleteSubComment", subCommentIdx);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public List<SubCommentVO> selectAllSubComments(int commentIdx) {
		return sqlSession.selectList(ns + "selectAllSubComments", commentIdx);
	}

}
