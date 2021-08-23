package com.mask8.backend.dao;

import java.util.HashMap;
import java.util.List;

import com.mask8.backend.domain.BoardLikeVO;
import com.mask8.backend.domain.CommentLikeVO;
import com.mask8.backend.domain.CommentVO;

public interface CommentDAO {
	boolean insertComment(CommentVO commentVO);
	boolean updateComment(CommentVO commentVO);
	boolean deleteComment(int commentIdx);
	List<CommentVO> selectAllComments(HashMap<String, Integer> map);
	
	boolean insertCommentLike(CommentLikeVO commentLikeVO);
	boolean deleteCommentLike(CommentLikeVO commentLikeVO);
	CommentLikeVO checkCommentLike(CommentLikeVO commentLikeVO);  
	boolean updateCommentLike(int commentIdx);
	int commentCnt(int boardIdx);
	List<Integer> commentLikeList(HashMap<String, Integer> map);
}