package com.mask8.backend.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mask8.backend.dao.CommentDAO;
import com.mask8.backend.domain.CommentLikeVO;
import com.mask8.backend.domain.CommentVO;

@Repository
public class CommentDAOImpl implements CommentDAO {

	private String ns = "commentMapper.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean insertComment(CommentVO commentVO) {
		int result = sqlSession.insert(ns + "insertComment", commentVO);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean updateComment(CommentVO commentVO) {
		int result = sqlSession.update(ns + "updateComment", commentVO);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteComment(int commentIdx) {
		int result = sqlSession.update(ns + "deleteComment", commentIdx);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public List<CommentVO> selectAllComments(HashMap map) {
		return sqlSession.selectList(ns + "selectAllComments", map);
	}

	@Override
	public boolean insertCommentLike(CommentLikeVO commentLikeVO) {
		int result = sqlSession.insert(ns + "insertCommentLike", commentLikeVO);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public boolean deleteCommentLike(CommentLikeVO commentLikeVO) {
		int result = sqlSession.update(ns + "deleteCommentLike", commentLikeVO);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public CommentLikeVO checkCommentLike(CommentLikeVO commentLikeVO) {
		return sqlSession.selectOne(ns + "checkCommentLike", commentLikeVO);
	}

	@Override
	public boolean updateCommentLike(int commentIdx) {
		int result = sqlSession.update(ns + "updateCommentLike", commentIdx);
		if (result > 0)
			return true;
		return false;
	}

	@Override
	public int commentCnt(int boardIdx) {
		return sqlSession.selectOne(ns + "commentCnt", boardIdx);
	}

	@Override
	public List<Integer> commentLikeList(HashMap<String, Integer> map) {
		return sqlSession.selectList(ns + "commentLikeList", map);
	}
}
