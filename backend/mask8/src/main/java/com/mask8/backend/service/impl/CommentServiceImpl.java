package com.mask8.backend.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mask8.backend.dao.CommentDAO;
import com.mask8.backend.domain.CommentLikeVO;
import com.mask8.backend.domain.CommentVO;
import com.mask8.backend.service.CommentService;

@Service
@Transactional
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	private CommentDAO commentDAO;
	
	@Override
	public boolean insertComment(CommentVO commentVO) {
		return commentDAO.insertComment(commentVO);
	}

	@Override
	public boolean updateComment(CommentVO commentVO) {
		return commentDAO.updateComment(commentVO);
	}

	@Override
	public boolean deleteComment(int commentIdx) {
		return commentDAO.deleteComment(commentIdx);
	}

	@Override
	public List<CommentVO> selectAllComments(HashMap map) {
		return commentDAO.selectAllComments(map);
	}

	@Override
	public boolean insertCommentLike(CommentLikeVO commentLikeVO) {
		return commentDAO.insertCommentLike(commentLikeVO);
	}

	@Override
	public boolean deleteCommentLike(CommentLikeVO commentLikeVO) {
		return commentDAO.deleteCommentLike(commentLikeVO);
	}

	@Override
	public CommentLikeVO checkCommentLike(CommentLikeVO commentLikeVO) {
		return commentDAO.checkCommentLike(commentLikeVO);
	}

	@Override
	public boolean updateCommentLike(int commentIdx) {
		return commentDAO.updateCommentLike(commentIdx);
	}

	@Override
	public int commentCnt(int boardIdx) {
		return commentDAO.commentCnt(boardIdx);
	}

	@Override
	public List<Integer> commentLikeList(HashMap<String, Integer> map) {
		
		return commentDAO.commentLikeList(map);
	}

}
